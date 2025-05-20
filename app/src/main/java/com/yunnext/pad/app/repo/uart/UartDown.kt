package com.yunnext.pad.app.repo.uart

import com.yunnext.pad.app.ui.screen.vo.ChildLock
import com.yunnext.pad.app.ui.screen.vo.DebugValue
import com.yunnext.pad.app.ui.screen.vo.DrinkTemp
import com.yunnext.pad.app.ui.screen.vo.Eco
import com.yunnext.pad.app.ui.screen.vo.FangShuiMode
import com.yunnext.pad.app.ui.screen.vo.HeatTemp
import com.yunnext.pad.app.ui.screen.vo.Heating
import com.yunnext.pad.app.ui.screen.vo.PaiKongTime
import com.yunnext.pad.app.ui.screen.vo.PowerOffTime
import com.yunnext.pad.app.ui.screen.vo.PowerOnTime
import com.yunnext.pad.app.ui.screen.vo.ShaJunSwitch
import com.yunnext.pad.app.ui.screen.vo.ShuiLiuBaoHuPulse
import com.yunnext.pad.app.ui.screen.vo.TimeForPower

sealed interface UartDown : Uart {
    override val cmd: UartDownCmd

    data object GetAllDown : UartDown {
        override val cmd: UartDownCmd
            get() = UartDownCmd.GetAllCmd

    }


    data class Other(val debugValue: DebugValue) : UartDown {
        override val cmd: UartDownCmd
            get() = when (debugValue.debug) {
                ChildLock -> UartDownCmd.ChildLockCmd
                DrinkTemp -> UartDownCmd.DrinkTempCmd
                Eco -> UartDownCmd.EcoCmd
                FangShuiMode -> UartDownCmd.FangShuiModeCmd
                HeatTemp -> UartDownCmd.HeatTempCmd
                Heating -> UartDownCmd.HeatingCmd
                PaiKongTime -> UartDownCmd.PaiKongTimeCmd
                PowerOffTime -> UartDownCmd.PowerOffTimeCmd
                PowerOnTime -> UartDownCmd.PowerOnTimeCmd
                ShaJunSwitch -> UartDownCmd.ShaJunSwitchCmd
                ShuiLiuBaoHuPulse -> UartDownCmd.ShuiLiuBaoHuPulseCmd
                TimeForPower -> UartDownCmd.TimeForPowerCmd
            }
    }

}

enum class UartDownCmd(override val value: Byte) : UartCmd {
    GetAllCmd(0xFE.toByte()),
    TimeForPowerCmd(0x06.toByte()),
    PowerOnTimeCmd(0x07.toByte()),
    PowerOffTimeCmd(0x08.toByte()),
    EcoCmd(0x09.toByte()),
    FangShuiModeCmd(0x11.toByte()),
    ChildLockCmd(0x12.toByte()),
    DrinkTempCmd(0x21.toByte()),
    HeatTempCmd(0x22.toByte()),
    HeatingCmd(0x23.toByte()),
    PaiKongTimeCmd(0x27.toByte()),
    ShuiLiuBaoHuPulseCmd(0x28.toByte()),
    ShaJunSwitchCmd(0x50.toByte()),
    ;
}


fun UartDown.encode(): ByteArray {
    return when (this) {
        UartDown.GetAllDown -> SerialProtocol.create(
            this.cmd.value, byteArrayOf(0)
        )

        is UartDown.Other -> when (val v = this.debugValue) {
            is com.yunnext.pad.app.ui.screen.vo.ChildLockValue -> SerialProtocol.create(
                this.cmd.value, if (v.enable) byteArrayOf(0x01) else  byteArrayOf(0x00)
            )
            is com.yunnext.pad.app.ui.screen.vo.DrinkTempValue -> SerialProtocol.create(
                this.cmd.value,  v.value.toByteArray2()
            )
            is com.yunnext.pad.app.ui.screen.vo.EcoValue -> SerialProtocol.create(
                this.cmd.value,  v.level.value.toByteArray1()
            )
            is com.yunnext.pad.app.ui.screen.vo.FangShuiModeValue -> SerialProtocol.create(
                this.cmd.value,  v.level.value.toByteArray1()
            )
            is com.yunnext.pad.app.ui.screen.vo.HeatTempValue -> SerialProtocol.create(
                this.cmd.value,  v.value.toByteArray1()
            )
            is com.yunnext.pad.app.ui.screen.vo.HeatingValue -> SerialProtocol.create(
                this.cmd.value,  v.level.value.toByteArray1()
            )
            is com.yunnext.pad.app.ui.screen.vo.PaiKongTimeValue -> SerialProtocol.create(
                this.cmd.value,  v.level.value.toByteArray1()
            )
            is com.yunnext.pad.app.ui.screen.vo.PowerOffTimeValue ->SerialProtocol.create(
                this.cmd.value,  v.minute.toByteArray2()
            )
            is com.yunnext.pad.app.ui.screen.vo.PowerOnTimeValue -> SerialProtocol.create(
                this.cmd.value,  v.minute.toByteArray2()
            )
            is com.yunnext.pad.app.ui.screen.vo.ShaJunSwitchValue -> SerialProtocol.create(
                this.cmd.value, if (v.enable) byteArrayOf(0x01) else  byteArrayOf(0x00)
            )
            is com.yunnext.pad.app.ui.screen.vo.ShuiLiuBaoHuPulseValue ->  SerialProtocol.create(
                this.cmd.value,  v.value.toByteArray1()
            )
            is com.yunnext.pad.app.ui.screen.vo.TimeForPowerValue -> SerialProtocol.create(
                this.cmd.value, if (v.enable) byteArrayOf(0x01) else  byteArrayOf(0x00)
            )
        }
    }
}
