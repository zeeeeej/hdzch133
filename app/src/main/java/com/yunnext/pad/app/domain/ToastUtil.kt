package com.yunnext.pad.app.domain

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.yunnext.pad.app.MyApp

object ToastUtil {
    private var toast: Toast? = null

    fun toast(msg: String?, context: Context? = null) {
        if (msg.isNullOrEmpty()) return
        try {
            toast?.cancel()
            toast = null
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        val temp: Toast = when (context) {
            is Activity -> Toast.makeText(context, msg, Toast.LENGTH_SHORT)
            else -> Toast.makeText(MyApp.CONTEXT, msg, Toast.LENGTH_SHORT)
        }
        temp.setGravity(
            Gravity.TOP,
            0,
             0//(context?.resources?.getDimension(R.dimen.toast_offset)?.toInt() ?: 40)
        )
        toast = temp
        temp.setText(msg)
        temp.show()
    }

}