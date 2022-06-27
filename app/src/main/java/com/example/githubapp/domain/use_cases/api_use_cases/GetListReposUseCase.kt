package com.example.githubapp.domain.use_cases.api_use_cases

import com.example.githubapp.domain.models.Repo
import com.example.githubapp.domain.repository.AppRepository
import javax.inject.Inject

class GetListReposUseCase
@Inject
constructor(
    val appRepository: AppRepository
) {
    suspend fun execute(token: String) : List<Repo> =
        appRepository.getRepositories(token)
}