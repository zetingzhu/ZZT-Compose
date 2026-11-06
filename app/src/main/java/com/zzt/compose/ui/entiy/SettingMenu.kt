package com.zzt.compose.ui.entiy

/**
 * @author: zeting
 * @date: 2025/6/23
 * 设置列表
 */

enum class SettingMenu(val route: String, val title: String, val isclick: Boolean = false) {
    ThemeSettings("ThemeSettings", "主题设置"),
    ThemeSettings2("ThemeSettings2", "主题设置2"),
    FullView("FullView", "跳到一个全屏视图", isclick = true),
    RequestsNetwork("RequestsNetwork", "接口请求", isclick = true),
}