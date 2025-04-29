package com.yunnext.pad.app.util

import android.util.Log

class XLog {
    companion object {
        private const val TAG = "xlog"
        fun d(tag: String, msg: String) {
            Log.d(tag, msg)
        }

        fun d(msg: String) {
            Log.d(TAG, msg)
        }
    }
}