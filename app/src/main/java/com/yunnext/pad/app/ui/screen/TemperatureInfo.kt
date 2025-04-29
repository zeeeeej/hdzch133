package com.yunnext.pad.app.ui.screen


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunnext.pad.app.R
import com.yunnext.pad.app.ui.screen.compontent.ComposeIcon
import com.yunnext.pad.app.ui.screen.compontent.ImageComponent
import com.yunnext.pad.app.ui.screen.vo.CenterInfoVo
import com.yunnext.pad.app.ui.screen.vo.DateTimeInfoVo
import com.yunnext.pad.app.ui.screen.vo.Level
import com.yunnext.pad.app.ui.screen.vo.RightInfoVo
import com.yunnext.pad.app.ui.screen.vo.Status
import com.yunnext.pad.app.ui.screen.vo.StatusVo
import com.yunnext.pad.app.ui.screen.vo.WifiInfoVo
import com.yunnext.pad.app.ui.theme.ColorBlue
import com.yunnext.pad.app.ui.theme.ColorGray
import com.yunnext.pad.app.ui.theme.myTextStyle

@Composable
internal fun TemperatureInfo(modifier: Modifier = Modifier, temp: Int, unit: String = "℃") {
    val textMeasurer = rememberTextMeasurer()
    Row(modifier = modifier) {

//        Text(
//            modifier = Modifier
//                .border(1.dp, color = Color.Red)
//                .drawBehind {
//
//                },
//            text = "",
//            style = TextStyle.Default.copy(
//                color = Color.White,
//                fontSize = 32.sp,
//                fontWeight = FontWeight.Normal,
//                fontFamily = numberFontFamily
//
//            ).merge(
//                TextStyle(
//                    platformStyle = PlatformTextStyle(
//                        includeFontPadding = false
//                    )
//                )
//            ),
//            overflow = TextOverflow.Visible,
//            softWrap = false
//        )

        Spacer(Modifier.weight(1f))
        Text(
            modifier = Modifier
//                .alignBy(LastBaseline)
                .border(1.dp, color = Color.Red)
                .drawBehind {
//                    val cornerTextLayoutResult: TextLayoutResult = textMeasurer.measure(
//                        text = "℃",
//                        style = TextStyle(fontSize = 32.sp)
//                    )
//                    val x = size.width - cornerTextLayoutResult.size.width.toDp().toPx()
//                    val y = 0f
//                    drawText(
//                        textLayoutResult = cornerTextLayoutResult,
//                        color = Color.White,
//                        topLeft = Offset(x, y)
//                    )
                },
            text = "${temp}",
            style = myTextStyle(280.sp, Color.White, true)
                .copy(lineHeight = 0.sp, letterSpacing = 0.sp),
            overflow = TextOverflow.Visible,
            softWrap = false
        )
        Text(
            modifier = Modifier
                .border(1.dp, color = Color.Red)
                .weight(1f)
                .align(Alignment.Top)
//                .alignBy(LastBaseline)
                .alignByBaseline()
                .drawBehind {

                },
            text = unit,
            style = myTextStyle(40.sp),
            overflow = TextOverflow.Visible,
            softWrap = false
        )
//        Box(Modifier.weight(1f)) {
//
//
//            Text(
//                modifier = Modifier
//                    .border(1.dp, color = Color.Red)
//                    .alignByBaseline()
//                    .drawBehind {
//
//                    },
//                text = unit,
//                style = myTextStyle(40.sp),
//                overflow = TextOverflow.Visible,
//                softWrap = false
//            )
//        }
    }
}