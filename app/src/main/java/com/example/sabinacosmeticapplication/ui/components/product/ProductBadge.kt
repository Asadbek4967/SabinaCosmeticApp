package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val ProductBadgeMinHeight = 28.dp
private val ProductBadgeMinWidth = 44.dp
private val DefaultProductBadgePadding = PaddingValues(
    horizontal = 10.dp,
    vertical = 6.dp
)

@Composable
fun ProductBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.DiscountBadgeBackground,
    contentColor: Color = AppColors.DiscountBadgeText,
    paddingValues: PaddingValues = DefaultProductBadgePadding
) {
    val displayText = remember(text) {
        text.trim()
    }

    if (displayText.isBlank()) return

    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = AppShapes.Pill
            )
            .defaultMinSize(
                minWidth = ProductBadgeMinWidth,
                minHeight = ProductBadgeMinHeight
            )
            .padding(paddingValues)
    ) {
        Text(
            text = displayText,
            style = MaterialTheme.typography.labelMedium.copy(
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}