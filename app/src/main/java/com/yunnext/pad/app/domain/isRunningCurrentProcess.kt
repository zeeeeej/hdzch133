package com.yunnext.pad.app.domain

import android.app.ActivityManager
import android.content.Context

val Context.runningCurrentProcess: Boolean
    get() = this.run {
        val myPid = android.os.Process.myPid()
        getSystemService(ActivityManager::class.java).runningAppProcesses.forEach {
            return@run it.pid == myPid && this.packageName == it.processName
        }
        false
    }