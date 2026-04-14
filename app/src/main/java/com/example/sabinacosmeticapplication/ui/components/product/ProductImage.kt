package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductImage(
    imageUrl: String,
    imageRes: Int?,
    contentDescription: String,
    size: Dp,
    badgeText: String? = null,
    modifier: Modifier = Modifier
) {
    val displayImageUrl = remember(imageUrl) {
        imageUrl.trim()
    }

    val displayBadgeText = remember(badgeText) {
        badgeText?.trim().orEmpty()
    }

    Box(
        modifier = modifier.size(size)
    ) {
        ProductImageSurface(
            imageUrl = displayImageUrl,
            imageRes = imageRes,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxSize()
                .clip(AppShapes.Large)
                .background(AppColors.SurfaceVariant)
        )

        if (displayBadgeText.isNotBlank()) {
            ProductBadge(
                text = displayBadgeText,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(AppDimens.Space10)
                    .wrapContentSize(),
                backgroundColor = AppColors.InfoBackground,
                contentColor = AppColors.Primary
            )
        }
    }
}

@Composable
fun ProductCardImage(
    imageUrl: String,
    imageRes: Int? = null,
    contentDescription: String = "Product image",
    badgeText: String? = null,
    modifier: Modifier = Modifier
) {
    val displayImageUrl = remember(imageUrl) {
        imageUrl.trim()
    }

    val displayBadgeText = remember(badgeText) {
        badgeText?.trim().orEmpty()
    }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ProductImageSurface(
            imageUrl = displayImageUrl,
            imageRes = imageRes,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimens.ProductImageLarge)
                .clip(AppShapes.Large)
                .background(AppColors.SurfaceVariant)
        )

        if (displayBadgeText.isNotBlank()) {
            ProductBadge(
                text = displayBadgeText,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(AppDimens.Space10),
                backgroundColor = AppColors.InfoBackground,
                contentColor = AppColors.Primary
            )
        }
    }
}

@Composable
private fun ProductImageSurface(
    imageUrl: String,
    imageRes: Int?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    when {
        imageRes != null -> {
            AsyncImage(
                model = imageRes,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }

        imageUrl.isNotBlank() && isLikelyImageUrl(imageUrl) -> {
            RemoteProductImage(
                imageUrl = imageUrl,
                contentDescription = contentDescription,
                modifier = modifier
            )
        }

        else -> {
            ProductImagePlaceholder(
                modifier = modifier
            )
        }
    }
}

@Composable
private fun RemoteProductImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageRequest = remember(imageUrl, context) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    SubcomposeAsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        loading = {
            ProductImageLoadingPlaceholder(
                modifier = Modifier.fillMaxSize()
            )
        },
        error = {
            ProductImagePlaceholder(
                modifier = Modifier.fillMaxSize()
            )
        },
        success = {
            SubcomposeAsyncImageContent()
        }
    )
}

@Composable
private fun ProductImageLoadingPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    AppColors.SurfaceVariant,
                    AppColors.ImagePlaceholder
                )
            )
        ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(AppDimens.Space24),
            color = AppColors.Primary,
            strokeWidth = AppDimens.Space2
        )
    }
}

@Composable
fun ProductImagePlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    AppColors.SurfaceVariant,
                    AppColors.ImagePlaceholder
                )
            )
        ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.Image,
            contentDescription = null,
            tint = AppColors.SecondaryText
        )
    }
}

private fun isLikelyImageUrl(url: String): Boolean {
    val normalized = url.trim().lowercase()

    if (normalized.isBlank()) return false

    return normalized.startsWith("http://") ||
            normalized.startsWith("https://") ||
            normalized.startsWith("file://") ||
            normalized.startsWith("content://")
}