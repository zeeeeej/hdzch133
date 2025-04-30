package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yunnext.pad.app.R
import com.yunnext.pad.app.ui.screen.compontent.ComposeIcon
import com.yunnext.pad.app.ui.screen.compontent.ImageComponent
import com.yunnext.pad.app.ui.screen.vm.HomeState
import com.yunnext.pad.app.ui.screen.vm.HomeViewModel

private const val DEBUG = false

@Composable
fun HomeScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues) {
    val vm: HomeViewModel = viewModel()
    val state by vm.state.collectAsStateWithLifecycle(HomeState.DEFAULT)

    Box(
        modifier = modifier.background(Color.DarkGray)
    ) {
        ImageComponent(Modifier.fillMaxSize(), ComposeIcon.Local(R.drawable.ic_pad_bg_pic))

        Box(
            Modifier
                .fillMaxSize()
                .padding(start = 38.dp, end = 28.dp)
                .drawBehind {
                    if (DEBUG) {
                        val width = size.width
                        val height = size.height
                        drawLine(
                            color = Color.Red,
                            start = Offset(width / 2, 0f),
                            end = Offset(width / 2, height),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = null,
                            alpha = 1.0f
                        )

                        drawLine(
                            color = Color.Red,
                            start = Offset(0f, height / 2),
                            end = Offset(width, height / 2),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = null,
                            alpha = 1.0f
                        )
                    }
                }
            //.safeContentPadding()
        ) {

            DateTimeInfo(
                modifier = Modifier
                    //.border(1.dp, color = Color.Red)
                    .align(Alignment.TopStart)
                    .padding(top = 14.dp, start = 16.dp),
                state.dateTimeInfo
            )

            WifiInfo(
                modifier = Modifier
                    //.border(1.dp, color = Color.Red)
                    .align(Alignment.TopEnd)
                    .padding(top = 14.dp, end = 16.dp), state.wifiInfo
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LeftInfo(
                    modifier = Modifier
                        //.border(1.dp, color = Color.Red)
                        .weight(1f), state.leftInfo
                )

                CenterInfo(
                    modifier = Modifier
                    //.border(1.dp, color = Color.Yellow)
                    ,
                    info = state.tempInfo, state.statusListInfo
                )

                RightInfo(
                    modifier = Modifier
                        //.border(1.dp, color = Color.Red)
                        .weight(1f), state.rightInfo
                )
            }

        }
    }

}







