package com.example.sabinacosmeticapplication.domain.usecase

import com.example.sabinacosmeticapplication.feature.cart.CartRepository
import javax.inject.Inject

class IncreaseCartItemQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.increaseQuantity(productId)
    }
}