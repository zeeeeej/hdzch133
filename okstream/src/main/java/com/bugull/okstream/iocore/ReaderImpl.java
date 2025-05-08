package com.bugull.okstream.iocore;


import android.util.Log;

import com.bugull.okstream.Options;
import com.bugull.okstream.base.Constants;
import com.bugull.okstream.delivery.IStateSender;
import com.bugull.okstream.log.Utils;
import com.bugull.okstream.pojo.SerialData;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cj on 3/12/21.
 */
public class ReaderImpl implements IReader {
    private InputStream inputStream;
    private final ByteBuffer readBuffer = ByteBuffer.wrap(new byte[1024]);
    private IStateSender iStateSender;
    private Options options;
    private IParser parser;

    @Override
    public void initialize(InputStream inputStream, IStateSender iStateSender, Options options) {
        this.inputStream = inputStream;
        this.options = options;
        this.parser = options.getParser();
        this.iStateSender = iStateSender;
    }

    @Override
    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final byte[] temp = new byte[1024];

    @Override
    public void read() throws Exception {
        try {
            //放入
            if (inputStream.available() == 0) {
                if (options.getReadInterval() > 0) {
                    Thread.sleep(options.getReadInterval());
                }
                return;
            }

            int length = inputStream.read(temp);
            if (length == -1) {
                return;
            }
            Utils.print(temp, "ReaderImpl");
            if (parser == null) {
                iStateSender.sendBroadcast(Constants.RECEIVE_DATA_ACTION,
                        new SerialData(Arrays.copyOfRange(temp, 0, length)));
                return;
            }
            if (readBuffer.remaining() < length) {
                List<SerialData> r = parser.parse(readBuffer);
                if (r == null) {
                    readBuffer.clear();
                    return;
                }
                //解析器解析分发后，然后判断数据大小，如果还是太大则清空后放入，如果可以了的话，就放入
                for (SerialData s : r) {
                    iStateSender.sendBroadcast(Constants.RECEIVE_DATA_ACTION, s);
                }
                if (readBuffer.remaining() < length) {
                    readBuffer.clear();
                }
            }
            readBuffer.put(temp, 0, length);
            List<SerialData> r = parser.parse(readBuffer);
            if (r == null) return;
            for (SerialData s : r) {
                iStateSender.sendBroadcast(Constants.RECEIVE_DATA_ACTION, s);
            }
        } catch (Exception e) {
            if (options != null) {
                options.getLog().e(e);
            }
            e.printStackTrace();
        }
    }
}
