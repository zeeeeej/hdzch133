package com.yunnext.pad.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yunnext.pad.app.ui.screen.vo.DebugVo

@Composable
internal fun _DebugInfo(
    modifier: Modifier = Modifier,
    list: List<DebugVo>,
    onClick: (DebugVo) -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.Gray.copy(alpha = .3f))
            .padding(16.dp),

        ) {
        Text("《测试选项》", color = Color.Red)
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(list, {
                it.toString()
            }) { item ->
                Button(onClick = { onClick(item) }) {
                    Item(info = item)
                }
            }
        }
    }
}

@Composable
private fun Item(modifier: Modifier = Modifier, info: DebugVo) {

    Text(modifier = modifier, text = info.text)
}