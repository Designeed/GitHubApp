package com.example.githubapp.domain.use_cases.api_use_cases

import com.example.githubapp.domain.models.RepoDetails
import com.example.githubapp.domain.repository.AppRepository
import javax.inject.Inject

class GetRepoDetailUseCase
@Inject
constructor(
    val appRepository: AppRepository
) {
    suspend fun execute(token: String, owner: String, repoName: String) : RepoDetails =
        appRepository.getRepoDetail(token, owner, repoName)
}