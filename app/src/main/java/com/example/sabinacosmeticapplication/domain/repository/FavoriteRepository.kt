package com.example.sabinacosmeticapplication.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavoriteIds(): Flow<Set<String>>
    fun observeIsFavorite(productId: String): Flow<Boolean>
    suspend fun addFavorite(productId: String)
    suspend fun removeFavorite(productId: String)
    suspend fun toggleFavorite(productId: String)
}