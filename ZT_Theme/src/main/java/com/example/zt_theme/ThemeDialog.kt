package com.example.zt_theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.zt_theme.ui.theme.LocalAppColors

/**
 * @author: zeting
 * @date: 2025/6/26
 *
 */

@Composable
fun ThemeAlertDialog(
    currentThemeMode: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM, // 接收当前主题模式
    onThemeModeChange: (darkThemeConfig: DarkThemeConfig) -> Unit, // 接收更新主题模式的回调
    onDismiss: () -> Unit,
) {

    // 获取屏幕宽度
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.dialog_title),
                style = MaterialTheme.typography.titleLarge,
                color = LocalAppColors.current.color2,
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                SettingThemeMode(
                    settings = currentThemeMode,
                    onChangeDarkThemeConfig = onThemeModeChange
                )
                HorizontalDivider(Modifier.padding(top = 8.dp))
            }
        },
        confirmButton = {
            NiaTextButton(
                onClick = onDismiss,
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                Text(
                    text = stringResource(R.string.dialog_ok),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
    )
}

@Composable
fun NiaTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        content = content,
    )
}