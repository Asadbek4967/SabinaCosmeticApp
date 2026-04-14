package com.example.sabinacosmeticapplication.feature.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sabinacosmeticapplication.feature.cart.CartItemUi
import com.example.sabinacosmeticapplication.ui.components.commerce.QuantityStepper
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val ProductImageSize = 92.dp
private val ProductImageCornerRadius = 20.dp
private val RemoveIconSize = 18.dp

@Composable
fun CartItemCard(
    item: CartItemUi,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.CardElevation
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space14),
                verticalAlignment = Alignment.Top
            ) {
                CartProductImage(
                    imageUrl = item.imageUrl,
                    title = item.title
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
                ) {
                    if (item.category.isNotBlank()) {
                        CategoryBadge(text = item.category)
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

                    Text(
                        text = item.priceText,
                        style = MaterialTheme.typography.titleSmall,
                        color = AppColors.Price,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(color = AppColors.Divider)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuantityStepper(
                    quantity = item.quantity,
                    onDecrease = onDecrease,
                    onIncrease = onIncrease
                )

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
                ) {
                    Text(
                        text = "Subtotal",
                        style = MaterialTheme.typography.labelMedium,
                        color = AppColors.SecondaryText
                    )

                    Text(
                        text = item.totalItemPriceText,
                        style = MaterialTheme.typography.titleMedium,
                        color = AppColors.Price,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                RemoveItemAction(
                    onClick = onRemove
                )
            }
        }
    }
}

@Composable
private fun CartProductImage(
    imageUrl: String,
    title: String
) {
    Surface(
        modifier = Modifier.size(ProductImageSize),
        shape = RoundedCornerShape(ProductImageCornerRadius),
        color = AppColors.Background
    ) {
        if (imageUrl.isBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null,
                    tint = AppColors.SecondaryText
                )
            }
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun CategoryBadge(
    text: String
) {
    Box(
        modifier = Modifier
            .clip(AppShapes.Pill)
            .background(AppColors.Primary.copy(alpha = 0.08f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.Primary,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RemoveItemAction(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(AppShapes.Pill)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.error.copy(alpha = 0.08f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space6)
    ) {
        Icon(
            imageVector = Icons.Outlined.DeleteOutline,
            contentDescription = "Remove item",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(RemoveIconSize)
        )

        Text(
            text = "Remove",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.SemiBold
        )
    }
}