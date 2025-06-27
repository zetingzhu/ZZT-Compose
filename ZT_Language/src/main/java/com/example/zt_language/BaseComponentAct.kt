package com.example.zt_language

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.zt_language.util.LanguageChangeManager
import com.example.zt_language.v2.LocaleHelper

/**
 * @author: zeting
 * @date: 2025/6/27
 */
open class BaseComponentAct : ComponentActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LanguageChangeManager.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        LanguageChangeManager.removeActivity(this)
    }
}
