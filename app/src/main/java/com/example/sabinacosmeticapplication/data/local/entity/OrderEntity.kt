package com.example.sabinacosmeticapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val id: String,
    val status: String,
    val createdAtMillis: Long,
    val itemCount: Int,
    val subtotalPrice: Int,
    val shippingPrice: Int,
    val totalPrice: Int
)