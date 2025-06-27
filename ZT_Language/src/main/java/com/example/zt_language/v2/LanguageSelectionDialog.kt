package com.example.zt_language.v2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.zt_language.R
/**
 * @author: zeting
 * @date: 2025/6/27
 *
 */
@Composable
fun LanguageSelectionDialog(
    onDismissRequest: () -> Unit,
    onLanguageSelected: (Language) -> Unit
) {
    val currentLanguageTag = LocaleHelper.getCurrentLocaleTag(LocalContext.current)
    val currentSelectedLanguage = Language.getByCode(currentLanguageTag) ?: Language.getDefault()

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.language_selection_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn {
                    items(supportedLanguages) { language ->
                        LanguageItem(
                            language = language,
                            isSelected = language == currentSelectedLanguage,
                            onClick = {
                                onLanguageSelected(language)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cancel") // 你也可以使用stringResource
                }
            }
        }
    }
}

@Composable
fun LanguageItem(language: Language, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = language.displayNameResId),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}