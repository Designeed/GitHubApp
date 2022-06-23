package com.example.githubapp.domain.models

data class RepoDetails(
    val repoLink: String,
    val license: String,
    val stars: String,
    val forks: String,
    val watchers: String
)