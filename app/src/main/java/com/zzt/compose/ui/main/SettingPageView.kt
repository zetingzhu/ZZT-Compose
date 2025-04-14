package com.zzt.compose.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import com.zzt.compose.R
import com.zzt.compose.ui.theme.ZZTComposeTheme

/**
 * @author: zeting
 * @date: 2025/4/14
 *
 */
@Composable
fun SettingPage() {
//    SettingPageV1()

    SettingPageV2()
}

@Composable
fun SettingPageV1() {
    val contentPadding = WindowInsets
        .systemBars
        .add(WindowInsets(left = 16.dp, top = 16.dp, right = 16.dp, bottom = 16.dp))
        .asPaddingValues()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Row {
                Text(text = "设置")
                Button(onClick = { }) {
                    Text("浅色模式")
                }
                Button(onClick = { }) {
                    Text("深色模式")
                }
            }
        }
    }
}

@Composable
fun SettingPageV2() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        SettingsDialogSectionTitle(text = stringResource(R.string.feature_settings_dark_mode_preference))
        Column(Modifier.selectableGroup()) {
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.feature_settings_dark_mode_config_system_default),
                selected = false,
                onClick = {
                },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.feature_settings_dark_mode_config_light),
                selected = false,
                onClick = { },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(R.string.feature_settings_dark_mode_config_dark),
                selected = false,
                onClick = { },
            )
        }
        HorizontalDivider(Modifier.padding(top = 8.dp))
    }
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


@Composable
private fun SettingsDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}


@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    ZZTComposeTheme {
        SettingPage()
    }
}
