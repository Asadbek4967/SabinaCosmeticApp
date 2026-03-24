package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductPriceBlock(
    price: String,
    modifier: Modifier = Modifier,
    oldPrice: String? = null,
    discountLabel: String? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppDimens.PriceBlockVerticalSpacing)
    ) {
        Row {
            Text(
                text = price,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = AppColors.Price,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (!oldPrice.isNullOrBlank()) {
                Spacer(modifier = Modifier.width(AppDimens.PriceBlockPriceSpacing))

                Text(
                    text = oldPrice,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.OldPrice,
                        textDecoration = TextDecoration.LineThrough
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (!discountLabel.isNullOrBlank()) {
            ProductBadge(
                text = discountLabel,
                backgroundColor = AppColors.DiscountBadgeBg,
                contentColor = AppColors.DiscountBadgeText
            )
        }
    }
}