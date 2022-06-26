package com.example.githubapp.domain.use_cases.request_use_cases

import com.example.githubapp.domain.repository.AppRepository
import javax.inject.Inject

class GetReadmeContentUseCase
@Inject
constructor(
    val appRepository: AppRepository
) {
    suspend fun execute(token: String, owner: String, repoName: String): String =
        appRepository.getReadme(token, owner, repoName)

}