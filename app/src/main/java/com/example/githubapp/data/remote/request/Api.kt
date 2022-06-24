package com.example.githubapp.data.remote.request

import com.example.githubapp.domain.models.response.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {
    @GET("/user")
    suspend fun authByToken(
        @Header("Authorization") token: String
    )

    @GET("/user/repos?type=all&sort=updated&direction=desc&per_page=10")
    suspend fun getRepositories(
        @Header("Authorization") token: String
    ) : List<RepoResponse>
}
