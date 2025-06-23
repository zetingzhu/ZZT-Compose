package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzt.compose.theme.ZZTComposeTheme
import com.zzt.compose.ui.main.ZtNavMainView

/**
 * @author: zeting
 * @date: 2025/6/23
 *
 */
class ComposeRequestNetwork : ComponentActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ComposeRequestNetwork::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            requestNetworkUi()
        }
    }
}

@Preview
@Composable
fun requestNetworkUi() {
    ZZTComposeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Text(text = "请求网络接口")
                Button(onClick = {

                }) {
                    Text(text = "点击请求网络接口")
                }
            }
        }
    }
}