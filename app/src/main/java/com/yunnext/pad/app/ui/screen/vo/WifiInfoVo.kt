package com.yunnext.pad.app.ui.screen.vo

import com.yunnext.pad.app.R
import kotlin.random.Random

sealed interface Level {
    data object NaN : Level
    data class Signal(val value: Int) : Level

    companion object {
        internal fun random() = from(Random.nextInt(5))

        fun from(value: Int) = when (value) {
            0 -> Level.Signal(0)
            1 -> Level.Signal(1)
            2 -> Level.Signal(2)
            3 -> Level.Signal(3)
            4 -> Level.Signal(4)
            else -> Level.NaN
        }
    }
}

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