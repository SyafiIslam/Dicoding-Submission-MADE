package com.syafi.core.di

import com.syafi.core.data.repository.GithubRepository
import com.syafi.core.domain.usecase.local.FavoriteUserInteractor
import com.syafi.core.domain.usecase.local.FavoriteUserUseCase
import com.syafi.core.domain.usecase.remote.GithubUserIntreactor
import com.syafi.core.domain.usecase.remote.GithubUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGithubUserUseCase(repository: GithubRepository): GithubUserUseCase {
        return GithubUserIntreactor(repository)
    }

    @Provides
    @Singleton
    fun provideFavoriteUserUseCase(repository: GithubRepository): FavoriteUserUseCase {
        return FavoriteUserInteractor(repository)
    }
}