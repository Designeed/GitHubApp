package com.example.githubapp.domain.use_cases

import com.example.githubapp.domain.repository.TokenSharedPrefRepository
import javax.inject.Inject

class GetTokenUseCase
@Inject
constructor(
    private val repository: TokenSharedPrefRepository
) {
    suspend fun execute() : String? = repository.getToken()
}