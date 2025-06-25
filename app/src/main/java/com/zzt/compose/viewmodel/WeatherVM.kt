package com.zzt.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzt.compose.net.GaodeHelper
import com.zzt.compose.net.GaodeWeather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * @author: zeting
 * @date: 2025/6/23
 * 天气接口
 */
class WeatherVM : ViewModel() {
    private val _mWeather = MutableStateFlow<GaodeWeather?>(null)
    val mWeather: StateFlow<GaodeWeather?> = _mWeather

    fun requestWeather(queryStr: String?) {
        viewModelScope.launch {
            try {
                val result = GaodeHelper.getInstance().gitHubApi.queryWeatherObj(queryStr)
                _mWeather.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                _mWeather.value = null
            }
        }
    }
}
