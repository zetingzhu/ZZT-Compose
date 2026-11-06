package com.zzt.compose.ui.fullscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

/**
 * @author: zeting
 * @date: 2025/6/30
 * 播放视频页面
 */

@Composable
fun FullScreenVideo(mainNavController: NavController = rememberNavController()) {
    // 这个屏幕不使用 Scaffold，因此它占据所有可用空间。
    Column(
        modifier = Modifier
            .fillMaxSize() // 填充所有可用空间
            .background(Color.LightGray), // 示例背景色
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "这是一个全屏页面！")
        Button(onClick = { mainNavController.popBackStack() }) { // 返回上一个屏幕
            Text("返回")
        }
    }
}
