package com.example.sabinacosmeticapplication.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import com.example.sabinacosmeticapplication.R
import com.example.sabinacosmeticapplication.ui.theme.CartBackground
import com.example.sabinacosmeticapplication.ui.theme.CartBorder
import com.example.sabinacosmeticapplication.ui.theme.CartDanger
import com.example.sabinacosmeticapplication.ui.theme.CartDialogContainer
import com.example.sabinacosmeticapplication.ui.theme.CartDialogText
import com.example.sabinacosmeticapplication.ui.theme.CartMutedText
import com.example.sabinacosmeticapplication.ui.theme.CartPrimary
import com.example.sabinacosmeticapplication.ui.theme.CartSummaryBg
import com.example.sabinacosmeticapplication.ui.theme.CartSurface
import com.example.sabinacosmeticapplication.ui.theme.CartTitle

private val CartCardShape = RoundedCornerShape(20.dp)
private val CartDialogShape = RoundedCornerShape(24.dp)
private val CartImageShape = RoundedCornerShape(16.dp)
private val CartButtonShape = RoundedCornerShape(18.dp)
private val CartSmallButtonShape = RoundedCornerShape(14.dp)
private val CartQuantityShape = RoundedCornerShape(999.dp)

private val CartImageFallbackBackground = Color(0xFFEFF3FF)
private val CartEmptyIconBackground = Color(0xFFEAF3FF)
private val CartSummaryDivider = Color(0xFFE8DDE4)

@Composable
fun CartScreen(
    padding: PaddingValues,
    viewModel: CartViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val cartItemCount by viewModel.cartItemCount.collectAsStateWithLifecycle()
    val lastRemovedItem by viewModel.lastRemovedItem.collectAsStateWithLifecycle()

    var showClearAllDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val removedMessage = stringResource(R.string.cart_item_removed)
    val undoLabel = stringResource(R.string.cart_undo)

    val subtotal = remember(cartItems) {
        cartItems.sumOf { item -> item.product.priceValue * item.quantity }
    }
    val deliveryFee = remember(cartItems, subtotal) {
        if (cartItems.isEmpty() || subtotal >= FREE_DELIVERY_THRESHOLD) 0 else DELIVERY_FEE
    }
    val discount = 0
    val total = remember(subtotal, deliveryFee, discount) {
        subtotal + deliveryFee - discount
    }

    LaunchedEffect(lastRemovedItem) {
        if (lastRemovedItem != null) {
            when (
                snackbarHostState.showSnackbar(
                    message = removedMessage,
                    actionLabel = undoLabel,
                    duration = SnackbarDuration.Short
                )
            ) {
                SnackbarResult.ActionPerformed -> viewModel.undoRemove()
                SnackbarResult.Dismissed -> viewModel.clearLastRemovedItem()
            }
        }
    }

    Scaffold(
        containerColor = CartBackground,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CartBackground)
                .padding(innerPadding)
        ) {
            if (cartItems.isEmpty()) {
                EmptyCartState(padding = padding)
            } else {
                CartContent(
                    padding = padding,
                    itemCount = cartItemCount,
                    items = cartItems,
                    subtotal = subtotal,
                    deliveryFee = deliveryFee,
                    discount = discount,
                    total = total,
                    onClearAllClick = { showClearAllDialog = true },
                    onDecrease = { productId -> viewModel.decreaseQuantity(productId) },
                    onIncrease = { productId -> viewModel.increaseQuantity(productId) },
                    onRemove = { productId -> viewModel.removeFromCart(productId) }
                )
            }

            if (showClearAllDialog) {
                ClearCartConfirmDialog(
                    onDismiss = { showClearAllDialog = false },
                    onConfirm = {
                        showClearAllDialog = false
                        viewModel.clearCart()
                    }
                )
            }
        }
    }
}

@Composable
private fun CartContent(
    padding: PaddingValues,
    itemCount: Int,
    items: List<CartItemUi>,
    subtotal: Int,
    deliveryFee: Int,
    discount: Int,
    total: Int,
    onClearAllClick: () -> Unit,
    onDecrease: (String) -> Unit,
    onIncrease: (String) -> Unit,
    onRemove: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .navigationBarsPadding()
    ) {
        CartHeader(
            itemCount = itemCount,
            onClearAll = onClearAllClick
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = items,
                key = { item -> item.product.id }
            ) { item ->
                CartItemCard(
                    item = item,
                    onDecrease = { onDecrease(item.product.id) },
                    onIncrease = { onIncrease(item.product.id) },
                    onRemove = { onRemove(item.product.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        CartSummarySection(
            subtotal = subtotal,
            deliveryFee = deliveryFee,
            discount = discount,
            total = total
        )
    }
}

@Composable
private fun CartHeader(
    itemCount: Int,
    onClearAll: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(R.string.cart_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = CartTitle
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (itemCount == 1) {
                    stringResource(R.string.cart_item_single, itemCount)
                } else {
                    stringResource(R.string.cart_item_plural, itemCount)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = CartMutedText
            )
        }

        TextButton(onClick = onClearAll) {
            Text(
                text = stringResource(R.string.cart_clear_all),
                color = CartDanger,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ClearCartConfirmDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CartDialogContainer,
        shape = CartDialogShape,
        title = {
            Text(
                text = stringResource(R.string.cart_clear_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CartTitle
            )
        },
        text = {
            Text(
                text = stringResource(R.string.cart_clear_message),
                style = MaterialTheme.typography.bodyMedium,
                color = CartDialogText
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cart_cancel),
                    fontWeight = FontWeight.SemiBold,
                    color = CartMutedText
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = CartSmallButtonShape
            ) {
                Text(
                    text = stringResource(R.string.cart_clear_confirm),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}

@Composable
private fun EmptyCartState(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CartBackground)
            .padding(padding)
            .navigationBarsPadding()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = CartEmptyIconBackground
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🛒",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.cart_empty_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CartTitle
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.cart_empty_description),
                style = MaterialTheme.typography.bodyMedium,
                color = CartMutedText
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
        modifier = Modifier.fillMaxWidth(),
        shape = CartCardShape,
        colors = CardDefaults.cardColors(containerColor = CartSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .background(
                        color = CartImageFallbackBackground,
                        shape = CartImageShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = item.product.imageUrl,
                    contentDescription = item.product.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = { CartImageFallback(item.product.category) },
                    error = { CartImageFallback(item.product.category) }
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = item.product.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = CartTitle
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = item.product.brand,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CartMutedText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.product.price,
                    style = MaterialTheme.typography.titleMedium,
                    color = CartPrimary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuantityControl(
                        quantity = item.quantity,
                        onDecrease = onDecrease,
                        onIncrease = onIncrease,
                        onRemove = onRemove
                    )

                    IconButton(onClick = onRemove) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.cart_remove_from_cart),
                            tint = CartDanger
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuantityControl(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onRemove: () -> Unit
) {
    val isSingleItem = quantity == 1

    Surface(
        shape = CartQuantityShape,
        color = CartSurface,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.border(
            width = 1.dp,
            color = CartBorder,
            shape = CartQuantityShape
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            TextButton(
                onClick = {
                    if (isSingleItem) onRemove() else onDecrease()
                }
            ) {
                if (isSingleItem) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.cart_remove_item),
                        tint = CartDanger,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Text(
                        text = "-",
                        fontWeight = FontWeight.Bold,
                        color = CartTitle
                    )
                }
            }

            Text(
                text = quantity.toString(),
                modifier = Modifier.padding(horizontal = 6.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = CartTitle
            )

            TextButton(onClick = onIncrease) {
                Text(
                    text = "+",
                    fontWeight = FontWeight.Bold,
                    color = CartPrimary
                )
            }
        }
    }
}

@Composable
private fun CartSummarySection(
    subtotal: Int,
    deliveryFee: Int,
    discount: Int,
    total: Int
) {
    Surface(
        color = CartSummaryBg,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            SummaryRow(
                label = stringResource(R.string.cart_subtotal),
                value = formatWon(subtotal)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryRow(
                label = stringResource(R.string.cart_delivery_fee),
                value = if (deliveryFee == 0) {
                    stringResource(R.string.cart_delivery_free)
                } else {
                    formatWon(deliveryFee)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryRow(
                label = stringResource(R.string.cart_discount),
                value = if (discount == 0) formatWon(0) else "-${formatWon(discount)}"
            )

            Spacer(modifier = Modifier.height(14.dp))

            Divider(color = CartSummaryDivider)

            Spacer(modifier = Modifier.height(14.dp))

            SummaryRow(
                label = stringResource(R.string.cart_total),
                value = formatWon(total),
                isTotal = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = CartButtonShape
            ) {
                Text(
                    text = stringResource(R.string.cart_checkout, formatWon(total)),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isTotal: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isTotal) {
                MaterialTheme.typography.titleMedium
            } else {
                MaterialTheme.typography.bodyMedium
            },
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
            color = if (isTotal) CartTitle else CartMutedText
        )

        Text(
            text = value,
            style = if (isTotal) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.bodyMedium
            },
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Medium,
            color = if (isTotal) CartPrimary else CartTitle
        )
    }
}

@Composable
private fun CartImageFallback(category: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CartImageFallbackBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (category) {
                "Serum" -> "💧"
                "Sun Care" -> "☀️"
                "Cream" -> "🫙"
                "Cleanser" -> "🫧"
                "Lip Care" -> "💄"
                "Ampoule" -> "🩵"
                "Toner" -> "✨"
                else -> "🧴"
            },
            style = MaterialTheme.typography.displaySmall
        )
    }
}

private fun formatWon(value: Int): String {
    return "₩${"%,d".format(value)}"
}

private const val FREE_DELIVERY_THRESHOLD = 50_000
private const val DELIVERY_FEE = 3_000