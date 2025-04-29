package com.yunnext.pad.app.ui.screen.compontent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.yunnext.pad.app.ui.theme.myTextStyle
import com.yunnext.pad.app.ui.theme.numberFontFamily

@Preview
@Composable
fun NoSpacingTextPreview(modifier: Modifier = Modifier) {
    Row(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        NoSpaceText(
            text = "56",
            style = myTextStyle(280.sp, color = Color.White),
            modifier = Modifier
                .background(Color.DarkGray)
        )
        NoSpaceText(
            text = "℃",
            style = myTextStyle(40.sp, color = Color.White),
            modifier = Modifier
                .background(Color.DarkGray)
        )
    }

}

@Composable
fun NoSpaceText(
    modifier: Modifier = Modifier
        .background(Color.DarkGray)
        .border(1.dp, Color.Red),
    text: String,
    style: TextStyle,
    debug: Boolean = false
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = text,
        style = style
    )
    val content = @Composable {
        Canvas(modifier) {

            if (debug) {
                drawRect(Color.LightGray)

                for (i in 0 until textLayoutResult.lineCount) {
                    drawLine(
                        color = Color.Green,
                        strokeWidth = 2f,
                        start = Offset(0f, textLayoutResult.getLineBaseline(i)),
                        end = Offset(size.width, textLayoutResult.getLineBaseline(i))
                    )
                    drawLine(
                        color = Color.Red,
                        strokeWidth = 2f,
                        start = Offset(0f, textLayoutResult.getLineBottom(i)),
                        end = Offset(size.width, textLayoutResult.getLineBottom(i))
                    )
                    drawLine(
                        color = Color.Blue,
                        strokeWidth = 2f,
                        start = Offset(0f, textLayoutResult.getLineTop(i)),
                        end = Offset(size.width, textLayoutResult.getLineTop(i))
                    )

                    drawLine(
                        color = Color.Yellow,
                        strokeWidth = 2f,
                        start = Offset(
                            0f,
                            textLayoutResult.getLineBottom(i) - textLayoutResult.getLineBaseline(i)
                        ),
                        end = Offset(
                            size.width,
                            textLayoutResult.getLineBottom(i) - textLayoutResult.getLineBaseline(i)
                        )
                    )
                }

                textLayoutResult.placeholderRects.forEach {
                    it?.let { rect ->
                        drawRect(color = Color.Blue, rect.topLeft, rect.size)
                    }
                }
            }
            drawText(textLayoutResult = textLayoutResult)
        }
    }
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        // Set the size of the layout as big as it can
        //layout(constraints.maxWidth, constraints.maxHeight) {
        val offset = 10.dp.roundToPx()
        val spaceBottom =
            textLayoutResult.getLineBottom(0) - textLayoutResult.getLineBaseline(0)
        val spaceTop =
            textLayoutResult.getLineBottom(0) - textLayoutResult.getLineBaseline(0)
        val w = textLayoutResult.size.width
        val h = textLayoutResult.size.height - spaceBottom - spaceTop + offset
        layout(w, h.toInt()) {
            // Track the y co-ord we have placed children up to
            var yPosition = spaceTop - offset

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = -yPosition.toInt())

                // Record the y co-ord placed up to
                yPosition += h
            }
        }
    }
}

@Composable
private fun NoSpacingText(
    text: String,
    modifier: Modifier,
    style: TextStyle
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier) {
        val size = this.size
        println("size = ${size.width}x${size.height}")
        val textLayoutResult = textMeasurer.measure(
            text = text,
            style = style
        )
        drawRect(Color.LightGray)


        for (i in 0 until textLayoutResult.lineCount) {
            drawLine(
                color = Color.Green,
                strokeWidth = 2f,
                start = Offset(0f, textLayoutResult.getLineBaseline(i)),
                end = Offset(size.width, textLayoutResult.getLineBaseline(i))
            )
            drawLine(
                color = Color.Red,
                strokeWidth = 2f,
                start = Offset(0f, textLayoutResult.getLineBottom(i)),
                end = Offset(size.width, textLayoutResult.getLineBottom(i))
            )
            drawLine(
                color = Color.Blue,
                strokeWidth = 2f,
                start = Offset(0f, textLayoutResult.getLineTop(i)),
                end = Offset(size.width, textLayoutResult.getLineTop(i))
            )

            drawLine(
                color = Color.Yellow,
                strokeWidth = 2f,
                start = Offset(
                    0f,
                    textLayoutResult.getLineBottom(i) - textLayoutResult.getLineBaseline(i)
                ),
                end = Offset(
                    size.width,
                    textLayoutResult.getLineBottom(i) - textLayoutResult.getLineBaseline(i)
                )
            )
        }

        textLayoutResult.placeholderRects.forEach {
            it?.let { rect ->
                drawRect(color = Color.Blue, rect.topLeft, rect.size)
            }
        }
        val unitTextLayoutResult = textMeasurer.measure(
            text = "℃",
            style = myTextStyle(40.sp, color = Color.White)
        )

        drawText(textLayoutResult = textLayoutResult)
        drawText(
            textLayoutResult = unitTextLayoutResult,
            topLeft = Offset(
                textLayoutResult.getLineRight(0),
                textLayoutResult.getLineBottom(0) - textLayoutResult.getLineBaseline(0)
                        - (unitTextLayoutResult.getLineBottom(0) - unitTextLayoutResult.getLineBaseline(
                    0
                ))
            )
        )
    }
}

@Preview
@Composable
private fun Preview(modifier: Modifier = Modifier) {
    Row(modifier = Modifier.fillMaxSize()) {
        NoSpacingText(
            "56", modifier = Modifier
//                    .align(Alignment.Top)
                .border(2.dp, Color.Red)
                .background(Color.Yellow), myTextStyle(280.sp, color = Color.Blue, number = true)
        )

        NoSpacingText(
            "读", modifier = Modifier
//                    .align(Alignment.Top)
                .border(2.dp, Color.Red)
                .background(Color.Yellow), myTextStyle(40.sp, color = Color.Blue, number = true)
        )
    }
}


@Preview("123")
@Composable
private fun ComplexAlignmentExample() {
    val TopEdgeAlignmentLine = HorizontalAlignmentLine { measurable, _ ->
        // 对齐到组件顶部
        0
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Blue)
                .alignBy(TopEdgeAlignmentLine)
        )

        Text(
            text = "对齐文本",
            fontSize = 20.sp,
            modifier = Modifier.alignBy(TopEdgeAlignmentLine)
        )

        Text(
            text = "小文本",
            fontSize = 12.sp,
            modifier = Modifier.alignBy(TopEdgeAlignmentLine)
        )
    }
}

@Preview("456", backgroundColor = 0x000000)
@Composable
private fun ComplexAlignmentExample2() {
//    val TopEdgeAlignmentLine = HorizontalAlignmentLine { measurable, _ ->
//        // 对齐到组件顶部
//        0
//    }
//
//
//
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        Box(
//            modifier = Modifier
//                .size(280.dp)
//                .background(Color.Blue)
//                .alignBy(TopEdgeAlignmentLine)
//        )
//
//        Text(
//            modifier = Modifier
//                .alignBy(TopEdgeAlignmentLine)
//                .border(1.dp, color = Color.Red)
//            ,
//            text = "65",
//            style = myTextStyle(280.sp, Color.Black, true).copy(
//                lineHeight = 0.sp,
//                letterSpacing = 0.sp
//            ),
//            overflow = TextOverflow.Visible,
//            softWrap = false
//        )
//        Text(
//            modifier = Modifier
//                .border(1.dp, color = Color.Black)
//                .weight(1f)
//                .alignBy(TopEdgeAlignmentLine)
//            ,
//            text = "-",
//            style = myTextStyle(40.sp,color = Color.Black),
//            overflow = TextOverflow.Visible,
//            softWrap = false
//        )
//    }
    Box(Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "56",
                modifier = Modifier.border(1.dp, Color.Green),
                style = LocalTextStyle.current
                    .copy(fontSize = 255.sp)
                    .merge(
                        TextStyle(
                            lineHeight = 1.em,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Top,
                                trim = LineHeightStyle.Trim.Both
                            )
                        )
                    )
            )

            Text(
                text = "℃",
                modifier = Modifier
                    .border(1.dp, Color.Red)
                    .align(Alignment.Top),
                style = LocalTextStyle.current
                    .copy(fontSize = 40.sp)
                    .merge(
                        TextStyle(
                            lineHeight = 1.em,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Top,
                                trim = LineHeightStyle.Trim.Both
                            )
                        )
                    )
            )
        }
    }

}


private fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp,
) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

@Preview
@Composable
private fun TextWithPaddingToBaseline() {
    MaterialTheme {
        Row {
            Text(
                "65", Modifier
                    .border(1.dp, Color.Green)
                    .firstBaselineToTop(218.dp)
                    .border(1.dp, Color.Red), style = myTextStyle(218.sp, color = Color.Black)
            )

            Text(
                text = "℃", Modifier
                    .border(1.dp, Color.Green)
                    .firstBaselineToTop(40.dp)
                    .border(1.dp, Color.Red), style = myTextStyle(40.sp, color = Color.Black)
            )
        }
    }
}

@Composable
private fun TemperatureInfo2(modifier: Modifier = Modifier, temp: Int, unit: String = "℃") {
    ConstraintLayout() {
        val (tempRef, unitRef) = createRefs()
        Text(
            text = unit,
            style = myTextStyle(40.sp, color = Color.White.copy(1f)),
            modifier = Modifier.constrainAs(unitRef) {
                top.linkTo(tempRef.top)
                start.linkTo(tempRef.end)
            }
        )
        Text(
            text = "$temp",
            style = myTextStyle(280.sp, color = Color.White),

            modifier = Modifier.constrainAs(tempRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
private fun TemperatureInfo3(modifier: Modifier = Modifier, temp: Int, unit: String = "℃") {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
//        NoSpaceText(
//            text = unit,
//            style = myTextStyle(40.sp, color = Color.White.copy(0f)),
//            modifier = Modifier
//        )
//        NoSpaceText(
//            text = "$temp",
//            style = myTextStyle(280.sp, color = Color.White),
//            modifier = Modifier
//        )
//        NoSpaceText(
//            text = unit,
//            style = myTextStyle(40.sp, color = Color.White),
//            modifier = Modifier
//        )

        Text(
            modifier = Modifier.border(1.dp,Color.Red),
            text = buildAnnotatedString {
                withStyle(SpanStyle(color=Color.White,fontSize = 280.sp, fontFamily = numberFontFamily)){
                    append("$temp")
                }
                withStyle(SpanStyle(color=Color.White,fontSize = 40.sp, baselineShift = BaselineShift(11.5f))){
                    append(unit)
                }
            })

    }
}

