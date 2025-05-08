package com.bugull.okserial;

import com.bugull.okstream.Options;
import com.bugull.okstream.connection.AbConnectionManager;

import java.io.File;
import java.io.IOException;

import android_serialport_api.SerialPort;

/**
 * Created by cj on 4/12/21.
 */
public class SerialConnectionManager extends AbConnectionManager {
    private SerialPort mSerialPort;
    private String path;
    private int baundRate;

    public SerialConnectionManager(Options options, String path, int baundRate) {
        super(options);
        this.path = path;
        this.baundRate = baundRate;
    }

    public boolean open() {
        if (mSerialPort != null) {
            return false;
        }
        try {
            mSerialPort = new SerialPort(new File(path), baundRate, 0);
            afterResourcesLoaded(mSerialPort.getInputStream(), mSerialPort.getOutputStream());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void exchange() {
        toChangeResources(mSerialPort.getInputStream(), mSerialPort.getOutputStream());
    }

    public void close() {
        if (mSerialPort == null) return;
        toDestroyResources();
        if (mSerialPort != null) {
            mSerialPort.close();
        }
        mSerialPort = null;
    }
}
