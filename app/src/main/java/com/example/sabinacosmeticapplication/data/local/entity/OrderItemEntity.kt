package com.example.sabinacosmeticapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey
    val id: String,
    val orderId: String,
    val productId: String,
    val title: String,
    val brand: String,
    val category: String,
    val imageUrl: String,
    val price: Int,
    val quantity: Int
)