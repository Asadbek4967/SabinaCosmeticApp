package com.example.sabinacosmeticapplication.feature.productdetail

import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.example.sabinacosmeticapplication.core.util.PriceFormatter
import com.example.sabinacosmeticapplication.data.mapper.toUiProduct
import com.example.sabinacosmeticapplication.feature.productdetail.components.ProductDetailBottomBar
import com.example.sabinacosmeticapplication.feature.productdetail.components.ProductDetailContent
import com.example.sabinacosmeticapplication.feature.productdetail.components.ProductDetailError
import com.example.sabinacosmeticapplication.feature.productdetail.components.ProductDetailLoading
import com.example.sabinacosmeticapplication.ui.components.common.AppTopBar
import com.example.sabinacosmeticapplication.ui.theme.AppColors

@Composable
fun ProductDetailScreen(
    uiState: ProductDetailUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onAction: (ProductDetailUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val displayProduct = uiState.product?.toUiProduct()
    val unitPriceText = displayProduct?.formattedPrice.orEmpty()
    val totalPriceText = calculateTotalPriceText(
        unitPriceText = unitPriceText,
        quantity = uiState.safeQuantity
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppColors.Background,
        topBar = {
            ProductDetailTopBar(
                isFavorite = uiState.isFavorite,
                onBackClick = onBackClick,
                onCartClick = onCartClick,
                onFavoriteClick = {
                    onAction(ProductDetailUiAction.ToggleFavoriteClick)
                }
            )
        },
        snackbarHost = {
            Box(
                modifier = Modifier.navigationBarsPadding()
            ) {
                SnackbarHost(hostState = snackbarHostState)
            }
        },
        bottomBar = {
            if (uiState.showContent && displayProduct != null) {
                ProductDetailBottomBar(
                    unitPriceText = unitPriceText,
                    totalPriceText = totalPriceText,
                    quantity = uiState.safeQuantity,
                    onIncrease = {
                        onAction(ProductDetailUiAction.IncreaseQuantityClick)
                    },
                    onDecrease = {
                        onAction(ProductDetailUiAction.DecreaseQuantityClick)
                    },
                    onAddToCartClick = {
                        onAction(ProductDetailUiAction.AddToCartClick)
                    }
                )
            }
        }
    ) { innerPadding ->
        ProductDetailBody(
            uiState = uiState,
            onAction = onAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun ProductDetailTopBar(
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.12f else 1f,
        animationSpec = spring(
            dampingRatio = 0.45f,
            stiffness = 500f
        ),
        label = "favorite_scale"
    )

    AppTopBar(
        modifier = modifier.statusBarsPadding(),
        title = "Product Detail",
        onBackClick = onBackClick,
        actions = {
            IconButton(
                onClick = onFavoriteClick
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = if (isFavorite) {
                        "Remove from favorites"
                    } else {
                        "Add to favorites"
                    },
                    tint = if (isFavorite) {
                        AppColors.Price
                    } else {
                        AppColors.Primary
                    },
                    modifier = Modifier.graphicsLayer {
                        scaleX = favoriteScale
                        scaleY = favoriteScale
                    }
                )
            }

            IconButton(
                onClick = onCartClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Cart",
                    tint = AppColors.Primary
                )
            }
        }
    )
}

@Composable
private fun ProductDetailBody(
    uiState: ProductDetailUiState,
    onAction: (ProductDetailUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            ProductDetailLoading(
                modifier = modifier
            )
        }

        uiState.showContent -> {
            ProductDetailContent(
                uiState = uiState,
                onAction = onAction,
                modifier = modifier
            )
        }

        else -> {
            ProductDetailError(
                message = uiState.errorMessage ?: "Unable to load product details.",
                onRetry = {
                    onAction(ProductDetailUiAction.RetryClick)
                },
                modifier = modifier
            )
        }
    }
}

private fun calculateTotalPriceText(
    unitPriceText: String,
    quantity: Int
): String {
    val unitPrice = unitPriceText
        .filter { it.isDigit() }
        .toIntOrNull()
        ?: 0

    val safeQuantity = quantity.coerceAtLeast(1)
    return PriceFormatter.formatWon(unitPrice * safeQuantity)
}