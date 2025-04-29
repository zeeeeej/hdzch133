package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunnext.pad.app.R
import com.yunnext.pad.app.ui.screen.compontent.ComposeIcon
import com.yunnext.pad.app.ui.screen.compontent.ImageComponent
import com.yunnext.pad.app.ui.screen.vo.Status
import com.yunnext.pad.app.ui.screen.vo.StatusVo
import com.yunnext.pad.app.ui.theme.myTextStyle

@Composable
internal fun StatusListInfo(modifier: Modifier = Modifier, list: List<StatusVo>) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list, { it.status }) { info ->
            StatusInfo(value = info)
        }
    }
}

@Composable
private fun StatusInfo(modifier: Modifier = Modifier, value: StatusVo) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        ImageComponent(modifier = Modifier.size(72.dp), composeIcon = ComposeIcon.Local(value.res))
        Spacer(Modifier.height(4.dp))
        Text(
            modifier = Modifier
                .border(1.dp, color = Color.Red)
                .drawBehind {

                },
            text = value.text,
            style = myTextStyle(28.sp),
            overflow = TextOverflow.Visible,
            softWrap = false
        )

    }

}