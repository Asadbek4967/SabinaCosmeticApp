package com.example.sabinacosmeticapplication.feature.productdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.sabinacosmeticapplication.ui.components.commerce.QuantityStepper
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun ProductDetailBottomBar(
    unitPriceText: String,
    totalPriceText: String,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = AppColors.Surface,
        shadowElevation = AppDimens.BottomBarShadowElevation
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = AppDimens.ScreenHorizontal,
                    vertical = AppDimens.Space14
                ),
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space8)
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = AppColors.SecondaryText
                )

                Text(
                    text = totalPriceText,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = AppColors.Price
                )

                if (unitPriceText.isNotBlank()) {
                    Text(
                        text = "$unitPriceText each",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.SecondaryText
                    )
                }

                QuantityStepper(
                    quantity = quantity,
                    onDecrease = onDecrease,
                    onIncrease = onIncrease
                )
            }

            AddToCartButton(
                quantity = quantity,
                onClick = onAddToCartClick
            )
        }
    }
}

@Composable
private fun AddToCartButton(
    quantity: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = AppDimens.PrimaryButtonHeight * 2),
        shape = AppShapes.Pill,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.Primary,
            contentColor = AppColors.OnPrimary
        ),
        contentPadding = PaddingValues(
            horizontal = AppDimens.Space20,
            vertical = AppDimens.Space14
        )
    ) {
        Text(
            text = if (quantity > 1) "Add $quantity to Cart" else "Add to Cart",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}