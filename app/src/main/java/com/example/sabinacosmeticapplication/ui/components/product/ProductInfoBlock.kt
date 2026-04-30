package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductInfoBlock(
    title: String,
    brand: String,
    category: String? = null,
    priceText: String? = null,
    titleColor: Color,
    brandColor: Color,
    accentColor: Color,
    maxTitleLines: Int = 2,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space6)
    ) {
        ProductSupportingText(
            text = brand,
            color = brandColor
        )

        ProductTitleText(
            title = title,
            maxLines = maxTitleLines
        )

        if (!category.isNullOrBlank()) {
            ProductBadge(
                text = category,
                backgroundColor = accentColor.copy(alpha = 0.10f),
                contentColor = accentColor
            )
        }

        if (!priceText.isNullOrBlank()) {
            ProductAccentPriceText(
                priceText = priceText,
                color = accentColor
            )
        }
    }
}