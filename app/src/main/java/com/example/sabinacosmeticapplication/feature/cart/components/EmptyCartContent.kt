package com.example.sabinacosmeticapplication.feature.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.feature.cart.CartEmptyState
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val EmptyStateIconContainerSize = 88.dp
private val EmptyStateIconSize = 36.dp

@Composable
fun EmptyCartContent(
    state: CartEmptyState,
    onStartShoppingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .padding(horizontal = AppDimens.ScreenHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(EmptyStateIconContainerSize)
                .clip(CircleShape)
                .background(AppColors.Primary.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingBag,
                contentDescription = null,
                tint = AppColors.Primary,
                modifier = Modifier.size(EmptyStateIconSize)
            )
        }

        Text(
            text = state.title,
            modifier = Modifier.padding(top = AppDimens.Space20),
            style = MaterialTheme.typography.headlineSmall,
            color = AppColors.Primary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = state.description,
            modifier = Modifier.padding(top = AppDimens.Space10),
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.SecondaryText,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onStartShoppingClick,
            modifier = Modifier.padding(top = AppDimens.Space24),
            shape = AppShapes.Pill,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.Primary,
                contentColor = AppColors.OnPrimary
            )
        ) {
            Text(
                text = state.actionLabel,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}