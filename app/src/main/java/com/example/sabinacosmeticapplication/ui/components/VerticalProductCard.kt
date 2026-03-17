package com.example.sabinacosmeticapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            defaultElevation = AppDimens.Space4
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
        ) {
            ProductImage(
                imageUrl = product.imageUrl,
                contentDescription = product.title,
                modifier = Modifier.fillMaxWidth(),
                size = AppDimens.ProductImageLarge
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space6),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (product.brand.isNotBlank()) {
                    Text(
                        text = product.brand,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppColors.SecondaryText
                        )
                    )
                }

                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = AppColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 2,
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

                if (product.isBestSeller) {
                    Text(
                        text = "Best Seller",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppColors.DiscountBadgeText,
                            fontWeight = FontWeight.Medium
                        )
                    )
                } else if (product.isFlashSale) {
                    Text(
                        text = "Flash Sale",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppColors.DiscountBadgeText,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                Spacer(modifier = Modifier.height(AppDimens.Space2))
            }
        }
    }
}