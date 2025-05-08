package com.bugull.okstream.iocore;

import android.util.Log;

import com.bugull.okstream.Options;
import com.bugull.okstream.delivery.IStateSender;
import com.bugull.okstream.log.AndroidLog;
import com.bugull.okstream.log.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by cj on 3/12/21.
 */
public class WriterImpl implements IWriter {
    private OutputStream outputStream;
    private final LinkedBlockingQueue<ISendable> iSendableQueue = new LinkedBlockingQueue<>();
    private IStateSender iStateSender;
    private Options options;

    @Override
    public void initialize(OutputStream outputStream, IStateSender iStateSender, Options options) {
        this.outputStream = outputStream;
        this.options = options;
        this.iStateSender = iStateSender;
    }

    @Override
    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void write() throws Exception {
        try {
            ISendable sendable = iSendableQueue.take();
            if (sendable != null) {
                byte[] data = sendable.parse();
                Utils.print(data,"WriterImpl");
                outputStream.write(data);
                outputStream.flush();
            }
            Thread.sleep(options.getWriteDelay());
        } catch (InterruptedException exception) {
            //ignore
        } catch (Exception e) {
            Log.e("_xpl_","WriterImpl error :"+e);
            if (options != null) options.getLog().e(e);
            e.printStackTrace();
        }
    }

    @Override
    public void offer(ISendable iSendable) {
        if (iSendable == null) return;
        try {
            iSendableQueue.put(iSendable);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
