package com.example.sabinacosmeticapplication.domain.usecase

import com.example.sabinacosmeticapplication.feature.cart.CartItemUi
import com.example.sabinacosmeticapplication.feature.cart.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItemUi>> {
        return repository.cartItems
    }
}