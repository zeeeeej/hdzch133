package com.yunnext.pad.app.ui.screen.vo

import androidx.compose.runtime.Stable

@Stable
sealed interface Loading {
    object Start:Loading{
        override fun toString(): String {
            return "初始化开始..."
        }
    }
    data class Ing(val msg:String):Loading{
        override fun toString(): String {
            return "初始化中 $msg"
        }
    }
    object Completed:Loading{
        override fun toString(): String {
            return "初始化完毕！"
        }
    }
}