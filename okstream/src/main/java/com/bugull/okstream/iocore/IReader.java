package com.bugull.okstream.iocore;

import com.bugull.okstream.Options;
import com.bugull.okstream.delivery.IStateSender;

import java.io.InputStream;

/**
 * Created by cj on 3/12/21.
 */
public interface IReader {
    void initialize(InputStream inputStream, IStateSender iStateSender, Options options);
    void close();
    void read() throws Exception;
}
