package com.example.zt_composttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zzt.okhttp.ui.theme.ZZTOKHttpTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class NetAct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZZTOKHttpTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 30.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen() {
    var weatherInfo by remember { mutableStateOf("点击按钮获取天气信息") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                loading = true
                // 启动协程请求网络
                // 这里用LaunchedEffect+key触发
                weatherInfo = "请求中..."
            },
            enabled = !loading
        ) {
            Text("获取天气")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(weatherInfo)
    }

    // Compose 网络请求流程
    // 监听 loading 状态，触发网络请求
    if (loading) {
        LaunchedEffect(loading) {
            val result = getWeather()
            weatherInfo = result
            loading = false
        }
    }
}

// 网络请求函数
suspend fun getWeather(): String = withContext(Dispatchers.IO) {
    val url =
        "https://restapi.amap.com/v3/weather/weatherInfo?key=0a53a908f0378845f8c11b9acb5fd2f2&city=110101&extensions=all"
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    return@withContext try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.string() ?: "无数据"
        } else {
            "请求失败: ${response.code}"
        }
    } catch (e: Exception) {
        "请求异常: ${e.message}"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZZTOKHttpTheme {
        Greeting("Android")
    }
}