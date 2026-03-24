package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ProductInfoBlock(
    title: String,
    brand: String,
    category: String? = null,
    priceText: String? = null,
    titleColor: Color,
    brandColor: Color,
    accentColor: Color,
    maxTitleLines: Int = 2
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = titleColor,
            maxLines = maxTitleLines,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = brand,
            style = MaterialTheme.typography.bodyMedium,
            color = brandColor
        )

        if (!category.isNullOrBlank()) {
            Text(
                text = category,
                style = MaterialTheme.typography.bodySmall,
                color = accentColor,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (!priceText.isNullOrBlank()) {
            Spacer(modifier = androidx.compose.ui.Modifier.height(2.dp))

            Text(
                text = priceText,
                style = MaterialTheme.typography.titleMedium,
                color = accentColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}