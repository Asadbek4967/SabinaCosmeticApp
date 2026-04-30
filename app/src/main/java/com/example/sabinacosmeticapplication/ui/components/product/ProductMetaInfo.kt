package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductMetaInfo(
    brand: String,
    title: String,
    category: String,
    ratingText: String?,
    brandColor: Color,
    titleColor: Color,
    categoryColor: Color,
    ratingColor: Color,
    modifier: Modifier = Modifier,
    titleMaxLines: Int = 2
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
            maxLines = titleMaxLines
        )

        if (!ratingText.isNullOrBlank()) {
            ProductSupportingText(
                text = ratingText,
                color = ratingColor
            )
        }

        if (category.isNotBlank()) {
            ProductSupportingText(
                text = category,
                color = categoryColor
            )
        }
    }
}