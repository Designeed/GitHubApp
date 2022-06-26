package com.example.githubapp.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.util.Base64
import android.util.Log
import com.example.githubapp.R
import com.example.githubapp.data.remote.request.Api
import com.example.githubapp.domain.models.RepoDetails
import com.example.githubapp.domain.models.Repo
import com.example.githubapp.domain.repository.AppRepository
import com.example.githubapp.domain.utils.retrofitWrapException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.nio.charset.StandardCharsets
class AppRepositoryImpl(
    private val context: Context,
    private val api: Api,
) : AppRepository {
    private val jsonData = context.resources.openRawResource(R.raw.language_color).bufferedReader().readText()
    private val languages = Json.decodeFromString<Map<String, String?>>(jsonData)

    override suspend fun getRepositories(token: String): List<Repo> =
        retrofitWrapException {
            return@retrofitWrapException api.getRepositories("token $token")
                .map { it.toRepoModel(it.owner.login, getLanguageColor(it.language)) }
        }

    override suspend fun getRepoDetail(token: String, owner: String, repoName: String): RepoDetails =
        retrofitWrapException {
            api.getRepoDetailInfo(
                "token $token",
                owner,
                repoName)
        }.toRepoDetailModel()

    override suspend fun getReadme(
        token: String,
        ownerName: String,
        repoName: String,
        branchName: String?
    ): String =
        retrofitWrapException {
            val encodedReadme = api.getReadmeContent(
                "token $token",
                ownerName,
                repoName
            ).content

            decodeReadmeContent(encodedReadme)
        }

    override suspend fun signIn(token: String) =
        retrofitWrapException {
            api.authByToken("token $token")
        }

    private fun decodeReadmeContent(content: String): String =
         String(Base64.decode(content, Base64.DEFAULT))


    private fun getLanguageColor(language: String?) : Int? {
        if (language.isNullOrEmpty())
            return null

        val hexColor = languages[language]
        return Color.parseColor(hexColor)
    }
}


