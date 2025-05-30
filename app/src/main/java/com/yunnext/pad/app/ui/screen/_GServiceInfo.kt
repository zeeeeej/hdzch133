package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunnext.pad.app.ui.screen.vo.GServiceInfoVo
import com.yunnext.pad.app.ui.theme.ColorBlue
import com.yunnext.pad.app.ui.theme.myTextStyle

@Composable
internal fun GServiceInfo(modifier: Modifier = Modifier, info: GServiceInfoVo) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier
                .padding(horizontal = 0.dp),
            text = "${info.value}",
            style = myTextStyle(68.sp, number = true)
        )

        Spacer(Modifier.height(5.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 0.dp),
            text = "G+服务(天)",
            style = myTextStyle(32.sp, color = ColorBlue)
        )
    }
}