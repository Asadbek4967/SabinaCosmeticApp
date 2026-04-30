package com.example.sabinacosmeticapplication.feature.my

data class MyUiState(
    val title: String = "My Account",
    val subtitle: String = "Orders, wishlist, support, and preferences in one place.",
    val isLogoutDialogVisible: Boolean = false,
    val isLoggingOut: Boolean = false
)