package com.yunnext.pad.app.repo.uart

import com.yunnext.pad.app.repo.ble.toInt
import com.yunnext.pad.app.ui.screen.vo.Level
import java.lang.IllegalStateException

sealed interface UartUp {
    data object NaN : UartUp
    data class OnOffUp(val value: Boolean) : UartUp
    data class WifiUp(val value: Level) : UartUp
    data class JiaReUp(val value: Boolean) : UartUp
    data class ShaJunUp(val value: Boolean) : UartUp
    data class ZhiShuiUp(val value: Boolean) : UartUp
    data class QueShuiUp(val value: Boolean) : UartUp
    data class JieNengUp(val value: Boolean) : UartUp
    data class ServiceDaysUp(val value: Int) : UartUp
    data class SavedBottlesUp(val value: Int) : UartUp
    data class ChuShuiUp(val value: ChuSHuiType) : UartUp
    data class TemperatureUp(val value: Int) : UartUp

    //
    data class FilterUp(val value: Int) : UartUp
    data class ChildLockUp(val value: Boolean) : UartUp
    data class ErrorUp(val value: UartError) : UartUp


    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        @Throws
        fun parse(data: ByteArray): UartUp {
            try {
                val checkSize = SerialProtocol.checkSize(data = data)
                require(checkSize) {
                    "size error! size = ${data.size}"
                }

                val checkHeaderAndTail = SerialProtocol.checkHeaderAndTail(data = data)
                require(checkHeaderAndTail) {
                    "head or tail error!"
                }

                val checkCRC = SerialProtocol.checkCRC(data = data)
                require(checkCRC) {
                    "crc error!"
                }

                val checkCMD = SerialProtocol.checkCMD(data = data)
                require(checkCRC) {
                    "cmd error!"
                }
                val cmd = SerialProtocol.parseCmd(data = data)
                val parsePayload = SerialProtocol.parsePayload(data = data)
                val up = when (cmd) {
                    0x00 -> OnOffUp(parsePayload[0].toInt() == 0x01)
                    0x01 -> {
                        val value = parsePayload[0].toInt()
                        WifiUp(Level.from(value))
                    }

                    0x02 -> {
                        val value = parsePayload[0].toInt()
                        FilterUp(value)
                    }

                    0x03 -> JiaReUp(parsePayload[0].toInt() == 0x01)
                    0x04 -> ShaJunUp(parsePayload[0].toInt() == 0x01)
                    0x05 -> ZhiShuiUp(parsePayload[0].toInt() == 0x01)
                    0x06 -> QueShuiUp(parsePayload[0].toInt() == 0x01)
                    0x07 -> JieNengUp(parsePayload[0].toInt() == 0x01)
                    0x08 -> {
                        val value = parsePayload.toInt()
                        ServiceDaysUp(value)

                    }

                    0x09 -> {
                        val value = parsePayload.toInt()
                        SavedBottlesUp(value)

                    }

                    0x0A -> {
                        // 出水
                        val type: ChuSHuiType = when (val value = parsePayload.toInt()) {
                            0 -> ChuSHuiType.NaN
                            1 -> ChuSHuiType.Hot
                            2 -> ChuSHuiType.Cold
                            else -> throw IllegalStateException("不支持的出水值$:$value")
                        }
                        ChuShuiUp(type)
                    }

                    0x0C -> {
                        // 未按解锁键
                        TODO()
                    }

                    0x0D -> {
                        val value = parsePayload.toInt()
                        TemperatureUp(value)
                    }

                    0x0E -> {
                        val value = parsePayload.toInt()
                        // todo
                        val error: UartError = UartError.Other(value)
                        ErrorUp(error)
                    }

                    else -> {
                        NaN
                    }
                }
                return up
            } catch (e: Exception) {
                throw IllegalStateException("UartUp::parse error : $e data is ${data.toHexString()}")
            }
        }
    }
}


enum class ChuSHuiType {
    Hot, Cold, NaN;
}

sealed interface UartError {
    data object E02 : UartError
    data object E05 : UartError
    data object E07 : UartError
    data object E09 : UartError
    data object Normal : UartError
    data class Other(val value: Int) : UartError
}

