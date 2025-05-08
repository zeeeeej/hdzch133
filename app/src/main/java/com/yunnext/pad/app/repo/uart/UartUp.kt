package com.yunnext.pad.app.repo.uart

import com.yunnext.pad.app.ui.screen.vo.Level
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.lang.IllegalStateException

interface Uart {
    val cmd: UartCmd
}

sealed interface UartUp : Uart {
    override val cmd: UartUpCmd

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

    data class QuShuiCountUp(val value: Int) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.QuShuiCountCmd
    }

    data class QuShuiVolumeUp(val value: Int) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.QuShuiVolumeCmd
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

    data class YinYongUp(val value: Boolean) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.YinYongCmd
    }

    data class ErrorUp(val value: UartError) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.ErrorCmd
    }

    data class GetAllUp(val value: List<UartUp>) : UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.GetAllRespCmd

        companion object {
            // 010100 020100 0F07E808091028
            // 010100 020100 0F07E808091028
            @OptIn(ExperimentalStdlibApi::class)
            fun from(data: ByteArray): GetAllUp {
                val list: MutableList<UartUp> = mutableListOf()
                var cmd: Byte
                var flag = true
                var pos = 0
                while (flag && pos<data.size) {
                    //println("----------------------" )
                    cmd = data[pos]
                    //println("data[$pos] cmd = ${cmd}" )
                    try {
                        val encode2UartUpCmd = cmd.encode2UartUpCmd()
                        pos ++
                        val len = data[pos]
                        //println("data[$pos] len = ${len}" )
                        pos++
                        val payloadValue = data.copyOfRange(pos, pos + len)
                        //println("data[${pos}] payloadValue = ${payloadValue.toHexString()}" )
                        list.add(decode(encode2UartUpCmd,payloadValue))
                        pos += len
                    } catch (e: Exception) {
                        e.printStackTrace()
                        flag = false
                    }
                }
                return GetAllUp(list)
            }
        }
    }

    data class DateUp(val year: Int, val month: Int, val day: Int, val hour: Int, val minute: Int) :
        UartUp {
        override val cmd: UartUpCmd
            get() = UartUpCmd.DateCmd

        companion object {
            internal fun from(parsePayload: ByteArray): DateUp {
                // 07e9 05080a27
                val up = DateUp(
                    year = byteArrayOf(parsePayload[0], parsePayload[1]).toInt(),
                    month = parsePayload[2].toInt(),
                    day = parsePayload[3].toInt(),
                    hour = parsePayload[4].toInt(),
                    minute = parsePayload[5].toInt(),
                )
                return up
            }
        }
    }


    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        @Throws
        fun decode(data: ByteArray): UartUp {
            return try {
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
                val cmd = SerialProtocol.parseCmd(data = data).encode2UartUpCmd()
                val parsePayload = SerialProtocol.parsePayload(data = data)

                val up = decode(cmd, parsePayload)
                up
            } catch (e: Exception) {
                throw e
//                throw IllegalStateException(msg= "UartUp::parse error : $e data is ${data.toHexString()}", cause = e)
            }
        }

        internal fun decode(cmd: UartUpCmd, parsePayload: ByteArray): UartUp {
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
                    i("[UP]服务天数:$value")
                    ServiceDaysUp(value)

                }

                UartUpCmd.SavedBottlesCmd -> {
                    val value = parsePayload.toInt()
                    i("[UP]节约瓶子:$value")
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

                UartUpCmd.YinYongCmd -> YinYongUp(parsePayload[0].toInt() == 0x01)
                UartUpCmd.DateCmd -> {
                    DateUp.from(parsePayload)
                }

                UartUpCmd.QuShuiCountCmd -> {
                    val value = parsePayload.toInt()
                    i("[UP]取水次数:$value")
                    QuShuiCountUp(value)
                }

                UartUpCmd.QuShuiVolumeCmd -> {
                    val value = parsePayload.toInt()
                    i("[UP]取水流量:$value")
                    QuShuiVolumeUp(value)
                }

                UartUpCmd.GetAllRespCmd -> {
                    GetAllUp.from(parsePayload)
                }
            }
            return up
        }
    }
}


fun UartUp.DateUp.toTimestamps(): Long {
    val dateTime = LocalDateTime(year, month, day, hour, minute)
    val zone = TimeZone.currentSystemDefault()
    return dateTime.toInstant(zone).toEpochMilliseconds()
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
    QuShuiCountCmd(0x10),
    QuShuiVolumeCmd(0x12),
    YinYongCmd(0x11),
    ChuShuiCmd(0x0A),
    NotPressLock(0x0C),
    TemperatureCmd(0x0D),
    ErrorCmd(0x0E),
    DateCmd(0x0F),
    GetAllRespCmd(0xFE.toByte())

    ;

}

@OptIn(ExperimentalStdlibApi::class)
fun Byte.encode2UartUpCmd(): UartUpCmd {
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
        UartUpCmd.DateCmd.value -> UartUpCmd.DateCmd
        UartUpCmd.QuShuiCountCmd.value -> UartUpCmd.QuShuiCountCmd
        UartUpCmd.QuShuiVolumeCmd.value -> UartUpCmd.QuShuiVolumeCmd
        UartUpCmd.YinYongCmd.value -> UartUpCmd.YinYongCmd
        UartUpCmd.GetAllRespCmd.value -> UartUpCmd.GetAllRespCmd
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

        is UartUp.YinYongUp -> SerialProtocol.create(
            this.cmd.value, (if (this.value) 0x01 else 0x00).toByte().toArray()
        )

        is UartUp.DateUp -> SerialProtocol.create(
            this.cmd.value,
            byteArrayOf(
                this.minute.toByte(),
                this.hour.toByte(),
                this.day.toByte(),
                this.minute.toByte()
            ) + this.year.toByteArray2()
        )

        is UartUp.QuShuiCountUp -> SerialProtocol.create(
            this.cmd.value, (this.value).toByteArray4()
        )

        is UartUp.QuShuiVolumeUp -> SerialProtocol.create(
            this.cmd.value, (this.value).toByteArray4()
        )

        is UartUp.GetAllUp -> TODO()
    }
}



