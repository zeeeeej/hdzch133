package com.yunnext.pad.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import com.yunnext.pad.app.domain.runningCurrentProcess
import com.yunnext.pad.app.repo.UserHolder
import com.yunnext.pad.app.repo.http.tokenExpiredChannel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this.applicationContext
        holder = UserHolder(this)

        if (this.applicationContext.runningCurrentProcess) {
            MainScope().launch {
                tokenExpiredChannel.consumeAsFlow().collect() {
                    restartApp(this@MyApp)
                }
            }
        }
    }


    companion object {
        lateinit var holder: UserHolder

        @SuppressLint("StaticFieldLeak")
        lateinit var CONTEXT: Context

        val version: String
            get() = ""

        fun restartApp(context: Context) {
            context.run {
                val intent: Intent = packageManager?.getLaunchIntentForPackage(packageName)?.run {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                } ?: return
                startActivity(intent)
            }
        }

        /* 是否忽略服务器check */
        var IGNORE_CHECK_CODE = false

        const val V2 = true


    }
}




