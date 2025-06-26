package com.example.zt_theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zt_theme.ui.theme.ZZTComposeTheme

/**
 * @author: zeting
 * @date: 2025/6/26
 *
 */

@Preview
@Composable
fun SettingThemePreview() {
    ZZTComposeTheme() {
        SettingThemeMode() {}
    }
}


@Composable
fun SettingThemeMode(
    settings: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = "当前主题模式: ${settings.name}"
        )
        SettingsDialogSectionTitle(text = stringResource(id = R.string.theme_title))
        Column(Modifier.selectableGroup()) {
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.theme_mode_system_default),
                selected = settings == DarkThemeConfig.FOLLOW_SYSTEM,
                onClick = { onChangeDarkThemeConfig(DarkThemeConfig.FOLLOW_SYSTEM) },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.theme_mod_light),
                selected = settings == DarkThemeConfig.LIGHT,
                onClick = { onChangeDarkThemeConfig(DarkThemeConfig.LIGHT) },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.theme_mode_dark),
                selected = settings == DarkThemeConfig.DARK,
                onClick = { onChangeDarkThemeConfig(DarkThemeConfig.DARK) },
            )
        }
    }
}

@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
fun SettingsDialogThemeChooserRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}
