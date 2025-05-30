package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.yunnext.pad.app.ui.screen.vo.DateTimeInfoVo
import com.yunnext.pad.app.ui.theme.myTextStyle

@Composable
internal fun DateTimeInfo(modifier: Modifier = Modifier, info: DateTimeInfoVo) {

    val myText: @Composable RowScope.(String, Boolean) -> Unit =
        @Composable { text: String, number: Boolean ->
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 0.dp),
                text = text,
                style = myTextStyle(36.sp, Color.White, number).merge(
                    TextStyle(
                        lineHeight = 1.em,
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    )
                )
            )
        }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        myText(info.year, true)
        myText("年", false)
        myText(info.month, true)
        myText("月", false)
        myText(info.day, true)
        myText("日", false)
        Spacer(Modifier.width(4.dp))
        myText(info.hour, true)
        myText(":", false)
        myText(info.minute, true)
    }
}