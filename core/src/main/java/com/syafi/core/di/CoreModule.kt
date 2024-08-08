package com.syafi.core.di

import android.content.Context
import androidx.room.Room
import com.syafi.core.data.local.room.GithubUserDatabase
import com.syafi.core.data.remote.network.ApiConfig
import com.syafi.core.data.remote.network.ApiService
import com.syafi.core.data.repository.GithubRepository
import com.syafi.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideApi(): ApiService =
        ApiConfig.apiService

    @Provides
    @Singleton
    fun provideGithubRepository(apiService: ApiService, db: GithubUserDatabase): GithubRepository=
        GithubRepository(apiService, db)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GithubUserDatabase {

        val password= SQLiteDatabase.getBytes(Constants.DECIPHER_KEY.toCharArray())
        val factory= SupportFactory(password)

        val db= Room.databaseBuilder(
            context,
            GithubUserDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()

        return db
    }
}