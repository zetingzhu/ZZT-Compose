package com.example.zt_language.v1

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.zt_language.R

/**
 * @author: zeting
 * @date: 2025/6/26
 *
 */


@Composable
fun SettingLanguage(

) {
    var currentLanguage = remember { mutableStateOf("0") }
    Column {
        SettingsDialogSectionTitle(text = stringResource(R.string.selected_language))
        Column(Modifier.selectableGroup()) {
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.language_english),
                selected = currentLanguage.value == "1",
                onClick = { currentLanguage.value = "1" },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.language_chinese_simplified),
                selected = currentLanguage.value == "2",
                onClick = { currentLanguage.value = "2" },
            )
            SettingsDialogThemeChooserRow(
                text = stringResource(id = R.string.language_chinese_traditional),
                selected = currentLanguage.value == "3",
                onClick = { currentLanguage.value = "3" },
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

