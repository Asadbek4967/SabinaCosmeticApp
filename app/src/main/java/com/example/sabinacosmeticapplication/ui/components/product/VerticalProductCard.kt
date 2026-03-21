package com.example.sabinacosmeticapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun VerticalProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                ProductImage(
                    imageUrl = product.imageUrl,
                    imageRes = product.imageRes,
                    contentDescription = product.title,
                    modifier = Modifier.fillMaxWidth(),
                    size = AppDimens.ProductImageLarge,
                    badgeText = product.category
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimens.ProductCardContentHorizontal,
                        vertical = AppDimens.ProductCardContentVertical
                    )
                    .heightIn(min = AppDimens.VerticalProductCardContentMinHeight),
                verticalArrangement = Arrangement.spacedBy(AppDimens.ProductCardContentSpacing)
            ) {
                ProductBrandText(
                    brand = product.brand
                )

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = AppColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (product.category.isNotBlank()) {
                    ProductBadge(
                        text = product.category
                    )
                }

                ProductPriceBlock(
                    price = product.price,
                    oldPrice = product.oldPrice,
                    discountLabel = product.discountLabel
                )

                ProductStatusBadge(
                    isBestSeller = product.isBestSeller,
                    isFlashSale = product.isFlashSale
                )
            }
        }
    }
}