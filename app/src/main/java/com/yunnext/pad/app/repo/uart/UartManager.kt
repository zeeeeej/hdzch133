package com.yunnext.pad.app.repo.uart

import com.bugull.okserial.SerialConnectionManager
import com.bugull.okstream.Options
import com.bugull.okstream.delivery.IActionReceiver
import com.bugull.okstream.pojo.SerialData

class UartManager {
    private var _scm: SerialConnectionManager? = null
    private var _recv:((ByteArray)->Unit)? = null
    private val _receiver: IActionReceiver = IActionReceiver { serialData: SerialData? ->
        serialData?.rawData?.let {
            onRawDataChanged(it)
        }
    }

    fun start(recv:(ByteArray)->Unit): Boolean {
        val scm = SerialConnectionManager(
            Options.OptionsBuilder()
                .isDebug(true)
                //.parser(XParser())
                .writeDelay(200)
                .readInterval(200)
                .build(),
            SerialProtocol.UART,
            SerialProtocol.BAUD_RATE
        )
        try {
            val r = scm.open()
            if (r) {
                _recv = recv
                scm.registerReceiver(_receiver)
                _scm = scm
            }
            return r
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

        }
        return false
    }

    fun stop() {
        _recv = null
        _scm?.unRegisterReceiver(_receiver)
        _scm?.close()
        _scm = null
    }

    fun write(data: ByteArray) {
        _scm?.send {
            data
        }
    }

    private fun onRawDataChanged(data: ByteArray) {
        _recv?.invoke(data)
    }
}