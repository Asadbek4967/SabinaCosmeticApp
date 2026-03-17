package com.example.sabinacosmeticapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppShapes
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

@Composable
fun ProductBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color = AppColors.DiscountBadgeBg,
    contentColor: androidx.compose.ui.graphics.Color = AppColors.DiscountBadgeText,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = AppDimens.Space8,
        vertical = AppDimens.Space4
    )
) {
    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = AppShapes.Pill
            )
            .padding(paddingValues)
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}