package com.example.sabinacosmeticapplication.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductBrandText(
    brand: String,
    modifier: Modifier = Modifier
) {
    if (brand.isBlank()) return

    Text(
        text = brand,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall.copy(
            color = AppColors.SecondaryText,
            fontWeight = FontWeight.Medium
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ProductStatusBadge(
    isBestSeller: Boolean,
    isFlashSale: Boolean,
    modifier: Modifier = Modifier
) {
    val label = when {
        isBestSeller -> "Best Seller"
        isFlashSale -> "Flash Sale"
        else -> null
    }

    if (label == null) {
        Box(
            modifier = modifier.size(AppDimens.Space0)
        )
        return
    }

    ProductBadge(
        text = label,
        modifier = modifier
    )
}