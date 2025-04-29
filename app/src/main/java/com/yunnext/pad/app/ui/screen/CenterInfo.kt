package com.yunnext.pad.app.ui.screen


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import com.yunnext.pad.app.ui.screen.vo.StatusVo.Companion.debugList
import com.yunnext.pad.app.ui.screen.vo.WifiInfoVo
import com.yunnext.pad.app.ui.theme.ColorBlue
import com.yunnext.pad.app.ui.theme.ColorGray
import com.yunnext.pad.app.ui.theme.myTextStyle

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
        StatusListInfo(
            modifier = Modifier
                .border(1.dp, color = Color.Green)
                .align(Alignment.CenterHorizontally), list = debugList
        )


    }


}