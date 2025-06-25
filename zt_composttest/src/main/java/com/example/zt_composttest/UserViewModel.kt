package com.example.zt_composttest


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author: zeting
 * @date: 2025/6/25
 * 帮我自定义一个 Converter.Factory 来写 UiState 状态，不用每个接口都自己来判断当前状态 UserViewModel，只需要返回结果就行了
 */
class UserViewModel : ViewModel() {
    // 直接在内部访问全局的 ApiService 实例
    private val apiService: ApiService = GlobalApiService.apiService

    val weatherState: MutableStateFlow<UiState<GaodeWeather>> = MutableStateFlow(UiState.Loading)

    // 假设你的 ApiService 有一个获取天气的方法，例如 getWeather
    fun fetchWeather(cityCode: String) { // 参数改为城市代码或其他获取天气的必要参数
        viewModelScope.launch {
            weatherState.value = UiState.Loading
            try {
                // 直接调用全局 ApiService 实例的方法
                val response = apiService.getWeather(cityCode) // 假设 ApiService 有 getWeather 方法
                if (response.isSuccessful) {
                    response.body()?.let {
                        weatherState.value = UiState.Success(it)
                    } ?: run {
                        weatherState.value = UiState.Error("Response body is null")
                    }
                } else {
                    weatherState.value =
                        UiState.Error("API Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                weatherState.value = UiState.Error(e.message ?: "Network or other exception")
            }
        }
    }
}

// 定义一个通用的 UI 状态类
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}