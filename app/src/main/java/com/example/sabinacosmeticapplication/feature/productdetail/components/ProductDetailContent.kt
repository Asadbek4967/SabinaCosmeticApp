package com.example.sabinacosmeticapplication.feature.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.data.mapper.toUiProduct
import com.example.sabinacosmeticapplication.feature.productdetail.ProductDetailUiAction
import com.example.sabinacosmeticapplication.feature.productdetail.ProductDetailUiState
import com.example.sabinacosmeticapplication.ui.components.common.AppSectionTitle
import com.example.sabinacosmeticapplication.ui.components.product.ProductBadge
import com.example.sabinacosmeticapplication.ui.components.product.ProductPriceBlock
import com.example.sabinacosmeticapplication.ui.components.product.ProductStatusBadge
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

private val ProductDetailBottomSpacer = 132.dp

@Composable
fun ProductDetailContent(
    uiState: ProductDetailUiState,
    onAction: (ProductDetailUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val product = uiState.product ?: return
    val displayProduct = remember(product) { product.toUiProduct() }
    val displayCategory = displayProduct.category.trim()
    val safeBrand = displayProduct.safeBrand
    val safeTitle = displayProduct.safeTitle
    val safeDescription = displayProduct.safeDescription
    val safeProductId = displayProduct.id.trim().ifBlank { "-" }

    Column(
        modifier = modifier
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.ScreenHorizontal,
                    vertical = AppDimens.Space16
                ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space24)
        ) {
            ProductDetailHeader(
                category = displayCategory,
                imageUrl = displayProduct.imageUrl,
                imageRes = displayProduct.imageRes,
                title = safeTitle
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space14)
            ) {
                ProductBadgeRow(
                    category = displayCategory,
                    isBestSeller = displayProduct.isBestSeller,
                    isFlashSale = displayProduct.isFlashSale
                )

                ProductIdentitySection(
                    brand = safeBrand,
                    title = safeTitle
                )

                ProductPriceBlock(
                    price = displayProduct.formattedPrice,
                    oldPrice = displayProduct.formattedOldPrice,
                    discountLabel = displayProduct.discountLabel
                )
            }

            ProductDetailTrustSection()

            ProductDetailSection(
                title = "Description",
                subtitle = "About this product"
            ) {
                ProductDetailDescription(
                    description = safeDescription,
                    isExpanded = uiState.isDescriptionExpanded,
                    onToggle = {
                        onAction(ProductDetailUiAction.ToggleDescriptionClick)
                    }
                )
            }

            ProductDetailSection(
                title = "Product Info",
                subtitle = "Basic product details"
            ) {
                ProductDetailInfoCard(
                    brand = safeBrand.ifBlank { "-" },
                    category = displayCategory.ifBlank { "-" },
                    productId = safeProductId
                )
            }

            Spacer(modifier = Modifier.height(ProductDetailBottomSpacer))
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
        }
    }
}

@Composable
private fun ProductIdentitySection(
    brand: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
    ) {
        if (brand.isNotBlank()) {
            Text(
                text = brand,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = AppColors.Primary,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun ProductDetailSection(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        AppSectionTitle(
            title = title,
            subtitle = subtitle
        )
        content()
    }
}

@Composable
private fun ProductBadgeRow(
    category: String,
    isBestSeller: Boolean,
    isFlashSale: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (category.isNotBlank()) {
            ProductBadge(
                text = category,
                backgroundColor = AppColors.InfoBackground,
                contentColor = AppColors.Primary
            )
        }

        ProductStatusBadge(
            isBestSeller = isBestSeller,
            isFlashSale = isFlashSale
        )
    }
}