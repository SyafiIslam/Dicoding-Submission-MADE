package com.syafi.core.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.syafi.core.data.local.room.entity.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert
    suspend fun addFavoriteUser(user: FavoriteUser)

    @Delete
    suspend fun deleteFavoriteUser(user: FavoriteUser)

    @Query("SELECT * FROM favoriteuser")
    fun getAllFavoriteUser(): Flow<List<FavoriteUser>>

    @Query("SELECT EXISTS (SELECT * FROM FavoriteUser WHERE login = :username)")
    fun isFavoriteUser(username: String): Flow<Boolean>
}