package com.yunnext.pad.app.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TightTextCanvas(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier.wrapContentSize()) {
        drawRect(Color.Yellow.copy(alpha = .25f))

        val textStyle = style.copy(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        )
        val cornerTextLayoutResult: TextLayoutResult = textMeasurer.measure(
            text = text,
            style = textStyle.copy(fontSize = 100.sp)
        )
        val x = size.width  - cornerTextLayoutResult.size.width.toDp().toPx()
        val y = 0f
        drawText(
            textLayoutResult = cornerTextLayoutResult,
            color = Color.White,
            topLeft = Offset(x, y)
        )



//        drawText(
//            text = text,
//            style = textStyle,
//            topLeft = Offset(0f, -textStyle.fontSize / 4), // 调整这个值以达到完美对齐
//            size = size
//        )
    }
}