package com.zzt.compose.net;


import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: zeting
 * @date: 2023/9/14
 */
public class RetrofitUtils {
    private static final String TAG = RetrofitUtils.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT = 20; // 20  SECONDS
    private static final int DEFAULT_READ_TIMEOUT = 30; // 20  SECONDS

    private static class InnerClass {
        private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    }

    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        return InnerClass.INSTANCE;
    }

    public synchronized OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String s) {
                Log.d(TAG, s);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

        return builder.build();
    }

    private synchronized Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T getApiService(String ShowUrl, Class<T> service) {
        Retrofit retrofit = getRetrofit(ShowUrl);
        return retrofit.create(service);
    }
}
