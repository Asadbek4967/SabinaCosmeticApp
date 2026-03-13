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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sabinacosmeticapplication.feature.cart.CartScreen
import com.example.sabinacosmeticapplication.feature.cart.CartViewModel
import com.example.sabinacosmeticapplication.feature.categories.CategoriesScreen
import com.example.sabinacosmeticapplication.feature.home.HomeRoute
import com.example.sabinacosmeticapplication.feature.my.MyScreen
import com.example.sabinacosmeticapplication.feature.productdetail.ProductDetailRoute
import com.example.sabinacosmeticapplication.feature.search.SearchRoute

private const val PRODUCT_DETAIL_ROUTE = "product_detail"
private const val PRODUCT_ID_ARG = "productId"

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

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    val cartViewModel: CartViewModel = viewModel()
    val cartItemCount by cartViewModel.cartItemCount.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color(0xFF2F7DF6)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomItems.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any { destination ->
                        destination.route == item.route
                    } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigateToBottomRoute(item.route)
                        },
                        icon = {
                            if (item.route == BottomRoute.Cart.route && cartItemCount > 0) {
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
                                        imageVector = item.icon,
                                        contentDescription = item.label
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            }
                        },
                        label = {
                            Text(text = item.label)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2F7DF6),
                            selectedTextColor = Color(0xFF2F7DF6),
                            unselectedIconColor = Color(0xFF8B95A1),
                            unselectedTextColor = Color(0xFF8B95A1),
                            indicatorColor = Color(0xFFEAF3FF)
                        )
                    )
                }
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
                CategoriesScreen(padding = innerPadding)
            }

            composable(BottomRoute.Search.route) {
                SearchRoute(
                    padding = innerPadding,
                    onProductClick = { productId ->
                        navController.navigateToProductDetail(productId)
                    }
                )
            }

            composable(BottomRoute.Cart.route) {
                CartScreen(
                    padding = innerPadding,
                    viewModel = cartViewModel
                )
            }

            composable(BottomRoute.My.route) {
                MyScreen(padding = innerPadding)
            }

            composable(
                route = "$PRODUCT_DETAIL_ROUTE/{$PRODUCT_ID_ARG}",
                arguments = listOf(
                    navArgument(PRODUCT_ID_ARG) {
                        type = NavType.StringType
                    }
                )
            ) {
                ProductDetailRoute(
                    padding = innerPadding,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}