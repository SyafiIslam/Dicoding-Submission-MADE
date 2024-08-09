package com.syafi.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syafi.core.data.local.room.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class GithubUserDatabase: RoomDatabase() {
    abstract fun dao(): RoomDao
}
