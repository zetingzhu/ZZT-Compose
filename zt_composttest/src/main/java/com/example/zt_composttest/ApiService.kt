package com.example.zt_composttest


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: zeting
 * @date: 2025/6/25
 *
 */
interface ApiService {
    @GET("weather/weatherInfo?key=0a53a908f0378845f8c11b9acb5fd2f2&extensions=all")
    suspend fun getWeather(@Query("city") page: String): Response<GaodeWeather>
}