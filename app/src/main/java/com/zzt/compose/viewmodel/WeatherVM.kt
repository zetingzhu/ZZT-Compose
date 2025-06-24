package com.zzt.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzt.compose.net.GaodeApi
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
    var mWeather: MutableLiveData<GaodeWeather> = MutableLiveData()

    fun requestWeather(queryStr: String?) {
        viewModelScope.launch {
            mWeather = GaodeHelper.getInstance().gitHubApi.queryWeather(queryStr) as MutableLiveData<GaodeWeather>
        }
    }
}
