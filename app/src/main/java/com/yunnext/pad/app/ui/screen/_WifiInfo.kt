package com.yunnext.pad.app.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yunnext.pad.app.ui.screen.compontent.ComposeIcon
import com.yunnext.pad.app.ui.screen.compontent.ImageComponent
import com.yunnext.pad.app.ui.screen.vo.WifiInfoVo
import com.yunnext.pad.app.ui.screen.vo.res

@Composable
internal fun WifiInfo(modifier: Modifier = Modifier, info: WifiInfoVo) {
    ImageComponent(composeIcon = ComposeIcon.Local(info.res), modifier = modifier)
}