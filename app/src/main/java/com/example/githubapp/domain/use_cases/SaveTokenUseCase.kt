package com.example.githubapp.domain.use_cases

import com.example.githubapp.domain.repository.TokenSharedPrefRepository
import javax.inject.Inject

class SaveTokenUseCase
@Inject
constructor(
    private val repository: TokenSharedPrefRepository
) {
    suspend fun execute(token: String) = repository.saveToken(token)
}