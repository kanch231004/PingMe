package com.cnx.pingme.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    private val okHttpClient =  OkHttpClient.Builder().addNetworkInterceptor(
        HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger {
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(OfflineChatService.ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService() = retrofit.create(OfflineChatService::class.java)
}