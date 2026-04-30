package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductStatusBadge(
    isBestSeller: Boolean,
    isFlashSale: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isBestSeller && !isFlashSale) return

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space8)
    ) {
        if (isBestSeller) {
            ProductBadge(
                text = "Best Seller",
                backgroundColor = AppColors.BestSellerBadgeBackground,
                contentColor = AppColors.BestSellerBadgeText
            )
        }

        if (isFlashSale) {
            ProductBadge(
                text = "Flash Sale",
                backgroundColor = AppColors.FlashSaleBadgeBackground,
                contentColor = AppColors.FlashSaleBadgeText
            )
        }
    }
}