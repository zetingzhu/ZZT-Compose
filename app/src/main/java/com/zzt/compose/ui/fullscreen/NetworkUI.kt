package com.zzt.compose.ui.fullscreen

 import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zzt.compose.viewmodel.UiState
import com.zzt.compose.viewmodel.WeatherViewModel

/**
 * @author: zeting
 * @date: 2025/6/30
 *
 */

@Composable
fun NetworkGaodeUi() {
    val viewModel: WeatherViewModel = viewModel()
    val uiState2 by viewModel.weatherState2.collectAsState() // 观察 StateFlow

    Column {
        when (val state = uiState2) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            is UiState.Success -> {
                Text(
                    color = Color.Blue,
                    text = "查询天气信息:\n ${state.data}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
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


