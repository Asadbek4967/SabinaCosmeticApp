package com.example.sabinacosmeticapplication.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.sabinacosmeticapplication.data.local.entity.OrderEntity
import com.example.sabinacosmeticapplication.data.local.entity.OrderItemEntity

data class OrderWithItems(
    @Embedded
    val order: OrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItemEntity>
)