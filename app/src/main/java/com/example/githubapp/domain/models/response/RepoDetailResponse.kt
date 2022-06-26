package com.example.githubapp.domain.models.response

import com.example.githubapp.domain.models.RepoDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDetailResponse(
    @SerialName("html_url")
    val url: String,
    @SerialName("license")
    val license: LicenseResponse?,
    @SerialName("stargazers_count")
    val stars: Int,
    @SerialName("forks_count")
    val forks: Int,
    @SerialName("watchers_count")
    val watchers: Int
) {
    fun toRepoDetailModel() : RepoDetails =
        RepoDetails(
            url.replace("https://", ""),
            license?.name ?: "",
            stars.toString(),
            forks.toString(),
            watchers.toString()
        )
}

@Serializable
data class LicenseResponse(
    @SerialName("spdx_id")
    val name: String
)