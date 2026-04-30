package com.example.sabinacosmeticapplication.domain.repository

import com.example.sabinacosmeticapplication.data.local.relation.OrderWithItems
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun placeOrder(
        items: List<OrderLineRequest>,
        subtotalPrice: Int,
        shippingPrice: Int,
        totalPrice: Int
    )

    fun observeOrders(): Flow<List<OrderWithItems>>

    suspend fun getOrderDetail(orderId: String): OrderWithItems
}

data class OrderLineRequest(
    val productId: String,
    val title: String,
    val brand: String,
    val category: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int
)