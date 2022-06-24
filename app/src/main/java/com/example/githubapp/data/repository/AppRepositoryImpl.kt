package com.example.githubapp.data.repository

import android.content.Context
import android.graphics.Color
import com.example.githubapp.R
import com.example.githubapp.data.remote.request.Api
import com.example.githubapp.domain.models.RepoDetails
import com.example.githubapp.domain.models.Repo
import com.example.githubapp.domain.repository.AppRepository
import com.example.githubapp.domain.utils.retrofitWrapException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AppRepositoryImpl(
    private val context: Context,
    private val api: Api,
) : AppRepository {
    private val jsonData = context.resources.openRawResource(R.raw.language_color).bufferedReader().readText()
    private val languages = Json.decodeFromString<Map<String, String?>>(jsonData)

    override suspend fun getRepositories(token: String): List<Repo> {

        var repoList = emptyList<Repo>()

        retrofitWrapException {
            repoList = api.getRepositories("token $token")
                .map { it.toRepoModel(it.owner.login) }
            repoList.forEach { it.languageColor = getLanguageColor(it.language) }
        }
        return repoList
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

    private fun getLanguageColor(language: String?) : Int? {
        if (language.isNullOrEmpty())
            return null

        val hexColor = languages[language]
        return Color.parseColor(hexColor)
    }
}

