package com.bugull.okstream.iocore;

import com.bugull.okstream.pojo.SerialData;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by cj on 3/12/21.
 */
public interface IParser {
    List<SerialData> parse(ByteBuffer byteBuffer);
}
