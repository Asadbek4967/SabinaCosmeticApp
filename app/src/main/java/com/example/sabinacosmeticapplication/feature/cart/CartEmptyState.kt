package com.example.sabinacosmeticapplication.feature.cart

data class CartEmptyState(
    val title: String = "Your cart is empty",
    val description: String = "Looks like you haven’t added any beauty essentials yet. Start exploring and build your perfect routine.",
    val actionLabel: String = "Start shopping"
)