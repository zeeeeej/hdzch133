package com.yunnext.pad.app.ui.screen.vo

sealed interface Level {
    data object NaN : Level
    data class Signal(val value: Int) : Level
}