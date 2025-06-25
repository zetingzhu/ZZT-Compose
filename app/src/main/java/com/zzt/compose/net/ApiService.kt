package com.zzt.compose.net


import com.zzt.compose.entity.BaseGaodeWeather
import com.zzt.compose.entity.ForecastsDTO
import com.zzt.compose.entity.GaodeWeather
import com.zzt.compose.entity.LivesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: zeting
 * @date: 2025/6/25
 *  高德接口定义
 */
interface ApiService {
    @GET("weather/weatherInfo?key=0a53a908f0378845f8c11b9acb5fd2f2&extensions=all")
    suspend fun getWeather(@Query("city") page: String): Response<GaodeWeather>

    @GET("weather/weatherInfo")
    suspend fun getWeatherV2(
        @Query("city") cityCode: String,
        @Query("key") apiKey: String
    ): BaseGaodeWeather<ForecastsDTO, LivesDTO>

}