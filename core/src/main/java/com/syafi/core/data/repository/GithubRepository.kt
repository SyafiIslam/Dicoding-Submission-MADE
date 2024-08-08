package com.syafi.core.data.repository

import com.syafi.core.data.local.room.GithubUserDatabase
import com.syafi.core.data.local.room.entity.FavoriteUser
import com.syafi.core.data.mapper.GithubUserMapper
import com.syafi.core.data.remote.network.ApiService
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.domain.repository.IGithubRepository
import com.syafi.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: GithubUserDatabase
) : IGithubRepository {

    override suspend fun getAllUser(): Flow<Resource<List<GithubUser>>> =
        flow {
            emit(Resource.Loading())

            val resp = apiService.getAllUser()

            if (resp.isSuccessful) {
                resp.body()?.let {
                    emit(Resource.Success(GithubUserMapper.mapResponseToDomain(it)))
                }
            } else {
                emit(Resource.Error("${resp.errorBody()?.string()}"))
            }
        }

    override suspend fun getDetailUser(username: String): Flow<Resource<GithubUser>> =
        flow {
            emit(Resource.Loading())

            val resp = apiService.getDetailUser(username)

            if (resp.isSuccessful) {
                resp.body()?.let {
                    val userData = GithubUser(
                        avatar_url = it.avatar_url,
                        followers_url = it.followers_url,
                        following_url = it.following_url,
                        id = it.id,
                        login = it.login,
                        url = it.url
                    )

                    emit(Resource.Success(userData))
                }
            } else {
                emit(Resource.Error("${resp.errorBody()?.string()}"))
            }
        }

    override suspend fun getUserFollowers(username: String): Flow<Int> =
        flow {
            val resp = apiService.getUserFollowers(username)

            if (resp.isSuccessful) {
                resp.body()?.let {
                    emit(GithubUserMapper.mapResponseToDomain(it).size)
                }
            } else {
                emit(0)
            }
        }

    override suspend fun getUserFollowing(username: String): Flow<Int> =
        flow {
            val resp = apiService.getUserFollowing(username)

            if (resp.isSuccessful) {
                resp.body()?.let {
                    emit(GithubUserMapper.mapResponseToDomain(it).size)
                }
            } else {
                emit(0)
            }
        }

    override suspend fun addFavoriteUser(user: GithubUser) {
        db.dao().addFavoriteUser(GithubUserMapper.mapDomainToEntity(user))
    }

    override suspend fun deleteFavoriteUser(user: GithubUser) {
        db.dao().deleteFavoriteUser(GithubUserMapper.mapDomainToEntity(user))
    }

    override fun getAllFavoriteUser(): Flow<List<FavoriteUser>> =
        db.dao().getAllFavoriteUser()

    override fun isUserFavorite(username: String): Flow<Boolean> =
        db.dao().isFavoriteUser(username)
}