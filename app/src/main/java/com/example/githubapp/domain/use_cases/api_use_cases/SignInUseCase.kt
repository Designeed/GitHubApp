package com.example.githubapp.domain.use_cases.api_use_cases

import com.example.githubapp.domain.repository.AppRepository
import javax.inject.Inject

class SignInUseCase
@Inject
constructor(
    val appRepository: AppRepository
) {
    suspend fun execute(token: String) =
        appRepository.signIn(token)
}