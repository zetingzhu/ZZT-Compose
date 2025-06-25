package com.example.zt_composttest


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.zt_composttest.entiy.BaseGaodeWeather
import com.example.zt_composttest.entiy.GaodeWeather
import com.example.zt_composttest.entiy.ForecastsDTO
import com.example.zt_composttest.entiy.LivesDTO
import com.example.zzt.okhttp.ui.theme.ZZTOKHttpTheme

/**
 * @author: zeting
 * @date: 2025/6/25
 *
 */
class UserActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZZTOKHttpTheme {
                UserScreen(userViewModel)
            }
        }
        // 首次加载数据
        userViewModel.fetchWeather("310000")
        userViewModel.fetchWeatherV2("420600")
    }
}

@Composable
fun UserScreen(viewModel: UserViewModel) {
    val uiState by viewModel.weatherState.collectAsState() // 观察 StateFlow
    val uiState2 by viewModel.weatherState2.collectAsState() // 观察 StateFlow

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
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

        Button(
            onClick = {
                viewModel.fetchWeather("310000")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Refresh fetchWeather")
        }

        when (val state = uiState2) {
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

        Button(
            onClick = {
                viewModel.fetchWeatherV2("420600")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Refresh fetchWeather2")
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