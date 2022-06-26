package com.example.githubapp.domain.repository

import com.example.githubapp.domain.models.*

interface AppRepository {
    suspend fun getRepositories(token: String): List<Repo>

    suspend fun getRepoDetail(token: String, owner: String, repoName: String): RepoDetails

    suspend fun getReadme(token: String, ownerName: String, repoName: String, branchName: String? = null): String

    suspend fun signIn(token: String)
}