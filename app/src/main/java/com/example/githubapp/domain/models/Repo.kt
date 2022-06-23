package com.example.githubapp.domain.models

data class Repo(
    val owner: String,
    val name: String,
    val language: String,
    val description: String
)
