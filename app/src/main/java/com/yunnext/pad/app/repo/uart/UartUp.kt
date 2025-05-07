package com.yunnext.pad.app.repo.uart

import com.yunnext.pad.app.ui.screen.vo.Level
import java.lang.IllegalStateException

sealed interface UartUp {
    val cmd:UartUpCmd
    data class OnOffUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.OnOffCmd
    }

    data class WifiUp(val value: Level) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.WiFiCmd
    }
    data class JiaReUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.JiaReCmd
    }
    data class ShaJunUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.ShaJunCmd
    }
    data class ZhiShuiUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.ZhiShuiCmd
    }
    data class QueShuiUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.QueShuiCmd
    }
    data class JieNengUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.JieNengCmd
    }
    data class ServiceDaysUp(val value: Int) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.GServiceDaysCmd
    }
    data class SavedBottlesUp(val value: Int) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.SavedBottlesCmd
    }
    data class ChuShuiUp(val value: ChuSHuiType) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.ChuShuiCmd
    }
    data class TemperatureUp(val value: Int) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.TemperatureCmd
    }

    //
    data class FilterUp(val value: Int) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.FilterCmd
    }
    data class ChildLockUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.NotPressLock
    }
    data class ErrorUp(val value: UartError) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.ErrorCmd
    }


    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        @Throws
        fun decode(data: ByteArray): UartUp {
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
                require(checkCMD) {
                    "cmd error!"
                }
                val cmd = SerialProtocol.parseCmd(data = data).toUpCmd()
                val parsePayload = SerialProtocol.parsePayload(data = data)

                val up = when (cmd) {
                    UartUpCmd.OnOffCmd -> OnOffUp(parsePayload[0].toInt() == 0x01)
                    UartUpCmd.WiFiCmd -> {
                        val value = parsePayload[0].toInt()
                        WifiUp(Level.from(value))
                    }

                    UartUpCmd.FilterCmd -> {
                        val value = parsePayload[0].toInt()
                        FilterUp(value)
                    }

                    UartUpCmd.JiaReCmd -> JiaReUp(parsePayload[0].toInt() == 0x01)
                    UartUpCmd.ShaJunCmd -> ShaJunUp(parsePayload[0].toInt() == 0x01)
                    UartUpCmd.ZhiShuiCmd -> ZhiShuiUp(parsePayload[0].toInt() == 0x01)
                    UartUpCmd.QueShuiCmd -> QueShuiUp(parsePayload[0].toInt() == 0x01)
                    UartUpCmd.JieNengCmd -> JieNengUp(parsePayload[0].toInt() == 0x01)
                    UartUpCmd.GServiceDaysCmd -> {
                        val value = parsePayload.toInt()
                        ServiceDaysUp(value)

                    }

                    UartUpCmd.SavedBottlesCmd -> {
                        val value = parsePayload.toInt()
                        SavedBottlesUp(value)

                    }

                    UartUpCmd.ChuShuiCmd -> {
                        // 出水
                        val type: ChuSHuiType = when (val value = parsePayload.toInt()) {
                            0 -> ChuSHuiType.NaN
                            1 -> ChuSHuiType.Hot
                            2 -> ChuSHuiType.Cold
                            else -> throw IllegalStateException("不支持的出水值$:$value")
                        }
                        ChuShuiUp(type)
                    }

                    UartUpCmd.NotPressLock -> {
                        // 未按解锁键
                        TODO()
                    }

                    UartUpCmd.TemperatureCmd -> {
                        val value = parsePayload.toInt()
                        TemperatureUp(value)
                    }

                    UartUpCmd.ErrorCmd -> {
                        val value = parsePayload.toInt()
                        // todo
                        val error: UartError = UartError.Other(value)
                        ErrorUp(error)
                    }
                }
                return up
            } catch (e: Exception) {
                throw e
                //throw IllegalStateException("UartUp::parse error : $e data is ${data.toHexString()}")
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

interface UartCmd {
    val value: Byte
}

enum class UartUpCmd(override val value: Byte) : UartCmd {
    OnOffCmd(0x00),
    WiFiCmd(0x01),
    FilterCmd(0x02),
    JiaReCmd(0x03),
    ShaJunCmd(0x04),
    ZhiShuiCmd(0x05),
    QueShuiCmd(0x06),
    JieNengCmd(0x07),
    GServiceDaysCmd(0x08),
    SavedBottlesCmd(0x09),
    ChuShuiCmd(0x0A),
    NotPressLock(0x0C),
    TemperatureCmd(0x0D),
    ErrorCmd(0x0E)

    ;

}

@OptIn(ExperimentalStdlibApi::class)
fun Byte.toUpCmd(): UartUpCmd {
    return when (this) {
        UartUpCmd.OnOffCmd.value -> UartUpCmd.OnOffCmd
        UartUpCmd.WiFiCmd.value -> UartUpCmd.WiFiCmd
        UartUpCmd.FilterCmd.value -> UartUpCmd.FilterCmd
        UartUpCmd.JiaReCmd.value -> UartUpCmd.JiaReCmd
        UartUpCmd.ShaJunCmd.value -> UartUpCmd.ShaJunCmd
        UartUpCmd.ZhiShuiCmd.value -> UartUpCmd.ZhiShuiCmd
        UartUpCmd.QueShuiCmd.value -> UartUpCmd.QueShuiCmd
        UartUpCmd.JieNengCmd.value -> UartUpCmd.JieNengCmd
        UartUpCmd.GServiceDaysCmd.value -> UartUpCmd.GServiceDaysCmd
        UartUpCmd.SavedBottlesCmd.value -> UartUpCmd.SavedBottlesCmd
        UartUpCmd.ChuShuiCmd.value -> UartUpCmd.ChuShuiCmd
        UartUpCmd.NotPressLock.value -> UartUpCmd.NotPressLock
        UartUpCmd.TemperatureCmd.value -> UartUpCmd.TemperatureCmd
        UartUpCmd.ErrorCmd.value -> UartUpCmd.ErrorCmd
        else -> throw IllegalStateException("不支持的cmd：${this.toHexString()}")
    }
}

fun UartUp.encode(): ByteArray {
    return when (this) {
        is UartUp.ChildLockUp -> {
            TODO()
        }

        is UartUp.ChuShuiUp -> SerialProtocol.create(
            UartUpCmd.ChuShuiCmd.value, when (this.value) {
                ChuSHuiType.Hot -> 0x01
                ChuSHuiType.Cold -> 0x02
                ChuSHuiType.NaN -> 0x00
            }.toByte().toArray()
        )

        is UartUp.ErrorUp -> SerialProtocol.create(
            UartUpCmd.ErrorCmd.value, when (this.value) {
                UartError.E02 -> 0x02
                UartError.E05 -> 0x05
                UartError.E07 -> 0x07
                UartError.E09 -> 0x09
                UartError.Normal -> 0x00
                is UartError.Other -> this.value.value
            }.toByte().toArray()
        )

        is UartUp.FilterUp -> TODO()
        is UartUp.JiaReUp -> SerialProtocol.create(
            UartUpCmd.JiaReCmd.value, (if (this.value) 0x01 else 0x00).toByte().toArray()
        )

        is UartUp.JieNengUp -> SerialProtocol.create(
            UartUpCmd.JieNengCmd.value, (if (this.value) 0x01 else 0x00).toByte().toArray()
        )

        is UartUp.OnOffUp -> SerialProtocol.create(
            UartUpCmd.OnOffCmd.value, (if (this.value) 0x01 else 0x00).toByte().toArray()
        )

        is UartUp.QueShuiUp -> SerialProtocol.create(
            UartUpCmd.QueShuiCmd.value, (if (this.value) 0x01 else 0x00).toByte().toArray()
        )

        is UartUp.SavedBottlesUp -> SerialProtocol.create(
            UartUpCmd.SavedBottlesCmd.value, this.value.toByteArray4()
        )

        is UartUp.ServiceDaysUp -> SerialProtocol.create(
            UartUpCmd.GServiceDaysCmd.value, this.value.toByteArray2()
        )

        is UartUp.ShaJunUp -> SerialProtocol.create(
            UartUpCmd.ShaJunCmd.value, (if (this.value) 0x01 else 0x00).toByte().toArray()
        )

        is UartUp.TemperatureUp -> SerialProtocol.create(
            UartUpCmd.TemperatureCmd.value, (this.value).toByteArray1()
        )

        is UartUp.WifiUp -> SerialProtocol.create(
            UartUpCmd.WiFiCmd.value, when (this.value) {
                Level.NaN -> 0x00
                is Level.Signal -> this.value.value
            }.toByteArray1()
        )

        is UartUp.ZhiShuiUp -> SerialProtocol.create(
            UartUpCmd.ZhiShuiCmd.value, (if (this.value) 0x01 else 0x00).toByte().toArray()
        )
    }
}



