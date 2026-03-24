package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductImage(
    imageUrl: String,
    contentDescription: String,
    size: Dp,
    modifier: Modifier = Modifier,
    imageRes: Int? = null,
    badgeText: String? = null
) {
    val context = LocalContext.current

    val model = remember(imageUrl, imageRes) {
        when {
            imageRes != null -> imageRes
            imageUrl.isNotBlank() -> {
                ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build()
            }
            else -> null
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(AppShapes.Large)
            .background(AppColors.ImagePlaceholder),
        contentAlignment = Alignment.Center
    ) {
        if (model == null) {
            ProductImageFallback(
                title = contentDescription,
                badgeText = badgeText,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            SubcomposeAsyncImage(
                model = model,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    ProductImageLoading(
                        modifier = Modifier.fillMaxSize()
                    )
                },
                success = {
                    SubcomposeAsyncImageContent(
                        modifier = Modifier.fillMaxSize()
                    )
                },
                error = {
                    ProductImageFallback(
                        title = contentDescription,
                        badgeText = badgeText,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )
        }
    }
}

@Composable
private fun ProductImageLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(AppColors.ImagePlaceholder),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = AppDimens.Space2,
            modifier = Modifier.size(AppDimens.ProductImageLoadingIndicatorSize)
        )
    }
}

@Composable
private fun ProductImageFallback(
    title: String,
    badgeText: String?,
    modifier: Modifier = Modifier
) {
    val initial = title
        .trim()
        .firstOrNull()
        ?.uppercaseChar()
        ?.toString()
        ?: "P"

    Box(
        modifier = modifier.background(AppColors.ImagePlaceholder),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space12),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimens.ProductImageFallbackIconContainer)
                    .clip(CircleShape)
                    .background(AppColors.Surface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initial,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary,
                    maxLines = 1
                )
            }

            if (!badgeText.isNullOrBlank()) {
                Text(
                    text = badgeText,
                    modifier = Modifier.padding(top = AppDimens.Space10),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null,
                    tint = AppColors.SecondaryText,
                    modifier = Modifier
                        .padding(top = AppDimens.Space10)
                        .size(AppDimens.ProductImageFallbackSmallIcon)
                )
            }
        }
    }
}