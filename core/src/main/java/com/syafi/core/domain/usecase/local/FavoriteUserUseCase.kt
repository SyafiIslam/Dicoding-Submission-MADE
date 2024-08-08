package com.syafi.core.domain.usecase.local

import com.syafi.core.data.local.room.entity.FavoriteUser
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteUserUseCase {
    suspend fun addFavoriteUser(user: GithubUser)
    suspend fun deleteFavoriteUser(user: GithubUser)
    fun getAllFavoriteUser(): Flow<Resource<List<FavoriteUser>>>
    fun isUserFavorite(username: String): Flow<Boolean>
}