package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yunnext.pad.app.ui.screen.vo.CenterInfoVo
import com.yunnext.pad.app.ui.screen.vo.StatusVo.Companion.debugList

private object Defaults {
    val CENTER_WIDTH = 632.dp
}

@Composable
internal fun CenterInfo(modifier: Modifier, info: CenterInfoVo) {
    Column(
        modifier = modifier
            .width(Defaults.CENTER_WIDTH),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        TemperatureInfo(modifier = Modifier.align(Alignment.CenterHorizontally), temp = info.temp)
        Spacer(Modifier.height(40.dp))
        StatusListInfo(
            modifier = Modifier
                .border(1.dp, color = Color.Green)
                .align(Alignment.CenterHorizontally), list = debugList
        )
    }
}