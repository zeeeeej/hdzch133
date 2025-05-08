package com.bugull.okstream.base;

import android.util.Log;

/**
 * Created by cj on 2/12/21.
 */
public abstract class AbsLooperTask implements Runnable {
    public volatile Thread thread = null;

    protected volatile String threadName = "";

    private volatile boolean isStop;

    private volatile boolean isShutdown = true;

    private volatile Exception ioException = null;

    private volatile long loopTimes = 0;

    private volatile Boolean hasStart = false;

    public AbsLooperTask() {
        isStop = true;
        threadName = this.getClass().getSimpleName();
    }

    public AbsLooperTask(String name) {
        isStop = true;
        threadName = name;
    }

    public synchronized void start() throws InterruptedException {
        if (isStop) {
            thread = new Thread(this, threadName);
            isStop = false;
            loopTimes = 0;
            thread.start();
            if (!hasStart) {
                synchronized (this) {
                    wait();
                }
            }
            // System.out.println("AbsLooperRunnable:  " + threadName + " is starting");
        }
    }

    @Override
    public final void run() {
        try {
            isShutdown = false;
            hasStart = true;
            synchronized (this) {
                notifyAll();
            }
            beforeLoop();
            while (!isStop) {
                this.runInLoopThread();
                //Log.i("_xpl_", "AbsLooperTask::run ->" +loopTimes);
                loopTimes++;
            }
        } catch (Exception e) {
            //Log.e("_xpl_", "AbsLooperTask::run ->" + e);
            if (ioException == null) {
                ioException = e;
            }
        } finally {
            synchronized (this) {
                isShutdown = true;
                hasStart = false;
                this.loopFinish(ioException);
                ioException = null;

                System.out.println("AbsLooperRunnable:  " + threadName + "is shutting down");
                notifyAll();
            }
        }
    }

    public long getLoopTimes() {
        return loopTimes;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String appendStr) {
        threadName = this.getClass().getSimpleName() + appendStr;
    }

    protected void beforeLoop() throws Exception {

    }

    protected abstract void runInLoopThread() throws Exception;

    protected abstract void loopFinish(Exception e);

    private synchronized void shutdown() {
        if (thread != null && !isStop) {
            isStop = true;
            thread.interrupt();
            thread = null;
        }
    }

    public synchronized void shutdownSerial() throws InterruptedException {
        shutdown();
        Thread.interrupted();
        while (!isShutdown) {
            wait();
        }
    }

    public boolean isShutdown() {
        return isShutdown;
    }

}
