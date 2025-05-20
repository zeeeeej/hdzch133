package com.yunnext.pad.app.ui.screen.vo

sealed interface UartVo

data class UartUp(val data:String):UartVo
data class UartDown(val data:String):UartVo