package com.zzt.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzt.compose.entity.BaseGaodeWeather
import com.zzt.compose.entity.ForecastsDTO
import com.zzt.compose.entity.GaodeWeather
import com.zzt.compose.entity.LivesDTO
import com.zzt.compose.net.ApiService
import com.zzt.compose.net.GlobalApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * @author: zeting
 * @date: 2025/6/25
 *
 */
class WeatherViewModel : ViewModel() {

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
                e.printStackTrace()
                weatherState.value = UiState.Error(e.message ?: "Network or other exception")
            }
        }
    }

    val MY_API_KEY = "0a53a908f0378845f8c11b9acb5fd2f2"
    val weatherState2: MutableStateFlow<UiState<BaseGaodeWeather<ForecastsDTO, LivesDTO>>> =
        MutableStateFlow(UiState.Loading)

    fun fetchWeatherV2(cityCode: String) {
        viewModelScope.launch {
            launchSafeApiCall {
                apiService.getWeatherV2(cityCode, MY_API_KEY)
            }.collectLatest { uiStateResult ->
                weatherState2.value = uiStateResult
            }
        }
    }
}

/**
 * 定义一个通用的 UI 状态类
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

/**
 * 通用的网络请求处理函数，将挂起函数的结果转换为 Flow<UiState<T>>.
 *
 * @param T 成功时返回的数据类型
 * @param apiCall 一个挂起的 lambda 表达式，代表实际的网络 API 调用。
 *                它应该直接返回成功时的数据类型 T。
 * @return Flow<UiState<T>>
 *   中文回复
 *
 */
fun <T> launchSafeApiCall(apiCall: suspend () -> T): Flow<UiState<T>> {
    return flow<UiState<T>> {
        val result = apiCall()
        emit(UiState.Success(result))
    }.onStart {
        emit(UiState.Loading)
    }.catch { e ->
        e.printStackTrace()
        emit(UiState.Error(e.message ?: "An unknown error occurred"))
    }
}