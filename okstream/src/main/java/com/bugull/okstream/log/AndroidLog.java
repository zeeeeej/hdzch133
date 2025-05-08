package com.bugull.okstream.log;

import android.util.Log;

import com.bugull.okstream.base.Constants;

/**
 * Created by cj on 6/12/21.
 */
public class AndroidLog implements ILog{

    @Override
    public void v(String tag, Object arg) {
        Log.v(tag,arg==null?"null":arg.toString());
    }

    @Override
    public void v(Object arg) {
        Log.v(Constants.DEFAULT_LOG_TAG,arg==null?"null":arg.toString());
    }

    @Override
    public void i(String tag, Object arg) {
        Log.i(tag,arg==null?"null":arg.toString());
    }

    @Override
    public void i(Object arg) {
        Log.i(Constants.DEFAULT_LOG_TAG,arg==null?"null":arg.toString());
    }

    @Override
    public void d(String tag, Object arg) {
        Log.d(tag,arg==null?"null":arg.toString());
    }

    @Override
    public void d(Object arg) {
        Log.d(Constants.DEFAULT_LOG_TAG,arg==null?"null":arg.toString());
    }

    @Override
    public void w(String tag, Object arg) {
        Log.w(tag,arg==null?"null":arg.toString());
    }

    @Override
    public void w(Object arg) {
        Log.w(Constants.DEFAULT_LOG_TAG,arg==null?"null":arg.toString());
    }

    @Override
    public void e(String tag, Object arg) {
        Log.e(tag,arg==null?"null":arg.toString());
    }

    @Override
    public void e(Object arg) {
        Log.e(Constants.DEFAULT_LOG_TAG,arg==null?"null":arg.toString());
    }
}
