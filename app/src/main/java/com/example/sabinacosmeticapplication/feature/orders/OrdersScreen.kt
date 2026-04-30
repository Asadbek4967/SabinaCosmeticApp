package com.example.sabinacosmeticapplication.feature.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.sabinacosmeticapplication.data.local.relation.OrderWithItems
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersScreen(
    uiState: OrdersUiState,
    onBackClick: () -> Unit,
    onOrderClick: (String) -> Unit
) {
    when {
        uiState.isLoading -> {
            OrdersLoading()
        }

        uiState.orders.isEmpty() -> {
            OrdersEmptyState(
                onBackClick = onBackClick
            )
        }

        else -> {
            OrdersContent(
                orders = uiState.orders,
                onBackClick = onBackClick,
                onOrderClick = onOrderClick
            )
        }
    }
}

@Composable
private fun OrdersLoading() {
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
                    text = "Loading orders...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    modifier = Modifier.padding(top = AppDimens.Space12)
                )
            }
        }
    }
}

@Composable
private fun OrdersEmptyState(
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
                modifier = Modifier.padding(AppDimens.Space24),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
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
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Outlined.Inventory2,
                        contentDescription = null,
                        tint = AppColors.Primary
                    )
                }

                Text(
                    text = "No orders yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Text(
                    text = "Your placed orders will appear here.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun OrdersContent(
    orders: List<OrderWithItems>,
    onBackClick: () -> Unit,
    onOrderClick: (String) -> Unit
) {
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
            OrdersHeader(orderCount = orders.size)
        }

        item {
            OrdersInfoBanner(orderCount = orders.size)
        }

        items(
            items = orders,
            key = { it.order.id }
        ) { orderWithItems ->
            OrderSummaryCard(
                order = orderWithItems,
                formattedDate = formatter.format(Date(orderWithItems.order.createdAtMillis)),
                onClick = { onOrderClick(orderWithItems.order.id) }
            )
        }

        item {
            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
                    .windowInsetsBottomHeight(WindowInsets.safeDrawing)
            )
        }
    }
}

@Composable
private fun OrdersHeader(
    orderCount: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
    ) {
        Text(
            text = "My Orders",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppColors.Primary
        )

        Text(
            text = "$orderCount order${if (orderCount > 1) "s" else ""}",
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.SecondaryText
        )
    }
}

@Composable
private fun OrdersInfoBanner(
    orderCount: Int
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
                androidx.compose.material3.Icon(
                    imageVector = Icons.Outlined.Inventory2,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }

            Column {
                Text(
                    text = "Order history",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = "$orderCount completed and tracked order${if (orderCount > 1) "s" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun OrderSummaryCard(
    order: OrderWithItems,
    formattedDate: String,
    onClick: () -> Unit
) {
    val orderEntity = order.order

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "#${orderEntity.id}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.SecondaryText,
                        modifier = Modifier.padding(top = AppDimens.Space4)
                    )
                }

                Surface(
                    shape = AppShapes.Pill,
                    color = AppColors.Background
                ) {
                    Text(
                        text = orderEntity.status,
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
                text = "${orderEntity.itemCount} item${if (orderEntity.itemCount > 1) "s" else ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = AppColors.SecondaryText
            )

            Text(
                text = PriceFormatter.formatWon(orderEntity.totalPrice),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AppColors.Price
            )
        }
    }
}