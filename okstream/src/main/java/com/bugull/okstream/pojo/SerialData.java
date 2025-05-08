package com.bugull.okstream.pojo;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by cj on 3/12/21.
 */
public class SerialData implements Serializable {
    private byte[] rawData;

    public SerialData() {

    }

    public SerialData(byte[] rawData) {
        this.rawData = rawData;
    }

    public byte[] getRawData() {
        return rawData;
    }

    public void setRawData(byte[] rawData) {
        this.rawData = rawData;
    }

    @Override
    public String toString() {
        return "SerialData{" +
                "rawData=" + Arrays.toString(rawData) +
                '}';
    }
}
