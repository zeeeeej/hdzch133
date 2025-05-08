package com.bugull.okstream.iothreads;

import com.bugull.okstream.base.AbsLooperTask;
import com.bugull.okstream.iocore.IWriter;

/**
 * Created by cj on 3/12/21.
 */
public class WriteLoopTask extends AbsLooperTask {
    private final IWriter iWriter;

    public WriteLoopTask(IWriter iWriter) {
        this.iWriter = iWriter;
    }

    public WriteLoopTask(String name, IWriter iWriter) {
        super(name);
        this.iWriter = iWriter;
    }

    @Override
    protected void runInLoopThread() throws Exception {
        iWriter.write();
    }

    @Override
    protected void loopFinish(Exception e) {

    }
}
