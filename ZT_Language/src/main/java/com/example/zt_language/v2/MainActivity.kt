package com.example.zt_language.v2

/**
 * @author: zeting
 * @date: 2025/6/27
 *
 */// MainActivity.kt
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.zt_language.BaseComponentAct
import com.example.zt_language.R // 替换为你的 R 文件路径
import com.example.zt_language.ui.theme.ZZTComposeTheme
import com.example.zt_language.util.LanguageChangeManager


class MainActivity : BaseComponentAct() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZZTComposeTheme { // 使用你的应用主题
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainContentScreen(
                        onLanguageChange = { newLanguage -> // 修改回调，接收新的语言
                            val currentContext = this // Activity Context
                            LocaleHelper.setLocale(currentContext, newLanguage.code)
                            LanguageChangeManager.onLanguageChanged() // 通知管理器语言已更改
                            // MainActivity 自身也会被 LanguageChangeManager.onLanguageChanged() 中的 recreate 调用处理

                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainContentScreen(onLanguageChange: (Language) -> Unit) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    // Get the context here, within the composable scope
    val currentContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = stringResource(R.string.s1_1))
        Text(text = stringResource(R.string.s1_2))
        Text(text = stringResource(R.string.s1_3))


        Text(
            text = stringResource(id = R.string.greeting),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { showLanguageDialog = true }) {
            Text(text = stringResource(id = R.string.change_language))
        }
    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            onDismissRequest = { showLanguageDialog = false },
            onLanguageSelected = { selectedLanguage ->
                // Now use the currentContext obtained from the composable scope
                LocaleHelper.setLocale(currentContext, selectedLanguage.code) // 保存选择
                onLanguageChange(selectedLanguage) // 通知Activity重建
                showLanguageDialog = false
            }
        )
    }
}