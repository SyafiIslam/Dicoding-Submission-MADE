package com.syafi.core.domain.usecase.remote

import com.syafi.core.data.repository.GithubRepository
import com.syafi.core.domain.model.GithubUser
import com.syafi.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubUserIntreactor @Inject constructor (private val repo: GithubRepository): GithubUserUseCase {
    override suspend fun getAllUsers(): Flow<Resource<List<GithubUser>>> {
        return repo.getAllUser()
    }

    override suspend fun getDetailUser(username: String): Flow<Resource<GithubUser>> {
        return repo.getDetailUser(username)
    }

    override suspend fun getUserFollowers(username: String): Flow<Int> {
        return repo.getUserFollowers(username)
    }

    override suspend fun getUserFollowing(username: String): Flow<Int> {
        return repo.getUserFollowing(username)
    }
}