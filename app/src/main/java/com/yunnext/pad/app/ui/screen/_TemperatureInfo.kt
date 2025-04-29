package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.yunnext.pad.app.ui.screen.compontent.NoSpaceText
import com.yunnext.pad.app.ui.theme.myTextStyle

@Composable
internal fun TemperatureInfo(modifier: Modifier = Modifier, temp: Int, unit: String = "℃") {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        NoSpaceText(
            text = unit,
            style = myTextStyle(40.sp, color = Color.White.copy(0f)),
            modifier = Modifier
        )
        NoSpaceText(
            text = "$temp",
            style = myTextStyle(280.sp, color = Color.White),
            modifier = Modifier
        )
        NoSpaceText(
            text = unit,
            style = myTextStyle(40.sp, color = Color.White),
            modifier = Modifier
        )
    }
}
