package com.zzt.compose.net


import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author: zeting
 * @date: 2025/6/23
 * A Retrofit CallAdapter.Factory that converts Retrofit's Call to LiveData.
 * It also wraps the response in an ApiResponse for better state management.
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // 确保返回类型是 LiveData
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }

        // 获取 LiveData 的泛型参数 (e.g., LiveData<ApiResponse<User>>)
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)

        // 确保 LiveData 的泛型参数是 ApiResponse
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("return type must be LiveData<ApiResponse<Foo>>")
        }

        // 确保 ApiResponse 也有一个泛型参数 (e.g., ApiResponse<User>)
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("ApiResponse must have generic type (e.g., ApiResponse<Foo>)")
        }

        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}

/**
 * A Retrofit CallAdapter that wraps a Call into a LiveData.
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    // 发送 Loading 状态
                    postValue(ApiResponse.Loading)

                    call.enqueue(object : retrofit2.Callback<R> {
                        override fun onResponse(call: Call<R>, response: retrofit2.Response<R>) {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    postValue(ApiResponse.Success(it))
                                } ?: run {
                                    // Handle empty body for successful response
                                    postValue(
                                        ApiResponse.Error(
                                            "Empty response body",
                                            response.code()
                                        )
                                    )
                                }
                            } else {
                                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                                postValue(
                                    ApiResponse.Error(
                                        "HTTP Error: $errorBody",
                                        response.code()
                                    )
                                )
                            }
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            postValue(ApiResponse.Error(t.message ?: "Network error"))
                        }
                    })
                }
            }
        }
    }
}