package com.example.sabinacosmeticapplication.feature.orders

sealed interface OrdersUiAction {
    data class OpenOrderDetail(val orderId: String) : OrdersUiAction
}