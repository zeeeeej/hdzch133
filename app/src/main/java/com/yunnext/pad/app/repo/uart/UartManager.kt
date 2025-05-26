package com.yunnext.pad.app.repo.uart

import com.bugull.okserial.SerialConnectionManager
import com.bugull.okstream.Options
import com.bugull.okstream.delivery.IActionReceiver
import com.bugull.okstream.pojo.SerialData

class UartManager {
    private var _scm: SerialConnectionManager? = null
    private var _recv: ((Array<ByteArray>,ByteArray,String?) -> Unit)? = null

    @OptIn(ExperimentalStdlibApi::class)
    private val _receiver: IActionReceiver = IActionReceiver { serialData: SerialData? ->
        i("UartManager IActionReceiver: ${serialData?.rawData?.toHexString()}")
        serialData?.rawData?.let {
            // 存在一次读取buffer包含多条协议的情况
            var msg :String? = null
            val data = try {
               SerialProtocol.splitDataByMarkers(it)
            }catch (e:Throwable){
                e.printStackTrace()
                msg = e.localizedMessage?:"splitDataByMarkers error"
                emptyList()
            }

            onRawDataChanged(data = data.toTypedArray(),it,msg)
        }
    }



    fun start(recv: (Array<ByteArray>,ByteArray,String?) -> Unit): Boolean {
        val scm = SerialConnectionManager(
            Options.OptionsBuilder()
                .isDebug(true)

                .writeDelay(100)
                .readInterval(100)
                .build(),
            SerialProtocol.UART,
            SerialProtocol.BAUD_RATE
        )
        // aa 55 0b 09 00 00 01 83 8d 55 bb
        // aa 55 0b 10 00 00 01 83 94 55 bb
        // aa 55 0b 12 00 00 01 82 95 55 bb 0b 12 00 00 01 80 93 55 bb
        // aa 55 08 0d 18 25 55 bb 0d 3b 54 55 bb

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

    private fun onRawDataChanged(data: Array<ByteArray>,raw:ByteArray,error:String?) {
        _recv?.invoke(data,raw,error)
    }
}