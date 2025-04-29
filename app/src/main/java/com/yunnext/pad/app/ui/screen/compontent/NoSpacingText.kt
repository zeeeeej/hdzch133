package com.yunnext.pad.app.ui.screen.compontent

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp

@Composable
fun NoSpacingText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current.copy(fontSize = 100.sp)
) {
    val textMeasurer = rememberTextMeasurer()

    Layout(
        content = {},
        modifier = modifier
    ) { _, constraints ->
        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = style.copy(lineHeight = 0.sp)
        )

        layout(
            width = textLayoutResult.size.width,
            height = textLayoutResult.size.height
        ) {
//            textLayoutResult.multiParagraph.paint(
//                canvas = drawContext.canvas,
//                offset = Offset.Zero
//            )

        }
    }
}