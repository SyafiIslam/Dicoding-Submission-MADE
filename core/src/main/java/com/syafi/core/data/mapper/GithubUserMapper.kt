package com.syafi.core.data.mapper

import com.syafi.core.data.local.room.entity.FavoriteUser
import com.syafi.core.data.remote.response.GithubUserResponse
import com.syafi.core.domain.model.GithubUser

object GithubUserMapper {

    fun mapResponseToDomain(resp: List<GithubUserResponse>): List<GithubUser> =
        resp.map {
            GithubUser(
                avatar_url = it.avatar_url,
                followers_url = it.followers_url,
                following_url = it.following_url,
                id = it.id,
                login = it.login,
                url = it.url
            )
        }

    fun mapDomainToEntity(domain: GithubUser): FavoriteUser =
        FavoriteUser(
            id = domain.id,
            login = domain.login,
            avatarUrl = domain.avatar_url
        )
}