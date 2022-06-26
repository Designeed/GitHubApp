package com.example.githubapp.data.remote.request

import com.example.githubapp.domain.models.response.RepoDetailResponse
import com.example.githubapp.domain.models.response.RepoResponse
import com.example.githubapp.domain.models.response.ReadmeResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface Api {
    @GET("/user")
    suspend fun authByToken(
        @Header("Authorization") token: String
    )

    @GET("/user/repos?type=all&sort=updated&direction=desc&per_page=10")
    suspend fun getRepositories(
        @Header("Authorization") token: String
    ) : List<RepoResponse>

    @GET("/repos/{owner}/{repo}")
    suspend fun getRepoDetailInfo(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ) : RepoDetailResponse


    @GET("/repos/{owner}/{repo}/readme?ref=master")
    suspend fun getReadmeContent(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ) : ReadmeResponse
}
