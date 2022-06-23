package com.example.githubapp.data.repository

import android.content.Context
import com.example.githubapp.data.storage.TokenSharedPref
import com.example.githubapp.domain.repository.TokenSharedPrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenSharedPrefRepositoryImpl
@Inject
constructor(
    @ApplicationContext
    private val context: Context,
) : TokenSharedPrefRepository {
    private val sharedPref = TokenSharedPref(context)

    override suspend fun getToken() : String? =
        sharedPref.localToken

    override suspend fun saveToken(token: String) {
        sharedPref.localToken = token
    }
}