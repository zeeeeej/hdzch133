package com.yunnext.pad.app.ui.screen.vo

import com.yunnext.pad.app.R
import java.lang.IllegalStateException
import kotlin.random.Random

sealed interface Level {
    data object NaN : Level
    data class Signal(val value: Int,val dbm:Int) : Level

    companion object {
        internal fun random() = from(Random.nextInt(150))

        fun from(value: Int) =
//            when (value) {
//                in Int.MIN_VALUE..0 -> Level.Signal(0)
//                in 1..7 -> Level.Signal(1)
//                in 8..15 -> Level.Signal(2)
//                in 16..25 -> Level.Signal(3)
//                in 26..31 -> Level.Signal(4)
//                in 32..Int.MAX_VALUE -> Signal(4)
//                else -> throw IllegalStateException("不会走到此分支")
//            }

            when (value) {
                in Int.MIN_VALUE..0 -> Level.Signal(0,value)
                in 1..50 -> Level.Signal(4,value)
                in 51..80 -> Level.Signal(3,value)
                in 81..100 -> Level.Signal(2,value)
                in 101..Int.MAX_VALUE -> Signal(1,value)
                else -> throw IllegalStateException("不会走到此分支")
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