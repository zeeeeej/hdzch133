package com.yunnext.pad.app.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import color
import com.yunnext.pad.app.ui.screen.compontent.NoSpaceText
import com.yunnext.pad.app.ui.theme.myTextStyle
import randomZhongGuoSe

@Composable
internal fun TemperatureInfo(modifier: Modifier = Modifier, temp: Int, unit: String = "℃") {
    Row(modifier = modifier
             //.background(randomZhongGuoSe().color.copy(alpha = .5f))
        , horizontalArrangement = Arrangement.Center) {
        NoSpaceText(
            text = unit,
            style = myTextStyle(40.sp, color = Color.White.copy(0f),number = true),
            modifier = Modifier
        )
        AnimatedContent(temp, label = "temp") {
            NoSpaceText(
                text = "$it",
                style = myTextStyle(280.sp, color = Color.White,number = true),
                modifier = Modifier.border(1.dp,Color.Red)
            )
        }

        NoSpaceText(
            text = unit,
            style = myTextStyle(40.sp, color = Color.White,number = false),
            modifier = Modifier
        )
    }
}
