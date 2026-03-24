package com.example.sabinacosmeticapplication.domain.usecase.cart

import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import javax.inject.Inject

class DecreaseCartItemQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.decreaseQuantity(productId)
    }
}