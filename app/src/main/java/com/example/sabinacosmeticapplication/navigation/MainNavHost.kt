package com.example.sabinacosmeticapplication.navigation

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sabinacosmeticapplication.feature.cart.CartRoute
import com.example.sabinacosmeticapplication.feature.cart.CartViewModel
import com.example.sabinacosmeticapplication.feature.categories.CategoriesScreen
import com.example.sabinacosmeticapplication.feature.categoryproducts.CategoryProductsRoute
import com.example.sabinacosmeticapplication.feature.home.HomeRoute
import com.example.sabinacosmeticapplication.feature.my.MyScreen
import com.example.sabinacosmeticapplication.feature.productdetail.ProductDetailRoute
import com.example.sabinacosmeticapplication.feature.search.SearchRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private const val PRODUCT_DETAIL_ROUTE = "product_detail"
private const val PRODUCT_ID_ARG = "productId"

private const val CATEGORY_PRODUCTS_ROUTE = "category_products"
private const val CATEGORY_ARG = "category"

private val BottomBarContainerColor = Color.White
private val BottomBarContentColor = Color(0xFF2F7DF6)
private val BottomBarSelectedColor = Color(0xFF2F7DF6)
private val BottomBarUnselectedColor = Color(0xFF8B95A1)
private val BottomBarIndicatorColor = Color(0xFFEAF3FF)

private fun NavHostController.navigateToBottomRoute(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun NavHostController.navigateToProductDetail(productId: String) {
    navigate("$PRODUCT_DETAIL_ROUTE/$productId")
}

private fun NavHostController.navigateToCategoryProducts(category: String) {
    val encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8.toString())
    navigate("$CATEGORY_PRODUCTS_ROUTE/$encodedCategory")
}

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    val cartViewModel: CartViewModel = hiltViewModel()
    val cartUiState by cartViewModel.uiState.collectAsStateWithLifecycle()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = currentDestination.shouldShowBottomBar()

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MainBottomBar(
                    currentDestination = currentDestination,
                    cartItemCount = cartUiState.totalItemCount,
                    onItemClick = { route ->
                        navController.navigateToBottomRoute(route)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomRoute.Home.route
        ) {
            composable(BottomRoute.Home.route) {
                HomeRoute(
                    padding = innerPadding,
                    onSearchClick = {
                        navController.navigateToBottomRoute(BottomRoute.Search.route)
                    },
                    onProductClick = { productId ->
                        navController.navigateToProductDetail(productId)
                    }
                )
            }

            composable(BottomRoute.Categories.route) {
                CategoriesScreen(
                    padding = innerPadding,
                    onCategoryClick = { category ->
                        navController.navigateToCategoryProducts(category)
                    }
                )
            }

            composable(BottomRoute.Search.route) {
                SearchRoute(
                    padding = innerPadding,
                    onProductClick = { productId ->
                        navController.navigateToProductDetail(productId)
                    }
                )
            }

            composable(BottomRoute.My.route) {
                MyScreen(
                    padding = innerPadding
                )
            }

            composable(BottomRoute.Cart.route) {
                CartRoute(
                    padding = innerPadding,
                    onCheckoutClick = {
                        // Checkout screen qo‘shilganda shu yerga navigate yoziladi
                    }
                )
            }

            composable(route = "$CATEGORY_PRODUCTS_ROUTE/{$CATEGORY_ARG}") {
                CategoryProductsRoute(
                    padding = innerPadding,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onProductClick = { productId ->
                        navController.navigateToProductDetail(productId)
                    }
                )
            }

            composable(route = "$PRODUCT_DETAIL_ROUTE/{$PRODUCT_ID_ARG}") {
                ProductDetailRoute(
                    padding = innerPadding,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onCartClick = {
                        navController.navigateToBottomRoute(BottomRoute.Cart.route)
                    }
                )
            }
        }
    }
}

@Composable
private fun MainBottomBar(
    currentDestination: NavDestination?,
    cartItemCount: Int,
    onItemClick: (String) -> Unit
) {
    NavigationBar(
        containerColor = BottomBarContainerColor,
        contentColor = BottomBarContentColor
    ) {
        bottomItems.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { destination -> destination.route == item.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item.route) },
                icon = {
                    BottomBarIcon(
                        route = item.route,
                        icon = item.icon,
                        label = item.label,
                        cartItemCount = cartItemCount
                    )
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BottomBarSelectedColor,
                    selectedTextColor = BottomBarSelectedColor,
                    unselectedIconColor = BottomBarUnselectedColor,
                    unselectedTextColor = BottomBarUnselectedColor,
                    indicatorColor = BottomBarIndicatorColor
                )
            )
        }
    }
}

@Composable
private fun BottomBarIcon(
    route: String,
    icon: ImageVector,
    label: String,
    cartItemCount: Int
) {
    val isCartRoute = route == BottomRoute.Cart.route
    val shouldShowBadge = isCartRoute && cartItemCount > 0

    if (shouldShowBadge) {
        BadgedBox(
            badge = {
                Badge {
                    Text(
                        text = if (cartItemCount > 99) "99+" else cartItemCount.toString()
                    )
                }
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        }
    } else {
        Icon(
            imageVector = icon,
            contentDescription = label
        )
    }
}

private fun NavDestination?.shouldShowBottomBar(): Boolean {
    val route = this?.route
    return route in setOf(
        BottomRoute.Home.route,
        BottomRoute.Categories.route,
        BottomRoute.Search.route,
        BottomRoute.My.route,
        BottomRoute.Cart.route
    )
}