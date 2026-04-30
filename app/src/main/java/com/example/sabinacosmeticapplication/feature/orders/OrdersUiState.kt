package com.example.sabinacosmeticapplication.feature.orders

import com.example.sabinacosmeticapplication.data.local.relation.OrderWithItems

data class OrdersUiState(
    val isLoading: Boolean = true,
    val orders: List<OrderWithItems> = emptyList()
)