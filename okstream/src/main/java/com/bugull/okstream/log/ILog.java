package com.bugull.okstream.log;

/**
 * Created by cj on 6/12/21.
 */
public interface ILog {

    void v(String tag,Object arg);
    void v(Object arg);
    void i(String tag,Object arg);
    void i(Object arg);
    void d(String tag,Object arg);
    void d(Object arg);
    void w(String tag,Object arg);
    void w(Object arg);
    void e(String tag,Object arg);
    void e(Object arg);

}
