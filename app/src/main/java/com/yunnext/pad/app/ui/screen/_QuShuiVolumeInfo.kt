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
import com.yunnext.pad.app.ui.screen.vo.BottlesInfoVo
import com.yunnext.pad.app.ui.screen.vo.QuShuiVolumeInfoVo
import com.yunnext.pad.app.ui.theme.ColorGray
import com.yunnext.pad.app.ui.theme.myTextStyle

@Composable
internal fun QuShuiVolumeInfo(modifier: Modifier = Modifier, info: QuShuiVolumeInfoVo) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            modifier = Modifier.padding(horizontal = 0.dp),
            text = info.show,
            style = myTextStyle(fontSize = 68.sp, number = true)
        )

        Spacer(Modifier.height(5.dp))

        Text(
            modifier = Modifier.padding(horizontal = 0.dp),
            text = "取水量(升)",
            style = myTextStyle(32.sp, color = ColorGray.copy(alpha = .5f))
        )


    }
}