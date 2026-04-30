package com.example.sabinacosmeticapplication.domain.usecase.favorite

import com.example.sabinacosmeticapplication.domain.repository.FavoriteRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveIsFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(productId: String): Flow<Boolean> {
        return repository.observeIsFavorite(productId)
    }
}