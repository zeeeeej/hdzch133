package com.yunnext.pad.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.yunnext.pad.app.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


 val numberFontFamily  by lazy {
    FontFamily(
        Font(R.font.num, FontWeight.Normal),
        Font(R.font.num, FontWeight.Bold)
    )
}

fun myTextStyle(fontSize: TextUnit,color:Color =Color.White,number:Boolean = false):TextStyle {
    return if (number){
        TextStyle.Default.copy(
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = numberFontFamily
        )
//            .merge(
//            TextStyle(
//                platformStyle = PlatformTextStyle(
//                    includeFontPadding = false
//                )
//            )
//        )
    }else{
        TextStyle.Default.copy(
            color = color,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
        )
    }
}

val HDText75 = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
    color = Color.White
)

val HDText20 = TextStyle(
    fontSize = 20.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.5.sp,
    fontWeight = FontWeight(400),
    color = Color.White.copy(alpha = .75f)
)


