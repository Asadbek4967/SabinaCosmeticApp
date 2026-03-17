package com.example.sabinacosmeticapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductPriceBlock(
    price: String,
    modifier: Modifier = Modifier,
    oldPrice: String? = null,
    discountLabel: String? = null
) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = price,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = AppColors.Price,
                    fontWeight = FontWeight.Bold
                )
            )

            if (!oldPrice.isNullOrBlank()) {
                Spacer(modifier = Modifier.width(AppDimens.Space8))
                Text(
                    text = oldPrice,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.OldPrice,
                        textDecoration = TextDecoration.LineThrough
                    )
                )
            }
        }

        if (!discountLabel.isNullOrBlank()) {
            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.width(AppDimens.Space4)
            )
            Text(
                text = discountLabel,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = AppColors.DiscountBadgeText,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}