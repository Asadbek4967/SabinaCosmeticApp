package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.sabinacosmeticapplication.data.mapper.CategoryMapper
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun VerticalProductCard(
    product: Product,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val displayProductId = remember(product.safeId) {
        product.safeId
    }

    val displayCategory = remember(product.safeCategory) {
        CategoryMapper.toDisplayName(product.safeCategory)
    }

    val displayImageUrl = remember(product.resolvedImageUrl) {
        product.resolvedImageUrl.orEmpty()
    }

    val displayDescription = remember(product.safeDescription) {
        product.safeDescription
            .takeIf { it.isNotBlank() && it != "No description available." }
    }

    val imageBadge = remember(product.primaryBadge, displayCategory) {
        product.primaryBadge ?: displayCategory
    }

    val priceBadge = remember(product.secondaryBadge, product.discountLabel) {
        product.secondaryBadge ?: product.discountLabel
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(displayProductId) },
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.CardElevation
        )
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space12),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space10)
        ) {
            ProductCardImage(
                imageUrl = displayImageUrl,
                imageRes = product.imageRes,
                contentDescription = product.safeTitle,
                badgeText = imageBadge
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = AppDimens.HorizontalProductCardContentMinHeight),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
            ) {
                ProductBrandText(
                    brand = product.safeBrand
                )

                ProductTitleText(
                    title = product.safeTitle,
                    maxLines = 2,
                    minLines = 2
                )

                if (displayDescription != null) {
                    ProductDescriptionText(
                        description = displayDescription,
                        maxLines = 2
                    )
                }

                ProductPriceBlock(
                    price = product.formattedPrice,
                    oldPrice = product.formattedOldPrice,
                    discountLabel = priceBadge
                )

                ProductStatusBadge(
                    isBestSeller = product.isBestSeller,
                    isFlashSale = product.isFlashSale
                )
            }
        }
    }
}