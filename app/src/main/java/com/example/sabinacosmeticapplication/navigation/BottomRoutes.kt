package com.example.sabinacosmeticapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomRoute(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomRoute(
        route = "home",
        label = "Home",
        icon = Icons.Outlined.Home
    )

    data object Categories : BottomRoute(
        route = "categories",
        label = "Categories",
        icon = Icons.Outlined.Category
    )

    data object Search : BottomRoute(
        route = "search",
        label = "Search",
        icon = Icons.Outlined.Search
    )

    data object My : BottomRoute(
        route = "my",
        label = "My",
        icon = Icons.Outlined.Person
    )

    data object Cart : BottomRoute(
        route = "cart",
        label = "Cart",
        icon = Icons.Outlined.ShoppingCart
    )
}

val bottomItems = listOf(
    BottomRoute.Home,
    BottomRoute.Categories,
    BottomRoute.Search,
    BottomRoute.My,
    BottomRoute.Cart
)