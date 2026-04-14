package com.example.sabinacosmeticapplication.core.navigation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.sabinacosmeticapplication.core.security.SessionEvent
import com.example.sabinacosmeticapplication.feature.auth.LoginRoute
import com.example.sabinacosmeticapplication.feature.cart.CartRoute
import com.example.sabinacosmeticapplication.feature.categories.CategoriesRoute
import com.example.sabinacosmeticapplication.feature.categoryproducts.CategoryProductsRoute
import com.example.sabinacosmeticapplication.feature.checkout.CheckoutRoute
import com.example.sabinacosmeticapplication.feature.checkout.OrderSuccessScreen
import com.example.sabinacosmeticapplication.feature.favorites.FavoritesRoute
import com.example.sabinacosmeticapplication.feature.home.HomeRoute
import com.example.sabinacosmeticapplication.feature.my.MyRoute
import com.example.sabinacosmeticapplication.feature.orders.OrderDetailRoute
import com.example.sabinacosmeticapplication.feature.orders.OrdersRoute
import com.example.sabinacosmeticapplication.feature.productdetail.ProductDetailRoute
import com.example.sabinacosmeticapplication.feature.search.SearchRoute
import com.example.sabinacosmeticapplication.ui.theme.AppColors

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

private val bottomNavItems = listOf(
    BottomNavItem(
        route = AppDestinations.HOME,
        label = "Home",
        icon = Icons.Outlined.Home,
    ),
    BottomNavItem(
        route = AppDestinations.CATEGORIES,
        label = "Categories",
        icon = Icons.Outlined.Category,
    ),
    BottomNavItem(
        route = AppDestinations.SEARCH,
        label = "Search",
        icon = Icons.Outlined.Search,
    ),
    BottomNavItem(
        route = AppDestinations.MY,
        label = "My",
        icon = Icons.Outlined.PersonOutline,
    ),
    BottomNavItem(
        route = AppDestinations.CART,
        label = "Cart",
        icon = Icons.Outlined.ShoppingCart,
    ),
)

private val bottomBarRoutes = setOf(
    AppDestinations.HOME,
    AppDestinations.CATEGORIES,
    AppDestinations.SEARCH,
    AppDestinations.MY,
    AppDestinations.CART,
)

private val protectedRoutes = setOf(
    AppDestinations.HOME,
    AppDestinations.CATEGORIES,
    AppDestinations.SEARCH,
    AppDestinations.MY,
    AppDestinations.CART,
    AppDestinations.FAVORITES,
    AppDestinations.CHECKOUT,
    AppDestinations.ORDER_SUCCESS,
    AppDestinations.ORDERS,
    AppDestinations.ORDER_DETAIL,
    AppDestinations.PRODUCT_DETAIL,
    AppDestinations.CATEGORY_PRODUCTS,
)

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    sessionViewModel: AppSessionViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val sessionState by sessionViewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    if (!sessionState.isReady) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(AppColors.Background),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LaunchedEffect(Unit) {
        for (event in sessionViewModel.events) {
            when (event) {
                SessionEvent.SessionExpired -> {
                    snackbarHostState.showSnackbar(
                        message = "Session expired. Please login again.",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    LaunchedEffect(sessionState.isLoggedIn, currentRoute) {
        if (!sessionState.isLoggedIn && currentRoute != null) {
            val isProtected = protectedRoutes.any { route ->
                currentRoute.startsWith(route)
            }

            if (isProtected) {
                navController.navigate(AppDestinations.LOGIN) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }

        if (sessionState.isLoggedIn && currentRoute == AppDestinations.LOGIN) {
            navController.navigate(AppDestinations.HOME) {
                popUpTo(AppDestinations.LOGIN) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    val shouldShowBottomBar = sessionState.isLoggedIn && currentRoute in bottomBarRoutes

    Scaffold(
        modifier = modifier,
        containerColor = AppColors.Background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (shouldShowBottomBar) {
                AppBottomBar(
                    currentDestination = currentDestination,
                    onItemClick = { route ->
                        navController.navigateToBottomBarRoute(route)
                    },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (sessionState.isLoggedIn) {
                AppDestinations.HOME
            } else {
                AppDestinations.LOGIN
            },
        ) {
            composable(AppDestinations.LOGIN) {
                LoginRoute(
                    onLoginSuccess = {
                        navController.navigate(AppDestinations.HOME) {
                            popUpTo(AppDestinations.LOGIN) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable(AppDestinations.HOME) {
                HomeRoute(
                    padding = innerPadding,
                    onSearchClick = {
                        navController.navigateToBottomBarRoute(AppDestinations.SEARCH)
                    },
                    onProductClick = { productId ->
                        navController.navigate("${AppDestinations.PRODUCT_DETAIL}/$productId")
                    },
                    onCategoryClick = { categoryTitle ->
                        navController.navigate(
                            "${AppDestinations.CATEGORY_PRODUCTS}/home/${Uri.encode(categoryTitle)}"
                        )
                    },
                    onCategoriesSeeAllClick = {
                        navController.navigateToBottomBarRoute(AppDestinations.CATEGORIES)
                    },
                )
            }

            composable(AppDestinations.CATEGORIES) {
                CategoriesRoute(
                    padding = innerPadding,
                    onCategoryClick = { categoryId, categoryTitle ->
                        navController.navigate(
                            "${AppDestinations.CATEGORY_PRODUCTS}/$categoryId/${Uri.encode(categoryTitle)}"
                        )
                    },
                )
            }

            composable(AppDestinations.SEARCH) {
                SearchRoute(
                    padding = innerPadding,
                    onProductClick = { productId ->
                        navController.navigate("${AppDestinations.PRODUCT_DETAIL}/$productId")
                    },
                )
            }

            composable(AppDestinations.CART) {
                CartRoute(
                    onCheckoutClick = {
                        navController.navigate(AppDestinations.CHECKOUT)
                    },
                    onStartShoppingClick = {
                        navController.navigateToBottomBarRoute(AppDestinations.HOME)
                    },
                )
            }

            composable(AppDestinations.MY) {
                MyRoute(
                    padding = innerPadding,
                    onWishlistClick = {
                        navController.navigate(AppDestinations.FAVORITES)
                    },
                    onOrdersClick = {
                        navController.navigate(AppDestinations.ORDERS)
                    },
                    onLogoutCompleted = {
                        navController.navigate(AppDestinations.LOGIN) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable(AppDestinations.FAVORITES) {
                FavoritesRoute(
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate("${AppDestinations.PRODUCT_DETAIL}/$productId")
                    },
                )
            }

            composable(
                route = AppDestinations.PRODUCT_DETAIL_ROUTE,
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.StringType
                    },
                ),
            ) {
                ProductDetailRoute(
                    padding = PaddingValues(),
                    onBackClick = { navController.popBackStack() },
                    onCartClick = {
                        navController.navigate(AppDestinations.CART)
                    },
                )
            }

            composable(
                route = AppDestinations.CATEGORY_PRODUCTS_ROUTE,
                arguments = listOf(
                    navArgument("categoryId") {
                        type = NavType.StringType
                    },
                    navArgument("categoryTitle") {
                        type = NavType.StringType
                    },
                ),
            ) {
                CategoryProductsRoute(
                    padding = PaddingValues(),
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { productId ->
                        navController.navigate("${AppDestinations.PRODUCT_DETAIL}/$productId")
                    },
                )
            }

            composable(AppDestinations.CHECKOUT) {
                CheckoutRoute(
                    snackbarHostState = snackbarHostState,
                    onOrderPlaced = {
                        navController.navigate(AppDestinations.ORDER_SUCCESS) {
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable(AppDestinations.ORDER_SUCCESS) {
                OrderSuccessScreen(
                    onContinueShopping = {
                        navController.navigateToBottomBarRoute(AppDestinations.HOME)
                    },
                )
            }

            composable(AppDestinations.ORDERS) {
                OrdersRoute(
                    onBackClick = { navController.popBackStack() },
                    onOrderClick = { orderId ->
                        navController.navigate("${AppDestinations.ORDER_DETAIL}/$orderId")
                    },
                )
            }

            composable(
                route = "${AppDestinations.ORDER_DETAIL}/{orderId}",
                arguments = listOf(
                    navArgument("orderId") {
                        type = NavType.StringType
                    },
                ),
            ) {
                OrderDetailRoute(
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    currentDestination: NavDestination?,
    onItemClick: (String) -> Unit,
) {
    Surface(
        color = AppColors.Surface,
        shadowElevation = 6.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(item.route)

                BottomBarItem(
                    modifier = Modifier.weight(1f),
                    label = item.label,
                    icon = item.icon,
                    selected = selected,
                    onClick = { onItemClick(item.route) },
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = if (selected) AppColors.Primary else AppColors.SecondaryText

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (selected) {
                        AppColors.Primary.copy(alpha = 0.10f)
                    } else {
                        AppColors.Surface
                    },
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(20.dp),
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(route: String): Boolean {
    return this?.hierarchy?.any { destination ->
        destination.route == route
    } == true
}

private fun NavHostController.navigateToBottomBarRoute(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}