package com.example.sabinacosmeticapplication.data.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteEntity(
    @PrimaryKey
    val productId: String,
    val addedAt: Long = System.currentTimeMillis()
)