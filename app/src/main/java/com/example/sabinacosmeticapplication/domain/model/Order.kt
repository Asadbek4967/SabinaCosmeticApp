package com.example.sabinacosmeticapplication.domain.model

data class Order(
    val orderId: String,
    val createdAtMillis: Long,
    val status: String,
    val items: List<OrderItem>,
    val itemCount: Int,
    val subtotalPrice: Int,
    val shippingFee: Int,
    val totalPrice: Int,
    val deliveryName: String,
    val deliveryPhone: String,
    val deliveryAddress: String,
    val paymentMethod: String
)