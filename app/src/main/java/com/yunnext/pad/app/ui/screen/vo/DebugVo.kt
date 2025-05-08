package com.yunnext.pad.app.ui.screen.vo

sealed interface DebugVo{
    val text:String
}
data object DebugCase01Vo : DebugVo {
    override val text: String
        get() = "排空"
}

data object DebugCase02Vo : DebugVo {
    override val text: String
        get() = "取消排空"
}

data object DebugCase03Vo : DebugVo {
    override val text: String
        get() = "单按"
}

data object DebugCase04Vo : DebugVo {
    override val text: String
        get() = "双按"
}