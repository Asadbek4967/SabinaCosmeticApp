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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage

private val CartBackground = Color(0xFFF6F7FB)
private val CartPrimary = Color(0xFF4D6BFE)
private val CartMutedText = Color(0xFF7E8794)
private val CartDanger = Color(0xFFE53935)
private val CartSurface = Color.White
private val CartSummaryBg = Color(0xFFFFF7FA)
private val CartBorder = Color(0xFFE3E8F2)
private val CartTitle = Color(0xFF1B1F26)
private val CartDialogContainer = Color.White
private val CartDialogText = Color(0xFF5F6B7A)

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

    val subtotal = cartItems.sumOf { it.product.priceValue * it.quantity }
    val deliveryFee = if (cartItems.isEmpty() || subtotal >= 50_000) 0 else 3_000
    val discount = 0
    val total = subtotal + deliveryFee - discount

    LaunchedEffect(lastRemovedItem) {
        if (lastRemovedItem != null) {
            val result = snackbarHostState.showSnackbar(
                message = "Item removed",
                actionLabel = "UNDO",
                duration = SnackbarDuration.Short
            )

            when (result) {
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .navigationBarsPadding()
                ) {
                    CartHeader(
                        itemCount = cartItemCount,
                        onClearAll = {
                            showClearAllDialog = true
                        }
                    )

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = cartItems,
                            key = { item -> item.product.id }
                        ) { item ->
                            CartItemCard(
                                item = item,
                                onDecrease = { viewModel.decreaseQuantity(item.product.id) },
                                onIncrease = { viewModel.increaseQuantity(item.product.id) },
                                onRemove = { viewModel.removeFromCart(item.product.id) }
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

            if (showClearAllDialog) {
                ClearCartConfirmDialog(
                    onDismiss = {
                        showClearAllDialog = false
                    },
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
                text = "My cart",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = CartTitle
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$itemCount item${if (itemCount > 1) "s" else ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = CartMutedText
            )
        }

        TextButton(onClick = onClearAll) {
            Text(
                text = "Clear all",
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
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = "Clear cart",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CartTitle
            )
        },
        text = {
            Text(
                text = "Are you sure you want to remove all items from your cart? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium,
                color = CartDialogText
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    fontWeight = FontWeight.SemiBold,
                    color = CartMutedText
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Clear",
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
                color = Color(0xFFEAF3FF)
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
                text = "Your cart is empty",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = CartTitle
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Add products from Home or Search to see them here.",
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
        shape = RoundedCornerShape(20.dp),
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
                        color = Color(0xFFEFF3FF),
                        shape = RoundedCornerShape(16.dp)
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
                            contentDescription = "Remove from cart",
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
        shape = RoundedCornerShape(999.dp),
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.border(
            width = 1.dp,
            color = CartBorder,
            shape = RoundedCornerShape(999.dp)
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
                        contentDescription = "Remove item",
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
                label = "Subtotal",
                value = formatWon(subtotal)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryRow(
                label = "Delivery fee",
                value = if (deliveryFee == 0) "Free" else formatWon(deliveryFee)
            )

            Spacer(modifier = Modifier.height(8.dp))

            SummaryRow(
                label = "Discount",
                value = if (discount == 0) formatWon(0) else "-${formatWon(discount)}"
            )

            Spacer(modifier = Modifier.height(14.dp))

            Divider(color = Color(0xFFE8DDE4))

            Spacer(modifier = Modifier.height(14.dp))

            SummaryRow(
                label = "Total",
                value = formatWon(total),
                isTotal = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(
                    text = "Checkout • ${formatWon(total)}",
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
            .background(Color(0xFFEFF3FF)),
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