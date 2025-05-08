package com.bugull.okstream.delivery;

import java.io.Serializable;

/**
 * Created by cj on 3/12/21.
 */
public interface IStateSender {
    void sendBroadcast(String action);
    void sendBroadcast(String action, Serializable serializable);
}
