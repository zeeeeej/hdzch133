package com.bugull.okstream.delivery;

import com.bugull.okstream.pojo.SerialData;

/**
 * Created by cj on 3/12/21.
 */
public interface IActionReceiver {
    void onReceive(SerialData serialData);
}
