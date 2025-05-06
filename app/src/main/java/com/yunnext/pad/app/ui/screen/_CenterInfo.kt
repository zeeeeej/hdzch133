package com.yunnext.pad.app.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yunnext.pad.app.ui.screen.vo.StatusVo
import com.yunnext.pad.app.ui.screen.vo.TempInfoVo

private object Defaults {
    val CENTER_WIDTH = 632.dp
}

@Composable
internal fun CenterInfo(modifier: Modifier, info: TempInfoVo, list: List<StatusVo>) {
    Column(
        modifier = modifier
            .width(Defaults.CENTER_WIDTH),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        AnimatedContent(if (list.isEmpty()) 0.dp else 96.dp, label = "top") {
            Spacer(Modifier.height(it))
        }

        TemperatureInfo(modifier = Modifier.align(Alignment.CenterHorizontally), temp = info.temp)

        AnimatedContent(if (list.isEmpty()) 0.dp else 24.dp, label = "top") {
            Spacer(Modifier.height(it))
        }
        AnimatedVisibility(list.isNotEmpty()) {

            StatusListInfo(
                modifier = Modifier
                    //.border(1.dp, color = Color.Green)
                    .align(Alignment.CenterHorizontally), list = list
            )
        }
    }
}