package com.syafi.core.domain.repository

import com.syafi.core.data.local.room.entity.FavoriteUser
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {

    suspend fun getAllUser(): Flow<Resource<List<GithubUser>>>
    suspend fun getDetailUser(username: String): Flow<Resource<GithubUser>>
    suspend fun getUserFollowers(username: String): Flow<Int>
    suspend fun getUserFollowing(username: String): Flow<Int>
    suspend fun addFavoriteUser(user: GithubUser)
    suspend fun deleteFavoriteUser(user: GithubUser)
    fun getAllFavoriteUser(): Flow<List<FavoriteUser>>
    fun isUserFavorite(username: String): Flow<Boolean>
}