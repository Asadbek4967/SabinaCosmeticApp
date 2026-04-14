package com.example.sabinacosmeticapplication.ui.components.commerce

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

private val QuantityTextMinWidth = 32.dp

@Composable
fun QuantityStepper(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = 1
) {
    val safeQuantity = quantity.coerceAtLeast(minValue)
    val canDecrease = safeQuantity > minValue

    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                color = AppColors.Border,
                shape = AppShapes.Pill
            )
            .background(
                color = AppColors.Surface,
                shape = AppShapes.Pill
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = onDecrease,
            enabled = canDecrease,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = AppColors.IconTint,
                disabledContentColor = AppColors.SecondaryText
            )
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease quantity"
            )
        }

        Text(
            text = safeQuantity.toString(),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = AppColors.Primary,
            modifier = Modifier.widthIn(min = QuantityTextMinWidth)
        )

        IconButton(
            onClick = onIncrease,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = AppColors.IconTint
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase quantity"
            )
        }
    }
}