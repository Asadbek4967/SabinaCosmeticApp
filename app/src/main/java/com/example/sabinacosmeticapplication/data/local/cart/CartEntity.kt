package com.example.sabinacosmeticapplication.data.local.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey
    val productId: String,
    val quantity: Int
)