package com.example.sabinacosmeticapplication.feature.orders

sealed interface OrdersUiEvent {
    data class NavigateToOrderDetail(val orderId: String) : OrdersUiEvent
}