package com.example.sabinacosmeticapplication.feature.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.core.util.PriceFormatter
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

private val SummaryCornerRadius = 22.dp
private val SummaryIconContainerSize = 38.dp
private val SummaryIconSize = 18.dp
private val SummaryDividerHeight = 28.dp

@Composable
fun CartSummarySection(
    subtotalPrice: Int,
    shippingPrice: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(SummaryCornerRadius),
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SummaryMiniItem(
                icon = Icons.Outlined.ShoppingBag,
                label = "Subtotal",
                value = PriceFormatter.formatWon(subtotalPrice)
            )

            SummaryMiniDivider()

            SummaryMiniItem(
                icon = Icons.Outlined.LocalShipping,
                label = "Shipping",
                value = if (shippingPrice == 0) {
                    "Free"
                } else {
                    PriceFormatter.formatWon(shippingPrice)
                }
            )
        }
    }
}

@Composable
private fun SummaryMiniItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space10)
    ) {
        Box(
            modifier = Modifier
                .size(SummaryIconContainerSize)
                .clip(CircleShape)
                .background(AppColors.Background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.Primary,
                modifier = Modifier.size(SummaryIconSize)
            )
        }

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.SecondaryText
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = AppColors.Primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun SummaryMiniDivider() {
    Box(
        modifier = Modifier
            .height(SummaryDividerHeight)
            .width(1.dp)
            .background(AppColors.Divider)
    )
}