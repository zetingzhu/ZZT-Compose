package com.example.zt_theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zt_theme.ui.theme.LocalAppColors
import com.example.zt_theme.ui.theme.ZZTComposeTheme

enum class DarkThemeConfig {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 1. 创建并记住主题模式的状态
            var currentThemeMode = remember { mutableStateOf(DarkThemeConfig.FOLLOW_SYSTEM) }

            // 2. 根据状态决定是否使用深色主题
            val useDarkTheme = shouldUseDarkTheme(themeMode = currentThemeMode.value)

            ZZTComposeTheme(darkTheme = useDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        currentThemeMode = currentThemeMode.value,
                        onThemeModeChange = { newThemeMode ->
                            currentThemeMode.value = newThemeMode
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    currentThemeMode: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM, // 接收当前主题模式
    onThemeModeChange: (darkThemeConfig: DarkThemeConfig) -> Unit // 接收更新主题模式的回调
) {
    //   添加状态来控制对话框的可见性
    var showDialog by remember { mutableStateOf(false) }
    // 条件性地显示对话框
    if (showDialog) {
        ThemeAlertDialog(
            currentThemeMode = currentThemeMode,
            onThemeModeChange = { newThemeMode ->
                onThemeModeChange(newThemeMode)
            },
            onDismiss = {
                showDialog = false // 当对话框被关闭时（例如点击外部或返回键），更新状态
            }
        )
    }
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "这个文本使用,系统颜色",
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "这个文本使用，自定义颜色",
            color = LocalAppColors.current.color1
        )

        // todo 当前页面切换深色，浅色模式
        SettingThemeMode(
            settings = currentThemeMode,
            onChangeDarkThemeConfig = onThemeModeChange
        )

        Button(onClick = {
            showDialog = true // 点击按钮时，设置状态为 true 以显示对话框
        }) {
            Text(text = "点击弹框，在弹框中切换主题")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZZTComposeTheme {
        Greeting(
            "Android",
            onThemeModeChange = { newThemeMode ->
            }
        )
    }
}

// 预览可能需要调整以适应新的参数
@Preview(showBackground = true, name = "Light Mode Preview")
@Composable
fun GreetingPreviewLight() {
    val currentThemeMode = remember { mutableStateOf(DarkThemeConfig.LIGHT) }
    ZZTComposeTheme(darkTheme = shouldUseDarkTheme(themeMode = currentThemeMode.value)) {
        Greeting(
            "Android",
            currentThemeMode = currentThemeMode.value,
            onThemeModeChange = { newMode -> currentThemeMode.value = newMode }
        )
    }
}

@Preview(showBackground = true, name = "Dark Mode Preview")
@Composable
fun GreetingPreviewDark() {
    val currentThemeMode = remember { mutableStateOf(DarkThemeConfig.DARK) }
    ZZTComposeTheme(darkTheme = shouldUseDarkTheme(themeMode = currentThemeMode.value)) {
        Greeting(
            "Android",
            currentThemeMode = currentThemeMode.value,
            onThemeModeChange = { newMode -> currentThemeMode.value = newMode }
        )
    }
}

// 辅助函数，根据选择的主题模式决定是否使用深色主题
@Composable
fun shouldUseDarkTheme(themeMode: DarkThemeConfig): Boolean {
    return when (themeMode) {
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }
}
