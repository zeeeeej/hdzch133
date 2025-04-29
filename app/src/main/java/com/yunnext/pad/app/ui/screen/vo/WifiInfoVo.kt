package com.yunnext.pad.app.ui.screen.vo

import com.yunnext.pad.app.R

data class WifiInfoVo(val level: Level)

internal val WifiInfoVo.res: Int
    get() = when (level) {
        Level.NaN -> R.drawable.ic_pad_no_signal
        is Level.Signal -> when (level.value) {
            0 -> R.drawable.ic_pad_signal_0
            1 -> R.drawable.ic_pad_signal_1
            2 -> R.drawable.ic_pad_signal_2
            3 -> R.drawable.ic_pad_signal_3
            4 -> R.drawable.ic_pad_signal_4
            else -> R.drawable.ic_pad_no_signal
        }
    }