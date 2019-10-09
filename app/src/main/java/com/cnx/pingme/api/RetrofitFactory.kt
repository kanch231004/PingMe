package com.cnx.pingme.api

import android.util.Log
import com.cnx.pingme.utils.isConnected
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Kanchan on 09/09/19.
 */


object RetrofitFactory {

    val retrofit: Retrofit? = null
    val BASE_URL = "https://www.personalityforge.com"

    fun getRetrofitClient() : ChatService {

       val spec : ConnectionSpec? = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
               .tlsVersions(TlsVersion.TLS_1_2)
               .cipherSuites(
                       CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                       CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                       CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
               .build()

       val okHttpClient = OkHttpClient.Builder()
               .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT,spec))
               .connectTimeout(30, TimeUnit.SECONDS)
               .readTimeout(30, TimeUnit.SECONDS)
               //.cache(Cache(appInstance.cacheDir, 20 * 1024 * 1024)) // 20MB
               .retryOnConnectionFailure(true)
               // Cache Policy Interceptor
               .addInterceptor {
                   var request = it.request()

                   val cacheHeader = request.header("MyCacheControl")

                   request = if (isConnected()) {

                       // holds the cache for 60 seconds
                       request.newBuilder()
                               .removeHeader("Pragma").header("Cache-Control",
                                       "public, max-age=60").build()
                   } else {
                       // holds the cache for a week if offline
                       if (!cacheHeader.isNullOrEmpty()) {
                           request.newBuilder()
                                   .removeHeader("Pragma").header("Cache-Control",
                                           cacheHeader).build()
                       } else {
                           request.newBuilder()
                                   .removeHeader("Pragma").header("Cache-Control",
                                           "no-cache").build()
                       }
                   }
                   it.proceed(request)
               }
               .addInterceptor {
                   val original = it.request()
                   val timeZone = TimeZone.getDefault().id
                   val request = original.newBuilder()
                           .header("CLIENTTZ", timeZone)
                           .method(original.method(), original.body())
                           .build()

                   it.proceed(request)
               }
               // Logging Interceptor
               .addNetworkInterceptor(
                       HttpLoggingInterceptor(
                               HttpLoggingInterceptor.Logger {
                                   Log.i("ApiInterface", ": $it")
                               }
                       ).setLevel(HttpLoggingInterceptor.Level.BODY))

               .build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient).build().create(ChatService::class.java)

    }
}