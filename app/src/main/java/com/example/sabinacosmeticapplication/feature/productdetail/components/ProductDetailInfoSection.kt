package com.example.sabinacosmeticapplication.feature.productdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductDetailTrustSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.Large,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.Space2
        )
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            TrustRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Verified,
                        contentDescription = null,
                        tint = AppColors.Primary
                    )
                },
                title = "Authentic beauty selection",
                subtitle = "Carefully curated essentials for a polished skincare routine"
            )

            HorizontalDivider(color = AppColors.Divider)

            TrustRow(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.LocalShipping,
                        contentDescription = null,
                        tint = AppColors.Primary
                    )
                },
                title = "Fast and secure ordering",
                subtitle = "Smooth cart and checkout experience for daily beauty shopping"
            )
        }
    }
}

@Composable
private fun TrustRow(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier.padding(top = 2.dp)
        ) {
            icon()
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = AppColors.Primary,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = AppColors.SecondaryText
                )
            )
        }
    }
}

@Composable
fun ProductDetailInfoCard(
    brand: String,
    category: String,
    productId: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = AppShapes.Large,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.Space2
        )
    ) {
        Column(
            modifier = Modifier.padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            InfoRow(
                label = "Brand",
                value = brand,
                showDivider = true
            )

            InfoRow(
                label = "Category",
                value = category,
                showDivider = true
            )

            InfoRow(
                label = "Product ID",
                value = productId,
                showDivider = false
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = label,
                modifier = Modifier.weight(0.9f),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.SecondaryText
                )
            )

            Text(
                text = value,
                modifier = Modifier.weight(1.1f),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = AppColors.Primary,
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End
            )
        }

        if (showDivider) {
            Spacer(modifier = Modifier.height(AppDimens.Space12))
            HorizontalDivider(color = AppColors.Divider)
        }
    }
}