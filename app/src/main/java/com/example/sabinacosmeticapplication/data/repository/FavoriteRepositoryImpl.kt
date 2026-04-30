package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.local.favorite.FavoriteDao
import com.example.sabinacosmeticapplication.data.local.favorite.FavoriteEntity
import com.example.sabinacosmeticapplication.domain.repository.FavoriteRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override fun observeFavoriteIds(): Flow<Set<String>> {
        return favoriteDao.observeFavoriteIds().map { ids ->
            ids.toSet()
        }
    }

    override fun observeIsFavorite(productId: String): Flow<Boolean> {
        return favoriteDao.observeIsFavorite(productId)
    }

    override suspend fun addFavorite(productId: String) {
        favoriteDao.insertFavorite(
            FavoriteEntity(productId = productId)
        )
    }

    override suspend fun removeFavorite(productId: String) {
        favoriteDao.deleteFavoriteByProductId(productId)
    }

    override suspend fun toggleFavorite(productId: String) {
        if (favoriteDao.isFavorite(productId)) {
            favoriteDao.deleteFavoriteByProductId(productId)
        } else {
            favoriteDao.insertFavorite(
                FavoriteEntity(productId = productId)
            )
        }
    }
}