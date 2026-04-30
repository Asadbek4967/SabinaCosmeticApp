package com.example.sabinacosmeticapplication.feature.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun OrderSuccessScreen(
    onContinueShopping: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .navigationBarsPadding()
            .padding(
                horizontal = AppDimens.ScreenHorizontal,
                vertical = AppDimens.Space20
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = AppShapes.ExtraLarge,
            colors = CardDefaults.cardColors(
                containerColor = AppColors.Surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = AppDimens.CardElevation
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimens.Space24,
                        vertical = AppDimens.Space28
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SuccessHeroIcon()

                Spacer(modifier = Modifier.size(AppDimens.Space20))

                Text(
                    text = "Order placed successfully",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(AppDimens.Space8))

                Text(
                    text = "Thank you for your purchase. Your order is now being prepared and will move to processing shortly.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.size(AppDimens.Space24))

                OrderSuccessInfoCard()

                Spacer(modifier = Modifier.size(AppDimens.Space24))

                Button(
                    onClick = onContinueShopping,
                    modifier = Modifier.fillMaxWidth(),
                    shape = AppShapes.Pill,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Accent,
                        contentColor = AppColors.OnPrimary
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        vertical = AppDimens.Space14
                    )
                ) {
                    Text(
                        text = "Continue Shopping",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessHeroIcon() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = CircleShape,
            color = AppColors.Accent.copy(alpha = 0.10f)
        ) {
            Box(
                modifier = Modifier.size(AppDimens.Space24 * 4),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = AppColors.Accent,
                    modifier = Modifier.size(AppDimens.Space24 * 2)
                )
            }
        }
    }
}

@Composable
private fun OrderSuccessInfoCard() {
    Card(
        shape = AppShapes.Large,
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space16,
                    vertical = AppDimens.Space16
                ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space14)
        ) {
            OrderSuccessInfoRow(
                icon = Icons.Outlined.Inventory2,
                title = "Order confirmed",
                subtitle = "Your order has been received successfully."
            )

            OrderSuccessInfoRow(
                icon = Icons.Outlined.LocalShipping,
                title = "Preparing for shipment",
                subtitle = "We are getting your items ready for dispatch."
            )
        }
    }
}

@Composable
private fun OrderSuccessInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = AppColors.Primary.copy(alpha = 0.08f)
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimens.Space20 * 2),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = AppColors.Primary,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.SecondaryText
            )
        }
    }
}