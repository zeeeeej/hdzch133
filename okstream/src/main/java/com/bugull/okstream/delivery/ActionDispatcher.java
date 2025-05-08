package com.bugull.okstream.delivery;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bugull.okstream.Options;
import com.bugull.okstream.base.AbsLooperTask;
import com.bugull.okstream.base.Constants;
import com.bugull.okstream.pojo.SerialData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by cj on 3/12/21.
 */
public class ActionDispatcher implements IStateSender {
    private final Options options;
    private final LinkedBlockingQueue<ActionBean> actionBeanQueue;
    private final ArrayList<IActionReceiver> receivers = new ArrayList<>();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ActionDispatcher(Options options) {
        this.options = options;
        if (options == null) {
            actionBeanQueue = new LinkedBlockingQueue<>();
        } else if (options.getDispatcherCapacity() == null) {
            actionBeanQueue = new LinkedBlockingQueue<>();
        } else {
            actionBeanQueue = new LinkedBlockingQueue<>(options.getDispatcherCapacity());
        }
        try {
            DispatchTask dispatchTask = new DispatchTask();
            dispatchTask.start();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void registerReceiver(IActionReceiver iActionReceiver) {
        synchronized (receivers) {
            if (!receivers.contains(iActionReceiver)) {
                receivers.add(iActionReceiver);
            }
        }
    }

    public void unRegisterReceiver(IActionReceiver iActionReceiver) {
        synchronized (receivers) {
            if (receivers.contains(iActionReceiver)) {
                receivers.remove(iActionReceiver);
            }
        }
    }

    @Override
    public void sendBroadcast(String action) {
        sendBroadcast(action, null);
    }

    @Override
    public void sendBroadcast(String action, Serializable serializable) {
        Log.e("_xpl_", "sendBroadcast action:" + action + " ,serializable:" + serializable);
        if (options == null) {
            ActionBean actionBean = new ActionBean(action, serializable, this);
            defaultDispatch(actionBean);
            return;
        }
        if (options.isDispatchInUiThread()) {
            handler.post(() -> {
                ActionBean actionBean = new ActionBean(action, serializable, this);
                defaultDispatch(actionBean);
            });
        } else {
            ActionBean actionBean = new ActionBean(action, serializable, this);
            actionBeanQueue.offer(actionBean);
        }
    }

    private void defaultDispatch(ActionBean actionBean) {
        synchronized (receivers) {
            for (IActionReceiver receiver : receivers) {
                if (actionBean.args instanceof SerialData) {
                    actionBean.dispatcher.doDispatch(actionBean.action, actionBean.args, receiver);
                }
            }
        }
    }

    private void doDispatch(String action, Serializable args, IActionReceiver receiver) {
        int type = -1;
        switch (action) {
            case Constants.RECEIVE_DATA_ACTION:
                type = 0;
                break;
            case Constants.EXCEPTION_INPUTSTREAMCLOSE_ACTION:
                type = 1;
                break;
            case Constants.EXCEPTION_OUTPUTSTREAMCLOSE_ACTION:
                type = 2;
                break;
        }
        switch (type) {
            case 0:
                try {
                    receiver.onReceive((SerialData) args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:

                break;
            case 2:

                break;
        }
    }

    public class DispatchTask extends AbsLooperTask {
        @Override
        protected void runInLoopThread() throws Exception {
            ActionBean bean = actionBeanQueue.take();
            if (bean != null && bean.dispatcher != null) {
                synchronized (receivers) {
                    for (IActionReceiver receiver : receivers) {
                        bean.dispatcher.doDispatch(bean.action, bean.args, receiver);
                    }
                }
            }
        }

        @Override
        protected void loopFinish(Exception e) {
            if (options != null && options.getLog() != null) {
                options.getLog().e(e);
            } else {
                e.printStackTrace();
            }
        }
    }

    public static class ActionBean {
        String action;
        Serializable args;
        ActionDispatcher dispatcher;

        public ActionBean(String action, Serializable args, ActionDispatcher dispatcher) {
            this.action = action;
            this.args = args;
            this.dispatcher = dispatcher;
        }
    }
}
