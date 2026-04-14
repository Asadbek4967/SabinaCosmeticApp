package com.example.sabinacosmeticapplication.feature.orders

import com.example.sabinacosmeticapplication.data.local.relation.OrderWithItems

sealed interface OrderDetailUiState {
    data object Loading : OrderDetailUiState
    data class Success(val order: OrderWithItems) : OrderDetailUiState
    data class Error(val message: String) : OrderDetailUiState
}