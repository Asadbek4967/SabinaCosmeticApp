package com.example.sabinacosmeticapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomRoute(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomRoute(
        route = "home",
        label = "Home",
        icon = Icons.Default.Home
    )

    data object Categories : BottomRoute(
        route = "categories",
        label = "Categories",
        icon = Icons.Default.Category
    )

    data object Search : BottomRoute(
        route = "search",
        label = "Search",
        icon = Icons.Default.Search
    )

    data object My : BottomRoute(
        route = "my",
        label = "My",
        icon = Icons.Default.Person
    )

    data object Cart : BottomRoute(
        route = "cart",
        label = "Cart",
        icon = Icons.Default.ShoppingCart
    )
}

val bottomItems = listOf(
    BottomRoute.Home,
    BottomRoute.Categories,
    BottomRoute.Search,
    BottomRoute.My,
    BottomRoute.Cart
)