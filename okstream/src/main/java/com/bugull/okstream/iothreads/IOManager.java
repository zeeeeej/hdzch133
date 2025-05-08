package com.bugull.okstream.iothreads;

import com.bugull.okstream.Options;
import com.bugull.okstream.delivery.IStateSender;
import com.bugull.okstream.iocore.IReader;
import com.bugull.okstream.iocore.ISendable;
import com.bugull.okstream.iocore.IWriter;
import com.bugull.okstream.iocore.ReaderImpl;
import com.bugull.okstream.iocore.ReaderWrapper;
import com.bugull.okstream.iocore.WriterImpl;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by cj on 3/12/21.
 */
public class IOManager {
    private final OutputStream outputStream;
    private final InputStream inputStream;
    private IWriter iWriter;
    private IReader iReader;
    private ReadLoopTask readLoopTask;
    private WriteLoopTask writeLoopTask;
    private final Options options;
    private final IStateSender iStateSender;

    public IOManager(OutputStream outputStream, InputStream inputStream, Options options, IStateSender iStateSender) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.options=options;
        this.iStateSender=iStateSender;
        ioinit();
    }

    //初始化读写器
    private void ioinit() {
        iWriter=new WriterImpl();
        iWriter.initialize(outputStream,iStateSender,options);
        iReader=new ReaderWrapper(new ReaderImpl());
        iReader.initialize(inputStream,iStateSender, options);
    }

    public void send(ISendable iSendable){
        if(iWriter!=null) iWriter.offer(iSendable);
    }

    /**
     * 开始运行读写线程
     */
    public void startLooperTask(){
        readLoopTask=new ReadLoopTask(iReader);
        writeLoopTask=new WriteLoopTask(iWriter);
        try {
            readLoopTask.start();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        try {
            writeLoopTask.start();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    //关闭读写线程
    public void shutdownLooperTask(){
        if(readLoopTask!=null){
            try {
                readLoopTask.shutdownSerial();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        if(writeLoopTask!=null){
            try {
                writeLoopTask.shutdownSerial();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void closeReadStreamAndWriteStream(){
        if(iReader!=null){
            iReader.close();
        }
        if(iWriter!=null){
            iWriter.close();
        }
    }

    /**
     * 摧毁所有资源
     */
    public void destroyLooperTasksAndStreams(){
        shutdownLooperTask();
        closeReadStreamAndWriteStream();
    }
}
