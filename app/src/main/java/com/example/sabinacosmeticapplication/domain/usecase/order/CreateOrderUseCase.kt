package com.example.sabinacosmeticapplication.domain.usecase.order

import com.example.sabinacosmeticapplication.domain.model.OrderItem
import com.example.sabinacosmeticapplication.domain.repository.OrderLineRequest
import com.example.sabinacosmeticapplication.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(
        items: List<OrderItem>,
        subtotalPrice: Int,
        shippingPrice: Int,
        totalPrice: Int
    ) {
        val safeItems = items.filter { it.productId.isNotBlank() && it.quantity > 0 }

        if (safeItems.isEmpty()) return

        val requests = safeItems.map { item ->
            OrderLineRequest(
                productId = item.productId,
                title = item.title,
                brand = item.brand,
                category = "",
                imageUrl = item.imageUrl,
                price = item.priceValue,
                quantity = item.quantity
            )
        }

        orderRepository.placeOrder(
            items = requests,
            subtotalPrice = subtotalPrice.coerceAtLeast(0),
            shippingPrice = shippingPrice.coerceAtLeast(0),
            totalPrice = totalPrice.coerceAtLeast(0)
        )
    }
}