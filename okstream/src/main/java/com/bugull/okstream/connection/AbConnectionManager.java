package com.bugull.okstream.connection;

import com.bugull.okstream.Options;
import com.bugull.okstream.delivery.ActionDispatcher;
import com.bugull.okstream.delivery.IActionReceiver;
import com.bugull.okstream.iocore.ISendable;
import com.bugull.okstream.iothreads.IOManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by cj on 3/12/21.
 */
public abstract class AbConnectionManager {
    private ActionDispatcher actionDispatcher;
    private Options options;
    private IOManager ioManager;

    public AbConnectionManager(Options options) {
        this.options=options;
        actionDispatcher=new ActionDispatcher(options);
    }

    public Options getOptions() {
        return options;
    }

    protected void afterResourcesLoaded(InputStream inputStream, OutputStream outputStream){
        toDestroyResources();
        ioManager=new IOManager(outputStream,inputStream,options,actionDispatcher);
        ioManager.startLooperTask();
    }

    protected void toDestroyResources(){
        if(ioManager!=null){
            ioManager.destroyLooperTasksAndStreams();
        }
        ioManager=null;
    }

    protected void toChangeResources(InputStream inputStream, OutputStream outputStream){
        if(ioManager!=null){
            ioManager.shutdownLooperTask();
            ioManager=new IOManager(outputStream,inputStream,options,actionDispatcher);
        }else{
            ioManager=new IOManager(outputStream,inputStream,options,actionDispatcher);
        }
        ioManager.startLooperTask();
    }

    public void send(ISendable iSendable){
        if(ioManager!=null){
            ioManager.send(iSendable);
        }
    }

    public void registerReceiver(IActionReceiver receiver){
        actionDispatcher.registerReceiver(receiver);
    }

    public void unRegisterReceiver(IActionReceiver receiver){
        actionDispatcher.unRegisterReceiver(receiver);
    }

    private void sendBroadcast(String action, Serializable args){
        actionDispatcher.sendBroadcast(action,args);
    }

    private void sendBroadcast(String action){
        sendBroadcast(action,null);
    }

}
