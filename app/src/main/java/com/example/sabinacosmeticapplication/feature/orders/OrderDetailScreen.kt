package com.example.sabinacosmeticapplication.feature.orders

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
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.core.util.PriceFormatter
import com.example.sabinacosmeticapplication.data.local.entity.OrderItemEntity
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderDetailScreen(
    uiState: OrderDetailUiState,
    onBackClick: () -> Unit
) {
    when (uiState) {
        OrderDetailUiState.Loading -> {
            OrderDetailLoading()
        }

        is OrderDetailUiState.Error -> {
            OrderDetailError(
                message = uiState.message,
                onBackClick = onBackClick
            )
        }

        is OrderDetailUiState.Success -> {
            val orderWithItems = uiState.order
            val order = orderWithItems.order
            val items = orderWithItems.items
            val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.Background),
                contentPadding = PaddingValues(
                    start = AppDimens.ScreenHorizontal,
                    end = AppDimens.ScreenHorizontal,
                    top = AppDimens.Space16,
                    bottom = AppDimens.Space24
                ),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
            ) {
                item {
                    TextButton(onClick = onBackClick) {
                        Text("Back")
                    }
                }

                item {
                    OrderDetailHeader(
                        orderId = order.id
                    )
                }

                item {
                    OrderDetailInfoBanner(
                        itemCount = order.itemCount,
                        status = order.status
                    )
                }

                item {
                    OrderStatusCard(
                        status = order.status,
                        formattedDate = formatter.format(Date(order.createdAtMillis)),
                        itemCount = order.itemCount
                    )
                }

                item {
                    OrderSectionTitle(
                        title = "Ordered items",
                        subtitle = "${items.size} product${if (items.size > 1) "s" else ""} in this order"
                    )
                }

                items(
                    items = items,
                    key = { it.productId }
                ) { item ->
                    OrderItemCard(item = item)
                }

                item {
                    OrderSummaryCard(
                        subtotalPrice = order.subtotalPrice,
                        shippingPrice = order.shippingPrice,
                        totalPrice = order.totalPrice
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .windowInsetsBottomHeight(WindowInsets.safeDrawing)
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderDetailLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = AppShapes.ExtraLarge,
            colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.CardElevation)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space24,
                    vertical = AppDimens.Space24
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = AppColors.Primary)

                Text(
                    text = "Loading order detail...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    modifier = Modifier.padding(top = AppDimens.Space12)
                )
            }
        }
    }
}

@Composable
private fun OrderDetailError(
    message: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(
                horizontal = AppDimens.ScreenHorizontal,
                vertical = AppDimens.Space20
            )
    ) {
        TextButton(onClick = onBackClick) {
            Text("Back")
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppDimens.Space12),
            shape = AppShapes.ExtraLarge,
            color = AppColors.Surface,
            shadowElevation = AppDimens.CardElevation
        ) {
            Column(
                modifier = Modifier.padding(AppDimens.Space20),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
            ) {
                Text(
                    text = "Unable to load order",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun OrderDetailHeader(
    orderId: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
    ) {
        Text(
            text = "Order Detail",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppColors.Primary
        )

        Text(
            text = "#$orderId",
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.SecondaryText
        )
    }
}

@Composable
private fun OrderDetailInfoBanner(
    itemCount: Int,
    status: String
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimens.Space24 * 2)
                    .background(
                        color = AppColors.Primary.copy(alpha = 0.08f),
                        shape = AppShapes.Pill
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ReceiptLong,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }

            Column {
                Text(
                    text = "Order summary",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = "$itemCount item${if (itemCount > 1) "s" else ""} • $status",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun OrderStatusCard(
    status: String,
    formattedDate: String,
    itemCount: Int
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
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space10)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Status",
                        style = MaterialTheme.typography.labelMedium,
                        color = AppColors.SecondaryText
                    )

                    Text(
                        text = status,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Primary,
                        modifier = Modifier.padding(top = AppDimens.Space4)
                    )
                }

                Surface(
                    shape = AppShapes.Pill,
                    color = AppColors.Background
                ) {
                    Text(
                        text = "$itemCount item${if (itemCount > 1) "s" else ""}",
                        modifier = Modifier.padding(
                            horizontal = AppDimens.Space12,
                            vertical = AppDimens.Space6
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.Primary
                    )
                }
            }

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText
            )
        }
    }
}

@Composable
private fun OrderSectionTitle(
    title: String,
    subtitle: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppColors.Primary
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.SecondaryText
        )
    }
}

@Composable
private fun OrderItemCard(
    item: OrderItemEntity
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
            if (item.category.isNotBlank()) {
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.Primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.Primary,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (item.brand.isNotBlank()) {
                Text(
                    text = item.brand,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Qty: ${item.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText
                )

                Text(
                    text = PriceFormatter.formatWon(item.price * item.quantity),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Price
                )
            }
        }
    }
}

@Composable
private fun OrderSummaryCard(
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
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space10)
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
                        imageVector = Icons.Outlined.LocalShipping,
                        contentDescription = null,
                        tint = AppColors.Primary,
                        modifier = Modifier.padding(AppDimens.Space10)
                    )
                }

                Text(
                    text = "Summary",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )
            }

            SummaryRow(
                label = "Subtotal",
                value = PriceFormatter.formatWon(subtotalPrice)
            )

            SummaryRow(
                label = "Shipping",
                value = if (shippingPrice == 0) "Free" else PriceFormatter.formatWon(shippingPrice)
            )

            SummaryRow(
                label = "Total",
                value = PriceFormatter.formatWon(totalPrice),
                isEmphasis = true
            )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isEmphasis: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isEmphasis) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isEmphasis) FontWeight.Bold else FontWeight.Medium,
            color = if (isEmphasis) AppColors.Primary else AppColors.SecondaryText
        )

        Text(
            text = value,
            style = if (isEmphasis) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isEmphasis) FontWeight.Bold else FontWeight.SemiBold,
            color = if (isEmphasis) AppColors.Price else AppColors.Primary
        )
    }
}