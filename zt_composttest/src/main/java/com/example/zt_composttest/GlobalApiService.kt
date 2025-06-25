package com.example.zt_composttest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  * @author: zeting
 *  * @date: 2025/6/25
 * 全局 Retrofit 和 ApiService 访问点
 */
object GlobalApiService {
    private const val BASE_URL = "https://restapi.amap.com/v3/"

    // 公开的静态 ApiService 实例
    val apiService: ApiService by lazy { // 使用 lazy 确保只在首次访问时初始化
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            // 在 debug 构建中打印请求和响应体, 生产环境应避免 BODY
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            // 你可以在这里添加其他全局拦截器
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}