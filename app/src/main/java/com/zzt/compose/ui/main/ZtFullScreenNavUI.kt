package com.zzt.compose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zzt.compose.ui.fullscreen.FullScreenVideo
import com.zzt.compose.ui.fullscreen.NetworkGaodeUi

/**
 * @author: zeting
 * @date: 2025/4/14
 * 这是一个处理全屏的导航
 */

@Composable
fun ZtFullScreenNavUI(
    mainNavController: NavController = rememberNavController(),
    viewPager: String? = "Default"
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        NavHost(
            navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            startDestination = viewPager ?: "Default"
        ) {
            composable("Default") {
                FullScreenDefault()
            }
            composable("video") {
                FullScreenVideo(mainNavController)
            }
            composable("network") {
                NetworkGaodeUi()
            }
        }
    }
}

@Composable
fun FullScreenDefault() {
    Box(modifier = Modifier.background(color = Color(0x2CA5EEA2))) {
        Text(text = "这是一个全屏页面，这里这里来处理全屏逻辑")
    }
}