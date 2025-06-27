package com.example.zt_language.v1

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zt_language.BaseComponentAct
import com.example.zt_language.R
import com.example.zt_language.ui.theme.ZZTComposeTheme
import com.example.zt_language.v2.LocaleHelper
import com.example.zt_language.v2.MainActivity
import java.lang.ref.WeakReference
import java.util.Collections
import java.util.LinkedList

class SimpleActivityV1 : BaseComponentAct() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val current = LocalContext.current
            ZZTComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Text(
                            text = stringResource(R.string.s1_1),
                            modifier = Modifier.padding(top = 30.dp)
                        )
                        Text(text = stringResource(R.string.s1_2))
                        TextButton(onClick = {
                        }) {
                            Text(text = stringResource(R.string.s1_3))
                        }
                        TextButton(onClick = {
                            MainActivity.start(current)
                        }) {
                            Text(
                                text = stringResource(R.string.change_language),
                                fontSize = 30.sp, // 修改字体大小
                            )
                        }
                    }
                }
            }
        }
    }

}
