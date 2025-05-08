package com.bugull.okstream.iothreads;

import com.bugull.okstream.base.AbsLooperTask;
import com.bugull.okstream.iocore.IReader;

/**
 * Created by cj on 3/12/21.
 */
public class ReadLoopTask extends AbsLooperTask {
    private IReader iReader;

    public ReadLoopTask(IReader iReader) {
        this.iReader = iReader;
    }

    public ReadLoopTask(String name, IReader iReader) {
        super(name);
        this.iReader = iReader;
    }

    @Override
    protected void runInLoopThread() throws Exception {
        iReader.read();
    }

    @Override
    protected void loopFinish(Exception e) {
        if(e!=null){
            e.printStackTrace();
        }
    }
}
