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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sabinacosmeticapplication.ui.components.AppTopBar
import com.example.sabinacosmeticapplication.ui.components.ProductBadge
import com.example.sabinacosmeticapplication.ui.components.ProductImage
import com.example.sabinacosmeticapplication.ui.components.ProductPriceBlock
import com.example.sabinacosmeticapplication.ui.components.QuantityStepper
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit = {},
    viewModel: CartViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.lastRemovedItem) {
        val removedItem = uiState.lastRemovedItem ?: return@LaunchedEffect

        val result = snackbarHostState.showSnackbar(
            message = "${removedItem.title} cartdan o‘chirildi",
            actionLabel = "Undo",
            duration = SnackbarDuration.Short
        )

        when (result) {
            SnackbarResult.ActionPerformed -> viewModel.restoreLastRemovedItem()
            SnackbarResult.Dismissed -> viewModel.clearLastRemovedItem()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppColors.Background,
        topBar = {
            AppTopBar(
                title = "My Cart",
                subtitle = "Review your selected products",
                onBackClick = onBackClick
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            if (uiState.items.isNotEmpty()) {
                CartBottomBar(
                    totalPrice = uiState.totalPrice,
                    onCheckoutClick = onCheckoutClick
                )
            }
        }
    ) { innerPadding ->
        if (uiState.items.isEmpty()) {
            EmptyCartContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(
                    start = AppDimens.ScreenHorizontal,
                    end = AppDimens.ScreenHorizontal,
                    top = AppDimens.Space16,
                    bottom = AppDimens.Space32
                ),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
            ) {
                items(
                    items = uiState.items,
                    key = { it.productId }
                ) { item ->
                    CartItemCard(
                        item = item,
                        onDecrease = { viewModel.decreaseQuantity(item.productId) },
                        onIncrease = { viewModel.increaseQuantity(item.productId) },
                        onRemove = { viewModel.removeFromCart(item.productId) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
                }
            }
        }
    }
}

@Composable
private fun EmptyCartContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(AppColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCartCheckout,
                contentDescription = null,
                tint = AppColors.SecondaryText,
                modifier = Modifier.size(AppDimens.Space32 * 2)
            )

            Text(
                text = "Savatchangiz bo‘sh",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.Primary
            )

            Text(
                text = "Mahsulot qo‘shganingizdan keyin shu yerda ko‘rinadi",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText
            )
        }
    }
}

@Composable
private fun CartItemCard(
    item: CartItemUi,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = AppShapes.Large,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space14)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                ProductImage(
                    imageUrl = item.imageUrl,
                    imageRes = item.imageRes,
                    contentDescription = item.title,
                    size = AppDimens.ProductImageMedium,
                    badgeText = item.category
                )

                Spacer(modifier = Modifier.width(AppDimens.Space12))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Space6)
                ) {
                    if (item.brand.isNotBlank()) {
                        Text(
                            text = item.brand,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppColors.SecondaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.Primary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (item.category.isNotBlank()) {
                        ProductBadge(text = item.category)
                    }

                    ProductPriceBlock(
                        price = item.price,
                        oldPrice = item.oldPrice,
                        discountLabel = item.discountLabel
                    )
                }

                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Remove item",
                        tint = AppColors.SecondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppDimens.Space12))
            HorizontalDivider(color = AppColors.Divider)
            Spacer(modifier = Modifier.height(AppDimens.Space12))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuantityStepper(
                    quantity = item.quantity,
                    onDecrease = onDecrease,
                    onIncrease = onIncrease
                )

                Text(
                    text = "Total: ${formatWon(item.priceValue * item.quantity)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Price
                )
            }
        }
    }
}

@Composable
private fun CartBottomBar(
    totalPrice: Int,
    onCheckoutClick: () -> Unit
) {
    Surface(
        color = AppColors.Surface,
        shadowElevation = AppDimens.Space8
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = AppDimens.ScreenHorizontal,
                    vertical = AppDimens.Space14
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = formatWon(totalPrice),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Price
                )
            }

            Spacer(modifier = Modifier.height(AppDimens.Space12))

            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimens.Space32 + AppDimens.Space20),
                shape = AppShapes.Pill,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = AppColors.OnPrimary
                )
            ) {
                Text(
                    text = "Checkout",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

private fun formatWon(value: Int): String {
    return "₩${value.toString().reversed().chunked(3).joinToString(",").reversed()}"
}