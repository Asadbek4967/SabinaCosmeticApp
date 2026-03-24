package com.example.sabinacosmeticapplication.ui.components.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

@Composable
fun QuantityStepper(
    quantity: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = 1
) {
    Row(
        modifier = modifier
            .border(
                width = AppDimens.BorderThin,
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
            enabled = quantity > minValue,
            modifier = Modifier.size(AppDimens.QuantityButtonSize)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease quantity",
                tint = AppColors.IconTint
            )
        }

        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = AppColors.Primary
            ),
            modifier = Modifier.widthIn(min = AppDimens.Space24)
        )

        IconButton(
            onClick = onIncrease,
            modifier = Modifier.size(AppDimens.QuantityButtonSize)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase quantity",
                tint = AppColors.IconTint
            )
        }
    }
}