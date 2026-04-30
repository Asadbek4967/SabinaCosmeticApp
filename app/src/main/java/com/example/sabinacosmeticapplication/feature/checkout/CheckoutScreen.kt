package com.example.sabinacosmeticapplication.feature.checkout

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.core.util.PriceFormatter
import com.example.sabinacosmeticapplication.ui.components.common.AppTopBar
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun CheckoutScreen(
    uiState: CheckoutUiState,
    onPlaceOrderClick: () -> Unit
) {
    val itemCount = uiState.items.sumOf { it.quantity }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppColors.Background,
        topBar = {
            AppTopBar(
                title = "Checkout",
                subtitle = if (uiState.items.isNotEmpty()) {
                    "Review before placing your order"
                } else {
                    "Your order summary"
                },
                onBackClick = null
            )
        },
        bottomBar = {
            CheckoutBottomBar(
                totalPrice = uiState.totalPrice,
                itemCount = itemCount,
                enabled = uiState.items.isNotEmpty(),
                onPlaceOrderClick = onPlaceOrderClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = AppDimens.ScreenHorizontal,
                end = AppDimens.ScreenHorizontal,
                top = AppDimens.Space16,
                bottom = 180.dp
            ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space16)
        ) {
            item {
                CheckoutInfoBanner(
                    itemCount = itemCount,
                    shippingPrice = uiState.shippingPrice
                )
            }

            item {
                CheckoutSectionCard(
                    title = "Order items",
                    subtitle = "$itemCount item${if (itemCount > 1) "s" else ""} in this order"
                )
            }

            items(
                items = uiState.items,
                key = { it.productId }
            ) { item ->
                CheckoutItemCard(
                    title = item.title,
                    brand = item.brand,
                    quantity = item.quantity,
                    totalPrice = item.price * item.quantity
                )
            }

            item {
                PaymentSummaryCard(
                    subtotalPrice = uiState.subtotalPrice,
                    shippingPrice = uiState.shippingPrice,
                    totalPrice = uiState.totalPrice
                )
            }

            item {
                Spacer(
                    modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing)
                )
            }
        }
    }
}

@Composable
private fun CheckoutInfoBanner(
    itemCount: Int,
    shippingPrice: Int
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space16,
                    vertical = AppDimens.Space14
                ),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = AppShapes.Pill,
                color = AppColors.Primary.copy(alpha = 0.10f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Inventory2,
                    contentDescription = null,
                    tint = AppColors.Primary,
                    modifier = Modifier.padding(AppDimens.Space10)
                )
            }

            Column {
                Text(
                    text = "Order review",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = if (shippingPrice == 0) {
                        "$itemCount item${if (itemCount > 1) "s" else ""} • Free shipping"
                    } else {
                        "$itemCount item${if (itemCount > 1) "s" else ""} • Shipping included"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun CheckoutSectionCard(
    title: String,
    subtitle: String
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space6)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText
                )
            )
        }
    }
}

@Composable
private fun CheckoutItemCard(
    title: String,
    brand: String,
    quantity: Int,
    totalPrice: Int
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )
            )

            if (brand.isNotBlank()) {
                Text(
                    text = brand,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.SecondaryText
                    )
                )
            }

            Text(
                text = "Quantity: $quantity",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText
                )
            )

            Text(
                text = PriceFormatter.formatWon(totalPrice),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Price
                )
            )
        }
    }
}

@Composable
private fun PaymentSummaryCard(
    subtotalPrice: Int,
    shippingPrice: Int,
    totalPrice: Int
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space10),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = AppShapes.Pill,
                    color = AppColors.Primary.copy(alpha = 0.10f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Payments,
                        contentDescription = null,
                        tint = AppColors.Primary,
                        modifier = Modifier.padding(AppDimens.Space10)
                    )
                }

                Text(
                    text = "Payment summary",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Primary
                    )
                )
            }

            CheckoutSummaryRow(
                label = "Subtotal",
                value = PriceFormatter.formatWon(subtotalPrice),
                isEmphasized = false
            )

            CheckoutSummaryRow(
                label = "Shipping",
                value = if (shippingPrice == 0) "Free" else PriceFormatter.formatWon(shippingPrice),
                isEmphasized = false,
                icon = Icons.Outlined.LocalShipping
            )

            HorizontalDivider(color = AppColors.Divider)

            CheckoutSummaryRow(
                label = "Total",
                value = PriceFormatter.formatWon(totalPrice),
                isEmphasized = true
            )
        }
    }
}

@Composable
private fun CheckoutSummaryRow(
    label: String,
    value: String,
    isEmphasized: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppColors.SecondaryText
                )
            }

            Text(
                text = label,
                style = if (isEmphasized) {
                    MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.Primary
                    )
                } else {
                    MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.SecondaryText
                    )
                }
            )
        }

        Text(
            text = value,
            style = if (isEmphasized) {
                MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Price
                )
            } else {
                MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Primary
                )
            }
        )
    }
}

@Composable
private fun CheckoutBottomBar(
    totalPrice: Int,
    itemCount: Int,
    enabled: Boolean,
    onPlaceOrderClick: () -> Unit
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
                ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.labelMedium,
                    color = AppColors.SecondaryText
                )

                Text(
                    text = "$itemCount item${if (itemCount > 1) "s" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText
                )

                Text(
                    text = PriceFormatter.formatWon(totalPrice),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = AppColors.Price,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Button(
                onClick = onPlaceOrderClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                shape = AppShapes.Pill,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Primary,
                    contentColor = AppColors.OnPrimary
                ),
                contentPadding = PaddingValues(
                    vertical = AppDimens.Space14
                )
            ) {
                Text(
                    text = "Place Order",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}