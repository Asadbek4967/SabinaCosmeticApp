package com.example.sabinacosmeticapplication.domain.usecase.favorite

import com.example.sabinacosmeticapplication.domain.repository.FavoriteRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.toggleFavorite(productId)
    }
}