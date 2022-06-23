package com.example.githubapp.data.repository

import com.example.githubapp.data.remote.request.Api
import com.example.githubapp.domain.models.RepoDetails
import com.example.githubapp.domain.models.RepoInfo
import com.example.githubapp.domain.repository.AppRepository
import com.example.githubapp.domain.utils.retrofitWrapException

class AppRepositoryImpl(
    private val api: Api
) : AppRepository {
    override suspend fun getRepositories(): List<RepoInfo> {
        TODO("Not yet implemented")
        return emptyList()
    }

    override suspend fun getRepository(repoId: String): RepoDetails {
        TODO("Not yet implemented")
    }

    override suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(token: String) {
        retrofitWrapException {
            api.authByToken("token $token")
        }
    }
}

