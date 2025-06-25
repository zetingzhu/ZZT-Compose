package com.example.zt_composttest

/**
 * @author: zeting
 * @date: 2025/6/25
 *
 */
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.zzt.okhttp.ui.theme.ZZTOKHttpTheme

class UserActivity : ComponentActivity() {
    // 使用默认的 ViewModelProvider 获取 UserViewModel 实例
    private val userViewModel: UserViewModel by viewModels() // 不再需要自定义工厂

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZZTOKHttpTheme {
                UserScreen(userViewModel)
            }
        }
        // 首次加载数据
        userViewModel.fetchWeather("310000")
    }
}

@Composable
fun UserScreen(viewModel: UserViewModel) {
    val uiState by viewModel.weatherState.collectAsState() // 观察 StateFlow

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            is UiState.Success -> {
                UserList(users = state.data) // 假设 GaodeWeather 有 geocodes 属性
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
            onClick = { viewModel.fetchWeather("310000") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Refresh Users")
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