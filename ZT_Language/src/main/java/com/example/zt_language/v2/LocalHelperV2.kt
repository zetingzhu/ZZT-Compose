package com.example.zt_language.v2


import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import java.util.Locale
/**
 * @author: zeting
 * @date: 2025/6/27
 *
 */
object LocaleHelper {

    private const val SELECTED_LANGUAGE_PREF = "AppLocalePrefs"
    private const val LANGUAGE_KEY = "selected_language"

    // 当Activity的attachBaseContext被调用时，用这个方法来包装Context
    fun onAttach(context: Context): Context {
        val lang = getPersistedLanguage(context)
        return setLocale(context, lang)
    }

    fun getPersistedLanguage(context: Context): String {
        val preferences = getPreferences(context)
        // 默认返回系统语言的 language tag，如果没有保存过偏好
        return preferences.getString(LANGUAGE_KEY, getSystemLanguageTag()) ?: getSystemLanguageTag()
    }

    private fun getSystemLanguageTag(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale.getDefault().toLanguageTag()
        } else {
            // 对于旧版本，我们可能需要更复杂的处理来获取完整的tag，
            // 但通常 language code 就足够了。
            Locale.getDefault().language
        }
    }

    // 设置新的Locale并返回更新后的Context
    fun setLocale(context: Context, languageTag: String): Context {
        persistLanguage(context, languageTag)
        val locale = if (languageTag.contains("-")) {
            val parts = languageTag.split("-")
            if (parts.size > 1) Locale(parts[0], parts[1].uppercase()) else Locale(parts[0])
        } else {
            Locale(languageTag)
        }
        Locale.setDefault(locale) // 设置默认Locale

        val configuration = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale) // 处理RTL语言
        } else {
            configuration.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale)
            }
        }
        return context.createConfigurationContext(configuration)
    }

    private fun persistLanguage(context: Context, languageTag: String) {
        val preferences = getPreferences(context)
        preferences.edit().putString(LANGUAGE_KEY, languageTag).apply()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SELECTED_LANGUAGE_PREF, Context.MODE_PRIVATE)
    }

    // 获取当前应用上下文中的语言标签（用于UI显示当前选择）
    fun getCurrentLocaleTag(context: Context): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        return locale?.toLanguageTag() ?: getSystemLanguageTag()
    }
}