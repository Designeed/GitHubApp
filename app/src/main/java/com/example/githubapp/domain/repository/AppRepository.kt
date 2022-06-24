package com.example.githubapp.domain.repository

import com.example.githubapp.domain.models.*

interface AppRepository {
    suspend fun getRepositories(token: String): List<Repo>

    suspend fun getRepository(repoId: String): RepoDetails

    suspend fun getRepositoryReadme(ownerName: String, repositoryName: String, branchName: String): String

    suspend fun signIn(token: String)
}