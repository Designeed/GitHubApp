package com.example.githubapp.domain.models.response

import com.example.githubapp.domain.models.Repo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.security.acl.Owner

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
    fun toRepoModel(login: String) : Repo = Repo(
        login,
        name,
        language ?: "",
        description ?: "",
        null
    )
}

@Serializable
data class OwnerInfo(
    @SerialName("login")
    val login: String
)