package com.zzt.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * @author: zeting
 * @date: 2022/11/15
 *
 */
class MTheme {

    @Composable
    fun JetnewsTheme(content: @Composable () -> Unit) {
        MaterialTheme(content = content)
    }
}