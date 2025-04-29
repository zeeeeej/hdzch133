//package com.yunnext.pad.app.ui.screen.compontent
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithContent
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.drawscope.translate
//import androidx.compose.ui.layout.onSizeChanged
//import androidx.compose.ui.text.TextLayoutResult
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.drawText
//
//@Composable
//fun TightText(
//    text: String,
//    modifier: Modifier = Modifier,
//    style: TextStyle = TextStyle.Default
//) {
//    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
//    var height by remember { mutableIntStateOf(0) }
//
//    Box(modifier = modifier
//        .onSizeChanged { height = it.height }
//        .drawWithContent {
//            textLayoutResult?.let { layoutResult ->
//                val textHeight = layoutResult.size.height
//                val extraSpace = height - textHeight
//                val topPadding = if (extraSpace > 0) extraSpace / 2f else 0f
//
//                with(layoutResult) {
//                    drawText(
//
//                        topLeft = Offset(0f, -topPadding)
//                    )
//                }
//            }
//        }
//    ) {
//        Text(
//            text = text,
//            style = style,
//            onTextLayout = { textLayoutResult = it },
//            modifier = Modifier
//                .fillMaxSize()
//                .wrapContentHeight(align = Alignment.Top)
//        )
//    }
//}