package com.syafi.core.domain.usecase.remote

import com.syafi.core.domain.model.GithubUser
import com.syafi.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    suspend fun getAllUsers(): Flow<Resource<List<GithubUser>>>
    suspend fun getDetailUser(username: String): Flow<Resource<GithubUser>>
    suspend fun getUserFollowers(username: String): Flow<Int>
    suspend fun getUserFollowing(username: String): Flow<Int>
}