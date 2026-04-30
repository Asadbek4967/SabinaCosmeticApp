package com.example.sabinacosmeticapplication.feature.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.core.util.PriceFormatter
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val BottomBarShadowElevation = 14.dp
private val CheckoutButtonHeight = 56.dp
private val CheckoutButtonMinWidth = 148.dp

@Composable
fun CartBottomBar(
    totalItems: Int,
    subtotalPrice: Int,
    shippingPrice: Int,
    totalPrice: Int,
    onCheckoutClick: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = AppColors.Surface,
        shadowElevation = BottomBarShadowElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = AppDimens.ScreenHorizontal,
                    vertical = AppDimens.Space14
                ),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space14)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppDimens.Space16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.Space4)
                ) {
                    Text(
                        text = "Order total",
                        style = MaterialTheme.typography.labelMedium,
                        color = AppColors.SecondaryText
                    )

                    Text(
                        text = buildItemCountText(totalItems),
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.SecondaryText
                    )

                    Text(
                        text = buildBottomBarMetaText(
                            subtotalPrice = subtotalPrice,
                            shippingPrice = shippingPrice
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.SecondaryText
                    )

                    Text(
                        text = PriceFormatter.formatWon(totalPrice),
                        style = MaterialTheme.typography.headlineSmall,
                        color = AppColors.Price,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onCheckoutClick,
                    modifier = Modifier
                        .height(CheckoutButtonHeight)
                        .widthIn(min = CheckoutButtonMinWidth),
                    shape = AppShapes.Pill,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary,
                        contentColor = AppColors.OnPrimary
                    )
                ) {
                    Text(
                        text = "Checkout",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(color = AppColors.Divider)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Clear all items",
                    modifier = Modifier
                        .clip(AppShapes.Pill)
                        .clickable(onClick = onClearAll)
                        .background(AppColors.Background)
                        .padding(
                            horizontal = AppDimens.Space14,
                            vertical = AppDimens.Space8
                        ),
                    style = MaterialTheme.typography.labelLarge,
                    color = AppColors.Primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

private fun buildBottomBarMetaText(
    subtotalPrice: Int,
    shippingPrice: Int
): String {
    val subtotalText = PriceFormatter.formatWon(subtotalPrice)

    return if (shippingPrice == 0) {
        "Subtotal $subtotalText • Free shipping"
    } else {
        "Subtotal $subtotalText • Shipping ${PriceFormatter.formatWon(shippingPrice)}"
    }
}

private fun buildItemCountText(totalItems: Int): String {
    return "$totalItems item${if (totalItems > 1) "s" else ""}"
}