package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.sabinacosmeticapplication.ui.theme.AppColors

@Composable
fun ProductBrandText(
    brand: String,
    modifier: Modifier = Modifier
) {
    val displayBrand = remember(brand) {
        brand.trim()
    }

    if (displayBrand.isBlank()) return

    Text(
        text = displayBrand,
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium.copy(
            color = AppColors.SecondaryText,
            fontWeight = FontWeight.Medium
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ProductTitleText(
    title: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 2,
    minLines: Int = 1
) {
    val displayTitle = remember(title) {
        title.trim().ifBlank { "Untitled Product" }
    }

    Text(
        text = displayTitle,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium.copy(
            color = AppColors.Primary,
            fontWeight = FontWeight.Bold
        ),
        maxLines = maxLines,
        minLines = minLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ProductDescriptionText(
    description: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 2
) {
    val displayDescription = remember(description) {
        description.trim()
    }

    if (displayDescription.isBlank()) return

    Text(
        text = displayDescription,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall.copy(
            color = AppColors.SecondaryText
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ProductSupportingText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    val displayText = remember(text) {
        text.trim()
    }

    if (displayText.isBlank()) return

    Text(
        text = displayText,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall.copy(
            color = color
        ),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ProductAccentPriceText(
    priceText: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    val displayPrice = remember(priceText) {
        priceText.trim()
    }

    if (displayPrice.isBlank()) return

    Text(
        text = displayPrice,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium.copy(
            color = color,
            fontWeight = FontWeight.Bold
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}