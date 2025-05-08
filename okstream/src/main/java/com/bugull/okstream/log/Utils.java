package com.bugull.okstream.log;

import android.util.Log;

public class Utils {
    public static void print(byte[] data, String tag) {
        StringBuilder sb = new StringBuilder();
        for (byte datum : data) {
            sb.append(String.format("%02x ", datum));
        }
        Log.i("_xpl_" + tag, sb.toString());
    }
}
