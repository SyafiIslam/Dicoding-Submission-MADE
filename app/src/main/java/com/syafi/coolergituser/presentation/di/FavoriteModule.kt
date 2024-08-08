package com.syafi.coolergituser.presentation.di

import com.syafi.core.domain.usecase.local.FavoriteUserUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModule {
    fun provideGetAllFavoriteUserUseCase(): FavoriteUserUseCase
}