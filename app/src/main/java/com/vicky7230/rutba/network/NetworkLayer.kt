package com.vicky7230.rutba.network

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkLayer {

    private const val BASE_URL = "https://slack.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()
    }

    private val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    suspend fun getAuthToken(code: String): ResponseBody {
        return apiService.getAuthToken(code)
    }

    suspend fun getUserInfo(auth: String): ResponseBody {
        return apiService.getUserInfo(auth)
    }

    suspend fun getUserPresence(auth: String): ResponseBody {
        return apiService.getUserPresence(auth)
    }
}