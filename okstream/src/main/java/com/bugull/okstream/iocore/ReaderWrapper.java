package com.bugull.okstream.iocore;

import com.bugull.okstream.Options;
import com.bugull.okstream.delivery.IStateSender;

import java.io.InputStream;

/**
 * Created by cj on 3/12/21.
 */
public class ReaderWrapper implements IReader {
    private IReader iReader;

    public ReaderWrapper(IReader iReader) {
        this.iReader = iReader;
    }

    @Override
    public void initialize(InputStream inputStream, IStateSender iStateSender, Options options) {
        iReader.initialize(inputStream,iStateSender,options);
    }

    @Override
    public void close() {
        iReader.close();
    }

    @Override
    public void read() throws Exception {
        iReader.read();
    }
}
