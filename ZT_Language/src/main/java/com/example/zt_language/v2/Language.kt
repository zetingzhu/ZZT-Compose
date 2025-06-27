package com.example.zt_language.v2


import androidx.annotation.StringRes
import com.example.zt_language.R // 替换为你的 R 文件路径
import java.util.Locale

/**
 * @author: zeting
 * @date: 2025/6/27
 *  定义支持的语言
 */
enum class Language(val code: String, @StringRes val displayNameResId: Int) {
    ENGLISH("en", R.string.english),
    SIMPLIFIED_CHINESE("zh-CN", R.string.simplified_chinese),
    TRADITIONAL_CHINESE("zh-TW", R.string.traditional_chinese);
    // 可以添加更多语言

    companion object {
        fun getByCode(code: String?): Language? {
            return values().find { it.code.equals(code, ignoreCase = true) }
        }

        fun getDefault(): Language {
            // 可以基于系统语言或其他逻辑选择默认语言
            val systemLangTag = Locale.getDefault().toLanguageTag()
            return getByCode(systemLangTag) ?: ENGLISH // 如果系统语言不支持，则默认为英语
        }
    }
}

// 语言列表，用于UI显示
val supportedLanguages = Language.values().toList()