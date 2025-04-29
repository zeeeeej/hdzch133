package com.yunnext.pad.app.ui.screen.compontent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yunnext.pad.app.R

@Stable
sealed interface ComposeIcon {
    data class Local(val res: Int) : ComposeIcon
    data class Remote(val path: String, val error: Int) : ComposeIcon
}

@Composable
fun ImageComponent(modifier: Modifier = Modifier, composeIcon: ComposeIcon) {
    when (composeIcon) {
        is ComposeIcon.Local -> Image(
            painter = painterResource(composeIcon.res),
            null,
            modifier = modifier//, contentScale = ContentScale.Crop
        )

        is ComposeIcon.Remote -> AsyncImage(
            model = composeIcon.path,
            contentDescription = "网络图片",
            modifier = modifier,
            placeholder = painterResource(composeIcon.error), // 加载中显示的占位图
            error = painterResource(composeIcon.error) // 加载失败显示的图片
        )
    }
}


@Preview
@Composable
fun ImageComponentPreview(modifier: Modifier = Modifier) {
    ImageComponent(
        Modifier.size(100.dp),
        ComposeIcon.Remote(
            "http://t13.baidu.com/it/u=2363275402,233694669&fm=224&app=112&f=JPEG?w=184&h=210",
            R.mipmap.main
        )
    )
}