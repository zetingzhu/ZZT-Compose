package com.example.zt_theme.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 1. 定义你的自定义颜色数据类
@Immutable // 推荐标记为 Immutable 以获得性能优化
data class AppColors(
    val myCustomBackground: Color,
    val myCustomContent: Color,
    val success: Color,
    val error: Color,
    val color1: Color,
    val color2: Color,
)

// 2. 为浅色主题定义自定义颜色实例
val LightAppColors = AppColors(
    myCustomBackground = Cream,       // 浅色主题下的自定义背景色
    myCustomContent = DarkBlue,       // 浅色主题下的自定义内容颜色
    success = SuccessGreen,
    error = ErrorRed,
    color1 = Color(0xFF002AFF),
    color2 = Color(0xFFFFBE00),
)

// 3. 为深色主题定义自定义颜色实例
val DarkAppColors = AppColors(
    myCustomBackground = AlmostBlack, // 深色主题下的自定义背景色
    myCustomContent = LightBlue,      // 深色主题下的自定义内容颜色
    success = SuccessGreen,           // 有些颜色可能在深浅主题下保持一致
    error = ErrorRed,
    color1 = Color(0xFF2EFF00),
    color2 = Color(0xFFFF5600),
)

// 4. 创建一个 CompositionLocal 来提供你的 AppColors
val LocalAppColors = staticCompositionLocalOf {
    LightAppColors
}
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF1C1B1F), // Material 3 深色背景
    surface = Color(0xFF1C1B1F),   // Material 3 深色表面
    onPrimary = Color.Black, // 假设 Purple80 上的文字是黑色
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color(0xFFE6E1E5),
    onSurface = Color(0xFFE6E1E5),
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFBFE), // Material 3 浅色背景
    surface = Color(0xFFFFFBFE),   // Material 3 浅色表面
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun ZZTComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    //  根据 darkTheme 的状态选择提供哪个 AppColors 实例
    val appColors = if (darkTheme) {
        DarkAppColors
    } else {
        LightAppColors
    }

    // 使用 CompositionLocalProvider 来提供选择的 AppColors
    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}