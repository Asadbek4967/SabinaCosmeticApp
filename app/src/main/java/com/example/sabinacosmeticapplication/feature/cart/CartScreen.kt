package com.example.sabinacosmeticapplication.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.feature.cart.components.CartBottomBar
import com.example.sabinacosmeticapplication.feature.cart.components.CartItemCard
import com.example.sabinacosmeticapplication.feature.cart.components.CartSummarySection
import com.example.sabinacosmeticapplication.feature.cart.components.EmptyCartContent
import com.example.sabinacosmeticapplication.ui.components.common.AppTopBar
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val CartBottomSpacing = 220.dp

@Composable
fun CartScreen(
    uiState: CartUiState,
    snackbarHostState: SnackbarHostState,
    onAction: (CartUiAction) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.isClearCartDialogVisible) {
        ClearCartConfirmationDialog(
            onDismiss = { onAction(CartUiAction.ClearCartDismissed) },
            onConfirm = { onAction(CartUiAction.ClearCartConfirmed) }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppColors.Background,
        snackbarHost = {
            Box(
                modifier = Modifier.navigationBarsPadding()
            ) {
                SnackbarHost(hostState = snackbarHostState)
            }
        },
        topBar = {
            AppTopBar(
                modifier = Modifier.statusBarsPadding(),
                title = "Cart",
                subtitle = buildCartSubtitle(uiState = uiState)
            )
        },
        bottomBar = {
            if (uiState.hasItems && !uiState.isLoading) {
                CartBottomBar(
                    totalItems = uiState.totalItems,
                    subtotalPrice = uiState.subtotalPrice,
                    shippingPrice = uiState.shippingPrice,
                    totalPrice = uiState.totalPrice,
                    onCheckoutClick = {
                        onAction(CartUiAction.CheckoutClicked)
                    },
                    onClearAll = {
                        onAction(CartUiAction.ClearCartClicked)
                    }
                )
            }
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> {
                CartLoading(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                )
            }

            uiState.showErrorState -> {
                CartErrorContent(
                    message = uiState.errorMessage ?: "Something went wrong",
                    onRetry = onRetry,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                )
            }

            uiState.showEmptyState -> {
                EmptyCartContent(
                    state = uiState.emptyState,
                    onStartShoppingClick = {
                        onAction(CartUiAction.StartShoppingClicked)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                )
            }

            uiState.showContent -> {
                CartContent(
                    items = uiState.items,
                    totalItems = uiState.totalItems,
                    subtotalPrice = uiState.subtotalPrice,
                    shippingPrice = uiState.shippingPrice,
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppColors.Background)
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun CartLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = AppShapes.ExtraLarge,
            colors = CardDefaults.cardColors(
                containerColor = AppColors.Surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = AppDimens.CardElevation
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space24,
                    vertical = AppDimens.Space24
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = AppColors.Primary
                )

                Text(
                    text = "Loading cart...",
                    modifier = Modifier.padding(top = AppDimens.Space12),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun CartContent(
    items: List<CartItemUi>,
    totalItems: Int,
    subtotalPrice: Int,
    shippingPrice: Int,
    onAction: (CartUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = AppDimens.ScreenHorizontal,
            end = AppDimens.ScreenHorizontal,
            top = AppDimens.Space16,
            bottom = CartBottomSpacing
        ),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space14)
    ) {
        item {
            CartInfoBanner(
                totalItems = totalItems,
                shippingPrice = shippingPrice
            )
        }

        item {
            CartSummarySection(
                subtotalPrice = subtotalPrice,
                shippingPrice = shippingPrice
            )
        }

        items(
            items = items,
            key = { item -> item.productId }
        ) { item ->
            CartItemCard(
                item = item,
                onIncrease = {
                    onAction(CartUiAction.IncreaseQuantity(item.productId))
                },
                onDecrease = {
                    onAction(CartUiAction.DecreaseQuantity(item.productId))
                },
                onRemove = {
                    onAction(CartUiAction.RemoveItem(item))
                }
            )
        }

        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    WindowInsets.safeDrawing
                )
            )
        }
    }
}

@Composable
private fun CartInfoBanner(
    totalItems: Int,
    shippingPrice: Int
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space16,
                    vertical = AppDimens.Space14
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        AppColors.Primary.copy(alpha = 0.10f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Ready for checkout",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = buildCartBannerDescription(
                        totalItems = totalItems,
                        shippingPrice = shippingPrice
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun CartErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    EmptyCartContent(
        state = CartEmptyState(
            title = "Unable to load cart",
            description = message,
            actionLabel = "Try again"
        ),
        onStartShoppingClick = onRetry,
        modifier = modifier
    )
}

@Composable
private fun ClearCartConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Clear cart?",
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.Primary,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "This will remove all items from your cart.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Clear",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel",
                    color = AppColors.Primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}

private fun buildCartSubtitle(uiState: CartUiState): String {
    return if (uiState.hasItems) {
        "${uiState.totalItems} item${if (uiState.totalItems > 1) "s" else ""}"
    } else {
        "Your selected beauty picks"
    }
}

private fun buildCartBannerDescription(
    totalItems: Int,
    shippingPrice: Int
): String {
    val itemText = "$totalItems item${if (totalItems > 1) "s" else ""} selected"

    return if (shippingPrice == 0) {
        "$itemText • Free shipping applied"
    } else {
        "$itemText • Shipping will be added at checkout"
    }
}