package com.syafi.core.domain.model

data class GithubUser(
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val id: Int,
    val login: String,
    val url: String
)
