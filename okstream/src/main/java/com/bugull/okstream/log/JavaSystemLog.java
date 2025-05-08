package com.bugull.okstream.log;

import com.bugull.okstream.base.Constants;

/**
 * Created by cj on 6/12/21.
 */
public class JavaSystemLog implements ILog{
    @Override
    public void v(String tag, Object arg) {
        System.out.println(tag+":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void v(Object arg) {
        System.out.println(Constants.DEFAULT_LOG_TAG +":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void i(String tag, Object arg) {
        System.out.println(tag+":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void i(Object arg) {
        System.out.println(Constants.DEFAULT_LOG_TAG +":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void d(String tag, Object arg) {
        System.out.println(tag+":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void d(Object arg) {
        System.out.println(Constants.DEFAULT_LOG_TAG +":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void w(String tag, Object arg) {
        System.out.println(tag+":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void w(Object arg) {
        System.out.println(Constants.DEFAULT_LOG_TAG +":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void e(String tag, Object arg) {
        System.out.println(tag+":"+(arg==null?"null":arg.toString()));
    }

    @Override
    public void e(Object arg) {
        System.out.println(Constants.DEFAULT_LOG_TAG +":"+(arg==null?"null":arg.toString()));
    }
}
