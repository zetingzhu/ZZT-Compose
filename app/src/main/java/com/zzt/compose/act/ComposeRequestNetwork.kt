package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zzt.compose.entity.BaseGaodeWeather
import com.zzt.compose.entity.ForecastsDTO
import com.zzt.compose.entity.GaodeWeather
import com.zzt.compose.entity.LivesDTO
import com.zzt.compose.theme.ZZTComposeTheme
import com.zzt.compose.ui.main.ZtNavMainView
import com.zzt.compose.viewmodel.UiState
import com.zzt.compose.viewmodel.WeatherViewModel
import kotlin.toString

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

    val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            requestNetworkUi(viewModel)
        }
    }
}

@Composable
fun requestNetworkUi(viewModel: WeatherViewModel) {
    ZZTComposeTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            color = MaterialTheme.colorScheme.background
        ) {

            val weather by viewModel.weatherState2.collectAsState()

            Column {
                Text(text = "请求网络接口")
                Button(onClick = {
                    viewModel.fetchWeatherV2("420600")
                }) {
                    Text(text = "点击请求网络接口")
                }
                when (val state = weather) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }

                    is UiState.Success -> {
                        UserList(users = state.data)
                    }

                    is UiState.Error -> {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun UserList(users: GaodeWeather) {
    Text(
        color = Color.White,
        text = "查询天气信息:\n ${users.toString()}",
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun UserList(users: BaseGaodeWeather<ForecastsDTO, LivesDTO>) {
    Text(
        color = Color.White,
        text = "查询天气信息:\n ${users.toString()}",
        modifier = Modifier.padding(vertical = 8.dp)
    )
}