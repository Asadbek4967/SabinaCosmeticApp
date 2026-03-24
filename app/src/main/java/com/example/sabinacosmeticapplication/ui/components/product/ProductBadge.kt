package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.DiscountBadgeBg,
    contentColor: Color = AppColors.DiscountBadgeText,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = AppDimens.BadgeHorizontalPadding,
        vertical = AppDimens.BadgeVerticalPadding
    )
) {
    if (text.isBlank()) return

    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = AppShapes.Pill
            )
            .defaultMinSize(minHeight = AppDimens.BadgeMinHeight)
            .padding(paddingValues)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}