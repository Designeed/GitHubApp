package com.example.githubapp.domain.repository

interface TokenSharedPrefRepository {
    suspend fun getToken() : String?
    suspend fun saveToken(token: String)
}