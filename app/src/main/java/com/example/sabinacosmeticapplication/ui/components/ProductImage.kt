package com.example.sabinacosmeticapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = AppDimens.ProductImageMedium
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(AppShapes.Medium)
            .background(AppColors.ImagePlaceholder),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isBlank()) {
            Icon(
                imageVector = Icons.Outlined.Image,
                contentDescription = null,
                tint = AppColors.SecondaryText,
                modifier = Modifier.size(size / 2)
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppDimens.Space4),
                contentScale = ContentScale.Crop
            )
        }
    }
}