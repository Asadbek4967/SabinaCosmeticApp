package com.example.sabinacosmeticapplication.feature.productdetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductDetailError(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(AppColors.Background)
            .padding(horizontal = AppDimens.ScreenHorizontal),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = AppShapes.ExtraLarge,
            color = AppColors.Surface,
            shadowElevation = AppDimens.CardElevation
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = AppDimens.Space20,
                    vertical = AppDimens.Space24
                ),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = AppColors.Error,
                    modifier = Modifier.size(AppDimens.IconLarge)
                )

                Text(
                    text = "Unable to load product",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Primary
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = message.ifBlank { "Something went wrong. Please try again." },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.SecondaryText
                    ),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = onRetry,
                    shape = AppShapes.Pill,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary,
                        contentColor = AppColors.OnPrimary
                    )
                ) {
                    Text(
                        text = "Try Again",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}