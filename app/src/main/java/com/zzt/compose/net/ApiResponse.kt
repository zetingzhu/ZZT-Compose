package com.zzt.compose.net

/**
 * @author: zeting
 * @date: 2025/6/23
 * 接口基类
 */
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}