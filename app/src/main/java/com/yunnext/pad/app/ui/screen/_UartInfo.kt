package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yunnext.pad.app.ui.screen.vo.DebugVo
import com.yunnext.pad.app.ui.screen.vo.UartDown
import com.yunnext.pad.app.ui.screen.vo.UartUp
import com.yunnext.pad.app.ui.screen.vo.UartVo

@Composable
internal fun _UartInfo(
    modifier: Modifier = Modifier,
    list: List<UartVo>,
) {
    LazyColumn(
        modifier = modifier.heightIn(max = 400.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(list, { index, item ->
            item.toString() + index.toString()
        }) { _, item ->
            UartItem(info = item)
        }
    }
}

@Composable
private fun UartItem(modifier: Modifier = Modifier, info: UartVo) {

    when (info) {
        is UartDown -> Text(modifier = modifier, text = info.data, color = Color.Yellow)
        is UartUp -> Text(modifier = modifier, text = info.data, color = Color.Red)
    }

}