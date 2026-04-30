package com.example.sabinacosmeticapplication.feature.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.example.sabinacosmeticapplication.ui.components.product.ProductImage
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductDetailHeader(
    category: String,
    imageUrl: String,
    imageRes: Int?,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.CardElevation
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space20),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space18)
        ) {
            if (category.isNotBlank()) {
                HeaderCategoryChip(
                    text = category
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(AppShapes.Large)
                    .background(AppColors.SurfaceVariant)
                    .padding(
                        horizontal = AppDimens.Space16,
                        vertical = AppDimens.Space20
                    ),
                contentAlignment = Alignment.Center
            ) {
                ProductImage(
                    imageUrl = imageUrl,
                    imageRes = imageRes,
                    contentDescription = title,
                    size = AppDimens.ProductImageXL,
                    badgeText = null
                )
            }
        }
    }
}

@Composable
private fun HeaderCategoryChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(AppShapes.Pill)
            .background(AppColors.InfoBackground)
            .padding(
                horizontal = AppDimens.Space12,
                vertical = AppDimens.Space8
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                color = AppColors.Primary,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}