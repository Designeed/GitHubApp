package com.example.githubapp.domain.models.response

import com.example.githubapp.domain.models.Repo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoResponse(
    @SerialName("owner")
    val owner: OwnerInfo,
    @SerialName("name")
    val name: String,
    @SerialName("language")
    val language: String?,
    @SerialName("description")
    val description: String?
) {
    fun toRepoModel(login: String, color: Int?) : Repo = Repo(
        login,
        name,
        language ?: "",
        description ?: "",
        color
    )
}

@Serializable
data class OwnerInfo(
    @SerialName("login")
    val login: String
)