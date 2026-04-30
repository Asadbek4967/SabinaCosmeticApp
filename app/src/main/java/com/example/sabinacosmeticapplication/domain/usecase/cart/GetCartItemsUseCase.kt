package com.example.sabinacosmeticapplication.domain.usecase.cart

import com.example.sabinacosmeticapplication.domain.model.CartItem
import com.example.sabinacosmeticapplication.domain.repository.CartRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return repository.observeCartItems()
    }
}