package com.syafi.core.domain.usecase.local

import com.syafi.core.data.local.room.entity.FavoriteUser
import com.syafi.core.data.repository.GithubRepository
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteUserInteractor @Inject constructor(private val repo: GithubRepository): FavoriteUserUseCase {
    override suspend fun addFavoriteUser(user: GithubUser) {
        repo.addFavoriteUser(user)
    }

    override suspend fun deleteFavoriteUser(user: GithubUser) {
        repo.deleteFavoriteUser(user)
    }

    override fun getAllFavoriteUser(): Flow<Resource<List<FavoriteUser>>> =
        flow {
            emit(Resource.Loading())

            try {
                val data= repo.getAllFavoriteUser()
                data.collect {
                    emit(Resource.Success(it))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }

    override fun isUserFavorite(username: String): Flow<Boolean> {
        return repo.isUserFavorite(username)
    }
}