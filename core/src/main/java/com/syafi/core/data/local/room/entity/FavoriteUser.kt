package com.syafi.core.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUser(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String,
)
