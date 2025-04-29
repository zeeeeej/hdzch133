package com.yunnext.pad.app.ui.screen.vo

import com.yunnext.pad.app.R

data class StatusVo(val status: Status, val text: String, val res: Int){
    companion object{
         val debugList = listOf(
            StatusVo(Status.ZhiShui, "制水", R.drawable.ic_pad_water_production),
            StatusVo(Status.JieNeng, "节能", R.drawable.ic_pad_eco),
            StatusVo(Status.ShaJun, "杀菌", R.drawable.ic_pad_sterilize),
        )
    }
}


enum class Status {
    ZhiShui, JieNeng, ShaJun
    ;
}

