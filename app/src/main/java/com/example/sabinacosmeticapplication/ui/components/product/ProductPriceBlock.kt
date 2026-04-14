package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors

private val ProductPriceRowSpacing = 8.dp
private val ProductPriceColumnSpacing = 6.dp

@Composable
fun ProductPriceBlock(
    price: String,
    modifier: Modifier = Modifier,
    oldPrice: String? = null,
    discountLabel: String? = null
) {
    val displayPrice = remember(price) {
        price.trim()
    }

    val displayOldPrice = remember(oldPrice) {
        oldPrice?.trim()?.takeIf { it.isNotBlank() && it != displayPrice }
    }

    val displayDiscountLabel = remember(discountLabel) {
        discountLabel?.trim()?.takeIf { it.isNotBlank() }
    }

    if (displayPrice.isBlank()) return

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(ProductPriceColumnSpacing)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ProductPriceRowSpacing)
        ) {
            Text(
                text = displayPrice,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = AppColors.Price,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (displayOldPrice != null) {
                Text(
                    text = displayOldPrice,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.OldPrice,
                        textDecoration = TextDecoration.LineThrough
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (displayDiscountLabel != null) {
            ProductBadge(
                text = displayDiscountLabel,
                backgroundColor = AppColors.DiscountBadgeBackground,
                contentColor = AppColors.DiscountBadgeText
            )
        }
    }
}