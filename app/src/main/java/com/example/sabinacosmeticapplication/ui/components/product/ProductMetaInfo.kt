package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

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
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = brand,
            style = MaterialTheme.typography.bodySmall,
            color = brandColor
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = titleColor,
            fontWeight = FontWeight.SemiBold,
            maxLines = titleMaxLines,
            overflow = TextOverflow.Ellipsis
        )

        if (!ratingText.isNullOrBlank()) {
            Text(
                text = ratingText,
                style = MaterialTheme.typography.bodySmall,
                color = ratingColor
            )
        }

        Text(
            text = category,
            style = MaterialTheme.typography.bodySmall,
            color = categoryColor
        )

        Spacer(modifier = Modifier.height(2.dp))
    }
}