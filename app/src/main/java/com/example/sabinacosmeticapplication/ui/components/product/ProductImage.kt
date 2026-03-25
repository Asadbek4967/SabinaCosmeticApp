package com.example.sabinacosmeticapplication.ui.components.product

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductImage(
    imageUrl: String,
    @DrawableRes imageRes: Int?,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = AppDimens.ProductImageLarge,
    badgeText: String? = null
) {
    val shape = RoundedCornerShape(20.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(size)
            .clip(shape)
            .background(AppColors.SurfaceVariant)
    ) {
        when {
            imageUrl.isNotBlank() -> {
                RemoteProductImage(
                    imageUrl = imageUrl,
                    contentDescription = contentDescription,
                    size = size
                )
            }

            imageRes != null -> {
                LocalProductImage(
                    imageRes = imageRes,
                    contentDescription = contentDescription
                )
            }

            else -> {
                ProductImagePlaceholder(
                    title = contentDescription,
                    size = size
                )
            }
        }

        if (!badgeText.isNullOrBlank()) {
            ProductImageBadge(
                text = badgeText,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            )
        }
    }
}

@Composable
private fun RemoteProductImage(
    imageUrl: String,
    contentDescription: String,
    size: Dp
) {
    val context = LocalContext.current
    val request = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build()
    }

    SubcomposeAsyncImage(
        model = request,
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxWidth()
            .height(size),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppColors.Primary
                )
            }
        },
        error = {
            ProductImagePlaceholder(
                title = contentDescription,
                size = size
            )
        },
        success = {
            SubcomposeAsyncImageContent()
        }
    )
}

@Composable
private fun LocalProductImage(
    @DrawableRes imageRes: Int,
    contentDescription: String
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun ProductImagePlaceholder(
    title: String,
    size: Dp
) {
    val firstLetter = title
        .trim()
        .firstOrNull()
        ?.uppercase()
        ?: "?"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
            .background(AppColors.SurfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(50))
                    .background(AppColors.Surface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = firstLetter,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )
            }

            Text(
                text = "No image",
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.SecondaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun ProductImageBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(AppColors.Surface.copy(alpha = 0.95f))
            .padding(
                horizontal = 10.dp,
                vertical = 6.dp
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.Primary,
            fontWeight = FontWeight.SemiBold
        )
    }
}