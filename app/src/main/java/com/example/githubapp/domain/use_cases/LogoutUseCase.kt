package com.example.githubapp.domain.use_cases

import com.example.githubapp.domain.repository.TokenSharedPrefRepository
import javax.inject.Inject

class LogoutUseCase
@Inject
constructor(
    private val repository: TokenSharedPrefRepository
) {
    fun execute() =
        repository.clearToken()

}