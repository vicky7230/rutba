package com.vicky7230.rutba.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("api/oauth.access")
    suspend fun getAuthToken(
        @Query("code") code: String?,
        @Query("client_id") client_id: String = "2885486251284.2876518786758",
        @Query("client_secret") client_secret: String = "3e5042051fd462923b8972ebe5e1a0a6",
    ): ResponseBody

    @GET("api/users.profile.get")
    suspend fun getUserInfo(
        @Header("Authorization") auth: String
    ): ResponseBody

    @GET("api/users.getPresence")
    suspend fun getUserPresence(
        @Header("Authorization") auth: String
    ): ResponseBody


}