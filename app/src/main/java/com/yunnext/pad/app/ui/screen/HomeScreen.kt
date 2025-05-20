package com.yunnext.pad.app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yunnext.pad.app.R
import com.yunnext.pad.app.ui.screen.compontent.ComposeIcon
import com.yunnext.pad.app.ui.screen.compontent.ImageComponent
import com.yunnext.pad.app.ui.screen.vm.HomeState
import com.yunnext.pad.app.ui.screen.vm.HomeViewModel
import com.yunnext.pad.app.ui.screen.vo.DebugVo

private const val DEBUG = false

@OptIn(ExperimentalFoundationApi::class)
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

            var showDebug: Boolean by remember {
                mutableStateOf(false)
            }
            DateTimeInfo(
                modifier = Modifier
                    .combinedClickable(onLongClick = {
                        showDebug = !showDebug
                    }, onLongClickLabel = "c") {

                    }
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
                //<editor-fold desc="Left">
                Column(
                    modifier = Modifier.weight(1f)
                    //.background(color = randomZhongGuoSe().color.copy(.3f))
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    GServiceInfo(
                        modifier = Modifier
                        //.border(1.dp, color = Color.Red)
                        , state.gServiceInfo
                    )

                    Spacer(Modifier.height(100.dp))

                    SavedBottlesInfo(
                        modifier = Modifier
                        //.border(1.dp, color = Color.Red)
                        , state.bottlesInfo
                    )

                }
                //</editor-fold>


                // center
                CenterInfo(
                    modifier = Modifier
                    //.background(color = randomZhongGuoSe().color.copy(.3f))
                    ,
                    info = state.tempInfo, state.statusListInfo
                )

                //<editor-fold desc="Right">
                Column(
                    modifier = Modifier
                        .weight(1f)
                    //.background(color = randomZhongGuoSe().color.copy(.3f))
                    ,

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    QuShuiVolumeInfo(
                        modifier = Modifier
                        //.border(1.dp, color = Color.Red)
                        , state.quShuiVolume
                    )

                    Spacer(Modifier.height(100.dp))

                    QuShuiCountInfo(
                        modifier = Modifier
                        //.border(1.dp, color = Color.Red)
                        , state.quShuiCount
                    )
                }
                //</editor-fold>
            }

            AnimatedVisibility(modifier = Modifier.fillMaxWidth(), visible = showDebug) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)) {
                    _DebugInfo(modifier = Modifier.fillMaxWidth(), list = state.debug) {
                        vm.debug(it)
                    }
                    Text("清空", color = Color.Red,modifier = Modifier.clickable {
                        vm.clearUart()
                    })

                    _UartInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray.copy(alpha = .3f))
                            .padding(16.dp), list = state.inputList
                    )

                }

            }
        }
    }

}









