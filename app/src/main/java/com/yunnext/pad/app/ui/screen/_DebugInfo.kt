package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yunnext.pad.app.ui.screen.vo.ChildLock
import com.yunnext.pad.app.ui.screen.vo.ChildLockValue
import com.yunnext.pad.app.ui.screen.vo.DebugValue
import com.yunnext.pad.app.ui.screen.vo.DebugVo
import com.yunnext.pad.app.ui.screen.vo.DrinkTemp
import com.yunnext.pad.app.ui.screen.vo.DrinkTempValue
import com.yunnext.pad.app.ui.screen.vo.Eco
import com.yunnext.pad.app.ui.screen.vo.EcoLevel
import com.yunnext.pad.app.ui.screen.vo.EcoValue
import com.yunnext.pad.app.ui.screen.vo.FangShuiMode
import com.yunnext.pad.app.ui.screen.vo.FangShuiModeLevel
import com.yunnext.pad.app.ui.screen.vo.FangShuiModeValue
import com.yunnext.pad.app.ui.screen.vo.HeatTemp
import com.yunnext.pad.app.ui.screen.vo.HeatTempValue
import com.yunnext.pad.app.ui.screen.vo.Heating
import com.yunnext.pad.app.ui.screen.vo.HeatingLevel
import com.yunnext.pad.app.ui.screen.vo.HeatingValue
import com.yunnext.pad.app.ui.screen.vo.PaiKongLevel
import com.yunnext.pad.app.ui.screen.vo.PaiKongTime
import com.yunnext.pad.app.ui.screen.vo.PaiKongTimeValue
import com.yunnext.pad.app.ui.screen.vo.PowerOffTime
import com.yunnext.pad.app.ui.screen.vo.PowerOffTimeValue
import com.yunnext.pad.app.ui.screen.vo.PowerOnTime
import com.yunnext.pad.app.ui.screen.vo.PowerOnTimeValue
import com.yunnext.pad.app.ui.screen.vo.ShaJunSwitch
import com.yunnext.pad.app.ui.screen.vo.ShaJunSwitchValue
import com.yunnext.pad.app.ui.screen.vo.ShuiLiuBaoHuPulse
import com.yunnext.pad.app.ui.screen.vo.ShuiLiuBaoHuPulseValue
import com.yunnext.pad.app.ui.screen.vo.TimeForPower
import com.yunnext.pad.app.ui.screen.vo.TimeForPowerValue

@Composable
internal fun _DebugInfo(
    modifier: Modifier = Modifier,
    list: List<DebugVo>,
    raw: String,
    onClick: (DebugValue) -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.Gray.copy(alpha = .3f))
            .padding(16.dp),

        ) {
        Text("《测试选项》", color = Color.Red)
        val keyboardController = LocalSoftwareKeyboardController.current
        var selected: DebugVo? by remember { mutableStateOf(null) }
        Text("关闭软件盘", color = Color.Red, modifier = Modifier.clickable {


// 关闭键盘
            keyboardController?.hide()
        })
        Box(Modifier.background(Color.White)) {
            ItemValue(selected) {
                onClick(it)
            }
        }

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .wrapContentHeight()
                .heightIn(max = 120.dp)
        ) {
            items(list, {
                it.toString()
            }) { item ->
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    selected = item
                }) {
                    Item(info = item, selected = { item == selected })
                }
            }
        }
        Text(raw, color = Color.White)
//        LazyRow(
//            modifier = Modifier,
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(list, {
//                it.toString()
//            }) { item ->
//                Button(onClick = {
//                    selected = item
//                }) {
//                    Item(info = item, selected = { item == selected })
//                }
//            }
//        }


        // uart inputs
    }
}


@Composable
private fun ItemValue(item: DebugVo?, onClick: (DebugValue) -> Unit) {
    when (item) {
        ChildLock -> {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("开启", modifier = Modifier.clickable {
                    onClick(ChildLockValue(true))
                })

                Text("关闭", modifier = Modifier.clickable {
                    onClick(ChildLockValue(false))
                })
            }
        }

        DrinkTemp -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var temp by remember {
                mutableIntStateOf(0)
            }
            TextField(
                value = "$temp", onValueChange = {
                    try {
                        temp = it.toInt()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Text("确定",
                color = if (temp in 45..90) Color.Black else Color.Red,
                modifier = Modifier.clickable(
                    temp in 45..90
                ) {
                    onClick(DrinkTempValue(temp))
                })
        }

        Eco -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(EcoLevel.L0.name, modifier = Modifier.clickable {
                onClick(EcoValue(EcoLevel.L0))
            })

            Text(EcoLevel.L1.name, modifier = Modifier.clickable {
                onClick(EcoValue(EcoLevel.L1))
            })
            Text(EcoLevel.L2.name, modifier = Modifier.clickable {
                onClick(EcoValue(EcoLevel.L2))
            })
            Text(EcoLevel.L3.name, modifier = Modifier.clickable {
                onClick(EcoValue(EcoLevel.L3))
            })
        }

        FangShuiMode -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("单", modifier = Modifier.clickable {
                onClick(FangShuiModeValue(FangShuiModeLevel.Mode1))
            })

            Text("双", modifier = Modifier.clickable {
                onClick(FangShuiModeValue(FangShuiModeLevel.Mode2))
            })

        }

        Heating -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(HeatingLevel.L0.value.toString(), modifier = Modifier.clickable {
                onClick(HeatingValue(HeatingLevel.L0))
            })

            Text(HeatingLevel.L2.value.toString(), modifier = Modifier.clickable {
                onClick(HeatingValue(HeatingLevel.L2))
            })
            Text(HeatingLevel.L4.value.toString(), modifier = Modifier.clickable {
                onClick(HeatingValue(HeatingLevel.L4))
            })
            Text(HeatingLevel.L6.value.toString(), modifier = Modifier.clickable {
                onClick(HeatingValue(HeatingLevel.L6))
            })
            Text(HeatingLevel.L8.value.toString(), modifier = Modifier.clickable {
                onClick(HeatingValue(HeatingLevel.L8))
            })

        }

        PaiKongTime -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var temp by remember {
                mutableIntStateOf(0)
            }
            TextField(
                value = "$temp", onValueChange = {
                    try {
                        temp = it.toInt()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Text("确定",
                color = if (temp > 10 || temp < 0) Color.Red else Color.Black,
                modifier = Modifier.clickable(
                    enabled = !(temp > 10 || temp < 0)
                ) {
                    if (temp > 10 || temp < 0) return@clickable
                    onClick(PaiKongTimeValue(PaiKongLevel.Normal(temp)))
                })

            Text(PaiKongLevel.L10.value.toString(), modifier = Modifier.clickable {
                onClick(PaiKongTimeValue(PaiKongLevel.L10))
            })

            Text(PaiKongLevel.L20.value.toString(), modifier = Modifier.clickable {
                onClick(PaiKongTimeValue(PaiKongLevel.L20))
            })

            Text(PaiKongLevel.L30.value.toString(), modifier = Modifier.clickable {
                onClick(PaiKongTimeValue(PaiKongLevel.L30))
            })

        }

        PowerOffTime -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                var hour by remember {
                    mutableIntStateOf(0)
                }
                TextField(
                    value = "$hour", onValueChange = {
                        try {
                            hour = it.toInt()
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }, keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Text("时")

                var min by remember {
                    mutableIntStateOf(0)
                }
                TextField(
                    value = "$min", onValueChange = {
                        try {
                            min = it.toInt()
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }, keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Text("分")


                Text("确定",
                    color = if (hour > 23 || hour < 0 || min < 0 || min > 59) Color.Red else Color.Black,
                    modifier = Modifier.clickable(
                        enabled = !(hour > 23 || hour < 0 || min < 0 || min > 59)
                    ) {
                        if (hour > 23 || hour < 0 || min < 0 || min > 59) return@clickable
                        onClick(PowerOffTimeValue(hour * 60 + min))
                    })
            }
        }

        PowerOnTime -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                var hour by remember {
                    mutableIntStateOf(0)
                }
                TextField(
                    value = "$hour", onValueChange = {
                        try {
                            hour = it.toInt()
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }, keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Text("时")

                var min by remember {
                    mutableIntStateOf(0)
                }
                TextField(
                    value = "$min", onValueChange = {
                        try {
                            min = it.toInt()
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }, keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Text("分")

                Text("确定",
                    color = if (hour > 23 || hour < 0 || min < 0 || min > 59) Color.Red else Color.Black,
                    modifier = Modifier.clickable(
                        enabled = !(hour > 23 || hour < 0 || min < 0 || min > 59)
                    ) {
                        if (hour > 23 || hour < 0 || min < 0 || min > 59) return@clickable
                        onClick(PowerOnTimeValue(hour * 60 + min))
                    })
            }
        }

        ShaJunSwitch -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("开启", modifier = Modifier.clickable {
                onClick(ShaJunSwitchValue(true))
            })

            Text("关闭", modifier = Modifier.clickable {
                onClick(ShaJunSwitchValue(false))
            })
        }

        ShuiLiuBaoHuPulse -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                var level by remember {
                    mutableIntStateOf(0)
                }
                TextField(
                    value = "$level", onValueChange = {
                        try {
                            level = it.toInt()
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }, keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Text(
                    "确定",
                    color = if (level < 0 || level > 10) Color.Red else Color.Black,
                    modifier = Modifier.clickable(enabled = !(level < 0 || level > 10)) {
                        if (level < 0 || level > 10) return@clickable
                        onClick(ShuiLiuBaoHuPulseValue(level))
                    })
            }
        }

        TimeForPower -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("开启", modifier = Modifier.clickable {
                onClick(TimeForPowerValue(true))
            })

            Text("关闭", modifier = Modifier.clickable {
                onClick(TimeForPowerValue(false))
            })
        }

        null -> {
            Text("请选择发送项", color = Color.Red)
        }

        HeatTemp -> Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var temp by remember {
                mutableIntStateOf(0)
            }
            TextField(
                value = "$temp", onValueChange = {
                    try {
                        temp = it.toInt()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Text("确定",
                color = if (temp in 50..97) Color.Black else Color.Red,
                modifier = Modifier.clickable(
                    temp in 50..97
                ) {
                    onClick(HeatTempValue(temp))
                })
        }
    }
}

@Composable
private fun Item(modifier: Modifier = Modifier, info: DebugVo, selected: () -> Boolean) {

    Text(
        modifier = modifier.fillMaxWidth(),
        text = info.text,
        color = if (selected()) Color.Red else Color.Black
    )
}