package com.bugull.okstream;

import com.bugull.okstream.iocore.IParser;
import com.bugull.okstream.log.AndroidLog;
import com.bugull.okstream.log.EmptyLog;
import com.bugull.okstream.log.ILog;

/**
 * Created by cj on 3/12/21.
 */
public class Options {
    protected boolean isDebug; //调试模式
    protected boolean isDispatchInUiThread; //是否在主线程返回数据
    protected IParser parser; //读取数据的解析器
    protected int writeDelay; //写入延迟
    protected int readInterval;
    protected Integer dispatcherCapacity; //分发队列的容量
    protected ILog log;
    private ILog emptyLog;

    public static Options getDefaultOptions(){
        Options options=new Options();
        options.isDebug=true;
        options.isDispatchInUiThread=false;
        options.parser=null;
        options.writeDelay=50;
        options.dispatcherCapacity=null;
        options.log=new AndroidLog();
        options.emptyLog=new EmptyLog();
        options.readInterval=0;
        return options;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public boolean isDispatchInUiThread() {
        return isDispatchInUiThread;
    }

    public IParser getParser() {
        return parser;
    }

    public int getWriteDelay() {
        return writeDelay;
    }

    public Integer getDispatcherCapacity() {
        return dispatcherCapacity;
    }

    public int getReadInterval() {
        return readInterval;
    }

    public ILog getLog() {
        if(!isDebug()){
            return emptyLog;
        }
        return log;
    }

    public static class OptionsBuilder{
        private Options options;

        public OptionsBuilder() {
            options=Options.getDefaultOptions();
        }

        public OptionsBuilder isDebug(boolean isDebug){
            options.isDebug=isDebug;
            return this;
        }

        public OptionsBuilder isDispatchInUiThread(boolean isDispatchInUiThread){
            options.isDispatchInUiThread=isDispatchInUiThread;
            return this;
        }

        public OptionsBuilder parser(IParser parser){
            options.parser=parser;
            return this;
        }

        public OptionsBuilder writeDelay(int delay){
            options.writeDelay=delay;
            return this;
        }

        public OptionsBuilder dispatcherCapacity(Integer capacity){
            options.dispatcherCapacity=capacity;
            return this;
        }

        public OptionsBuilder log(ILog log){
            options.log=log;
            return this;
        }

        public OptionsBuilder readInterval(int readInterval){
            options.readInterval=readInterval;
            return this;
        }

        public Options build(){
            return options;
        }
    }
}
