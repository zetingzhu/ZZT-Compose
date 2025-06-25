package com.zzt.compose.net;

import androidx.lifecycle.LiveData;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: zeting
 * @date: 2023/9/14
 * 搞的天气接口
 */
public interface GaodeApi {

    /**
     * 高德天气接口  BaiduApi
     * 官网地址： https://lbs.amap.com/api/webservice/guide/api/weatherinfo
     * <p>
     * https://restapi.amap.com/v3/weather/weatherInfo?key=0a53a908f0378845f8c11b9acb5fd2f2&city=110101&extensions=all
     * <p>
     * 中华人民共和国	100000
     * 北京   110000
     * 上海   310000
     * 襄阳   420600
     */
    @GET("weather/weatherInfo?key=0a53a908f0378845f8c11b9acb5fd2f2&extensions=all")
    LiveData<GaodeWeather> queryWeather(@Query("city") String queryStr);

    @GET("weather/weatherInfo?key=0a53a908f0378845f8c11b9acb5fd2f2&extensions=all")
    GaodeWeather queryWeatherObj(@Query("city") String queryStr);

    @GET("weather/weatherInfo?key=0a53a908f0378845f8c11b9acb5fd2f2")
    Call<GaodeWeather> queryWeatherLives(@Query("city") String queryStr);

}