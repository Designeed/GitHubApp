package com.example.githubapp.domain.models

data class RepoInfo(
    val repoId: Long,
    val repoName: String,
    val repoLanguage: String,
    val repoLanguageColor: String,
    val repoDescription: String
)
