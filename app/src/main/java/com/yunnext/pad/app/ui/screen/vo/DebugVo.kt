package com.yunnext.pad.app.ui.screen.vo

sealed interface DebugVo{
    val text:String
}

sealed interface DebugValue{
    val debug:DebugVo
}

data class TimeForPowerValue(val enable:Boolean):DebugValue{
    override val debug: DebugVo
        get() = TimeForPower

}
data class PowerOnTimeValue(val minute:Int):DebugValue{
    override val debug: DebugVo
        get() = PowerOnTime

}

data class PowerOffTimeValue(val minute:Int):DebugValue{
    override val debug: DebugVo
        get() = PowerOffTime

}

enum class EcoLevel(val value:Int){
    L1(10),
    L2(20),
    L3(30),
    L0(0)
    ;
}

data class EcoValue(val level:EcoLevel):DebugValue{
    override val debug: DebugVo
        get() = Eco

}

enum class FangShuiModeLevel(val value:Int){
    Mode1(1),
    Mode2(2)
    ;
}

data class FangShuiModeValue(val level:FangShuiModeLevel):DebugValue{
    override val debug: DebugVo
        get() = FangShuiMode

}

data class ChildLockValue(val enable:Boolean):DebugValue{
    override val debug: DebugVo
        get() = ChildLock

}

// 45-90
data class DrinkTempValue(val value:Int):DebugValue{
    override val debug: DebugVo
        get() = DrinkTemp

}

// 50-97
data class HeatTempValue(val value:Int):DebugValue{
    override val debug: DebugVo
        get() = HeatTemp

}


enum class HeatingLevel(val value:Int){
    L0(0),
    L2(2),
    L4(4),
    L6(6),
    L8(8)
    ;
}
data class HeatingValue(val level:HeatingLevel):DebugValue{
    override val debug: DebugVo
        get() = Heating

}

sealed class PaiKongLevel(open val value: Int){
    data class Normal(override val value:Int):PaiKongLevel(value)
    data object L10:PaiKongLevel(10)
    data object L20:PaiKongLevel(20)
    data object L30:PaiKongLevel(20)
}

data class PaiKongTimeValue(val level:PaiKongLevel):DebugValue{
    override val debug: DebugVo
        get() = PaiKongTime

}

// 0-10
data class ShuiLiuBaoHuPulseValue(val value:Int):DebugValue{
    override val debug: DebugVo
        get() = ShuiLiuBaoHuPulse

}

data class ShaJunSwitchValue(val enable:Boolean):DebugValue{
    override val debug: DebugVo
        get() = ShaJunSwitch

}



//data object DebugCase01Vo : DebugVo {
//    override val text: String
//        get() = "排空"
//}
//
//data object DebugCase02Vo : DebugVo {
//    override val text: String
//        get() = "取消排空"
//}
//
//data object DebugCase03Vo : DebugVo {
//    override val text: String
//        get() = "单按"
//}
//
//data object DebugCase04Vo : DebugVo {
//    override val text: String
//        get() = "双按"
//}

val debugList :List<DebugVo> by lazy {
    listOf(
        TimeForPower,
        PowerOnTime,
        PowerOffTime,
        Eco,
        FangShuiMode,
        ChildLock,
        DrinkTemp,
        Heating,
        PaiKongTime,
        ShuiLiuBaoHuPulse,
        ShaJunSwitch
    )
}

data object TimeForPower : DebugVo {
    override val text: String
        get() = "定时开关机"
}

data object PowerOnTime : DebugVo {
    override val text: String
        get() = "开机时间"
}

data object PowerOffTime : DebugVo {
    override val text: String
        get() = "关机时间"
}

data object Eco : DebugVo {
    override val text: String
        get() = "熄屏节能"
}

data object FangShuiMode : DebugVo {
    override val text: String
        get() = "放水模式"
}


data object ChildLock : DebugVo {
    override val text: String
        get() = "童锁"
}

data object DrinkTemp : DebugVo {
    override val text: String
        get() = "饮用温度"
}


data object HeatTemp : DebugVo {
    override val text: String
        get() = "加热温度"
}

data object Heating : DebugVo {
    override val text: String
        get() = "加热保护"
}

data object PaiKongTime : DebugVo {
    override val text: String
        get() = "排空时间"
}

data object ShuiLiuBaoHuPulse : DebugVo {
    override val text: String
        get() = "水流保护流量脉冲"
}

data object ShaJunSwitch : DebugVo {
    override val text: String
        get() = "手动杀菌开关"
}