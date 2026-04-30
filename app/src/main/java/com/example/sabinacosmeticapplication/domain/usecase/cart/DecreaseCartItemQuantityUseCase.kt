package com.example.sabinacosmeticapplication.domain.usecase.cart

import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import javax.inject.Inject

class DecreaseCartItemQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: String) {
        val currentItems = repository.getCartItemsOnce()
        val targetItem = currentItems.firstOrNull { it.productId == productId } ?: return

        val newQuantity = targetItem.quantity - 1
        repository.updateQuantity(productId, newQuantity)
    }
}