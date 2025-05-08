package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunnext.pad.app.R
import com.yunnext.pad.app.ui.screen.compontent.ComposeIcon
import com.yunnext.pad.app.ui.screen.compontent.ImageComponent
import com.yunnext.pad.app.ui.screen.vo.BottlesInfoVo
import com.yunnext.pad.app.ui.theme.ColorGray
import com.yunnext.pad.app.ui.theme.myTextStyle

@Composable
internal fun SavedBottlesInfo(modifier: Modifier = Modifier, info: BottlesInfoVo) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            modifier = Modifier.padding(horizontal = 0.dp),
            text = info.show,
            style = myTextStyle(fontSize = 68.sp, number = true)
        )

        Spacer(Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(horizontal = 0.dp),
                text = "累计节约",
                style = myTextStyle(32.sp, color = ColorGray.copy(alpha = .5f))
            )

            ImageComponent(composeIcon = ComposeIcon.Local(R.drawable.ic_pad_bottle_in_text))

            Text(
                modifier = Modifier.padding(horizontal = 0.dp),
                text = "(个)",
                style = myTextStyle(32.sp, color = ColorGray.copy(alpha = .5f))
            )
        }


    }
}

@Composable
internal fun SavedBottlesInfoOld(modifier: Modifier = Modifier, info: BottlesInfoVo) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ImageComponent(composeIcon = ComposeIcon.Local(R.drawable.ic_pad_water_bottle))
        Text(
            modifier = Modifier.padding(horizontal = 0.dp),
            text = info.show,
            style = myTextStyle(fontSize = 68.sp, number = true)
        )

        Text(
            modifier = Modifier.padding(horizontal = 0.dp),
            text = "累计节约(个)",
            style = myTextStyle(32.sp, color = ColorGray.copy(alpha = .5f))
        )
    }
}