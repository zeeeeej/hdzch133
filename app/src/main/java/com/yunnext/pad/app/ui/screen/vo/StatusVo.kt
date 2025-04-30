package com.yunnext.pad.app.ui.screen.vo

import com.yunnext.pad.app.R
import com.yunnext.pad.app.ui.screen.vo.Status.*

data class StatusVo(val status: Status, val text: String, val res: Int)

enum class Status {
    JiaRe,
    ZhiShui,
    QueShui,
    JieNeng,
    HuanXin,
    ShaJun,
    YinYong
    ;
}

val Status.text: String
    get() = when (this) {
        JiaRe -> "加热"
        ZhiShui -> "制水"
        QueShui -> "缺水"
        JieNeng -> "节能"
        HuanXin -> "换芯"
        ShaJun -> "杀菌"
        YinYong -> "饮用"
    }

val Status.res: Int
    get() = when (this) {
        JiaRe -> R.drawable.ic_pad_heating
        ZhiShui -> R.drawable.ic_pad_water_production
        QueShui -> R.drawable.ic_pad_lack_wate
        JieNeng -> R.drawable.ic_pad_eco
        HuanXin -> R.drawable.ic_pad_renew_cartridge
        ShaJun -> R.drawable.ic_pad_sterilize
        YinYong -> R.drawable.ic_pad_drinking
    }


