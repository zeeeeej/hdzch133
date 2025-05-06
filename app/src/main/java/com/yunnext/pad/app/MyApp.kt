package com.yunnext.pad.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import com.yunnext.pad.app.domain.runningCurrentProcess
import com.yunnext.pad.app.repo.DataManager
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
            DataManager.init()
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

        @JvmStatic
        fun reset(context: Context) {
            val metrics = context.resources.displayMetrics
            Log.v("_APP_", "--------设置Density前-----")
            Log.v("_APP_", metrics.toString())
            Log.v("_APP_", "     widthPixels     :   ${metrics.widthPixels}")
            Log.v("_APP_", "     heightPixels    :   ${metrics.heightPixels}")
            Log.v("_APP_", "     density         :   ${metrics.density}")
            Log.v("_APP_", "     xdpi            :   ${metrics.xdpi}")
            Log.v("_APP_", "     ydpi            :   ${metrics.ydpi}")
            Log.v("_APP_", "     densityDpi      :   ${metrics.densityDpi}")
            Log.v("_APP_", "     scaledDensity   :   ${metrics.scaledDensity}")
            Log.v("_APP_", "------------------------")
            val targetDpi = 160
            val targetDensity: Float = metrics.density * (targetDpi * 1f / metrics.densityDpi)
            val targetScaledDensity: Float =
                metrics.density * (targetDpi * 1f / metrics.scaledDensity)
            Log.v("_APP_", "targetDensity = $targetDensity")
            Log.v("_APP_", "------------------------")
            // 设置目标Density
            metrics.density = targetDensity
            metrics.densityDpi = targetDpi
            metrics.scaledDensity = targetScaledDensity
            Log.v("_APP_", "--------设置Density后-----")
            Log.v("_APP_", metrics.toString())
            Log.v("_APP_", "     widthPixels     :   ${metrics.widthPixels}")
            Log.v("_APP_", "     heightPixels    :   ${metrics.heightPixels}")
            Log.v("_APP_", "     density         :   ${metrics.density}")
            Log.v("_APP_", "     xdpi            :   ${metrics.xdpi}")
            Log.v("_APP_", "     ydpi            :   ${metrics.ydpi}")
            Log.v("_APP_", "     densityDpi      :   ${metrics.densityDpi}")
            Log.v("_APP_", "     scaledDensity   :   ${metrics.scaledDensity}")
        }
    }
}




