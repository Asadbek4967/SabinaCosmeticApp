package com.example.sabinacosmeticapplication.feature.orders

data class OrderCardUi(
    val orderId: String,
    val itemCount: Int,
    val subtotalPrice: Int,
    val shippingFee: Int,
    val totalPrice: Int,
    val status: String,
    val createdAtMillis: Long,
    val items: List<OrderItemUi>
)