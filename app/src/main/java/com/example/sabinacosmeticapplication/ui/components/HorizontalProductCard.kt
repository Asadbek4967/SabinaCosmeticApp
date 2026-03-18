package com.example.sabinacosmeticapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun HorizontalProductCard(
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.ProductCardOuterPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                ProductImage(
                    imageUrl = product.imageUrl,
                    imageRes = product.imageRes,
                    contentDescription = product.title,
                    size = AppDimens.ProductImageMedium,
                    badgeText = product.category
                )
            }

            Box(modifier = Modifier.width(AppDimens.ProductCardImageSpacing))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = AppDimens.HorizontalProductCardContentMinHeight),
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