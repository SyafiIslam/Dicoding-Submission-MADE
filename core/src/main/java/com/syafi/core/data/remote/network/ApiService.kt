package com.syafi.core.data.remote.network

import com.syafi.core.data.remote.response.GithubUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getAllUser(): Response<List<GithubUserResponse>>

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): Response<GithubUserResponse>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") username: String
    ): Response<List<GithubUserResponse>>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") username: String
    ): Response<List<GithubUserResponse>>
}