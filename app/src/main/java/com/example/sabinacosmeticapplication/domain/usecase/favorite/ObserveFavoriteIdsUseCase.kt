package com.example.sabinacosmeticapplication.domain.usecase.favorite

import com.example.sabinacosmeticapplication.domain.repository.FavoriteRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveFavoriteIdsUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        return repository.observeFavoriteIds()
    }
}