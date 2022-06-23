package com.example.githubapp.data.remote.request

import retrofit2.http.GET
import retrofit2.http.Header

interface Api {
    @GET("/user")
    suspend fun authByToken(
        @Header("Authorization") token: String
    )
}
