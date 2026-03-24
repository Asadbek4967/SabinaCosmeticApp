package com.example.sabinacosmeticapplication.domain.usecase.cart

import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import javax.inject.Inject

class IncreaseCartItemQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.increaseQuantity(productId)
    }
}