package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sabinacosmeticapplication.data.mapper.CategoryMapper
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun HorizontalProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val displayImageUrl = remember(product.resolvedImageUrl) {
        product.resolvedImageUrl.orEmpty()
    }

    val displayCategory = remember(product.safeCategory) {
        CategoryMapper.toDisplayName(product.safeCategory)
    }

    val displayDescription = remember(product.safeDescription) {
        product.safeDescription
            .takeIf { it.isNotBlank() && it != "No description available." }
    }

    val primaryBadge = remember(
        product.primaryBadge,
        product.isFlashSale,
        product.isBestSeller,
        product.discountLabel
    ) {
        product.primaryBadge
    }

    val secondaryBadge = remember(
        product.secondaryBadge,
        product.isFlashSale,
        product.discountLabel
    ) {
        product.secondaryBadge
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = AppShapes.Large,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.CardElevation
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.ProductCardOuterPadding),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.ProductCardImageSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProductImage(
                imageUrl = displayImageUrl,
                imageRes = product.imageRes,
                contentDescription = product.safeTitle,
                size = AppDimens.ProductImageMedium,
                badgeText = primaryBadge
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = AppDimens.HorizontalProductCardContentMinHeight),
                verticalArrangement = Arrangement.spacedBy(AppDimens.ProductCardContentSpacing)
            ) {
                ProductBrandText(
                    brand = product.safeBrand
                )

                ProductTitleText(
                    title = product.safeTitle,
                    maxLines = 2,
                    minLines = 2
                )

                if (displayCategory.isNotBlank()) {
                    ProductBadge(
                        text = displayCategory,
                        backgroundColor = AppColors.InfoBackground,
                        contentColor = AppColors.Primary
                    )
                }

                if (displayDescription != null) {
                    ProductDescriptionText(
                        description = displayDescription,
                        maxLines = 2
                    )
                }

                ProductPriceBlock(
                    price = product.formattedPrice,
                    oldPrice = product.formattedOldPrice,
                    discountLabel = secondaryBadge ?: product.discountLabel
                )

                ProductStatusBadge(
                    isBestSeller = product.isBestSeller,
                    isFlashSale = product.isFlashSale
                )
            }
        }
    }
}