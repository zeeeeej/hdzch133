package com.bugull.okstream.iocore;

import com.bugull.okstream.Options;
import com.bugull.okstream.delivery.IStateSender;

import java.io.OutputStream;

/**
 * Created by cj on 3/12/21.
 * 写入器
 */
public interface IWriter {

    void initialize(OutputStream outputStream, IStateSender iStateSender, Options options);

    //关闭流
    void close();

    void write() throws Exception;

    void offer(ISendable iSendable);
}
