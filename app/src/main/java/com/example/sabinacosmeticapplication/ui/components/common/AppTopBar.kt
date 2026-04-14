package com.example.sabinacosmeticapplication.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens

private const val AppTopBarMaxTitleLines = 1
private const val AppTopBarMaxSubtitleLines = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            AppTopBarTitle(
                title = title,
                subtitle = subtitle
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                AppTopBarBackButton(
                    onClick = onBackClick
                )
            }
        },
        actions = {
            actions()
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = AppColors.Surface,
            scrolledContainerColor = AppColors.Surface,
            navigationIconContentColor = AppColors.Primary,
            titleContentColor = AppColors.Primary,
            actionIconContentColor = AppColors.Primary
        )
    )
}

@Composable
private fun AppTopBarBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = AppColors.Primary
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(AppDimens.IconMedium)
        )
    }
}

@Composable
private fun AppTopBarTitle(
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier
) {
    val displayTitle = title.trim().ifBlank { "Untitled" }
    val displaySubtitle = subtitle?.trim()?.takeIf { it.isNotBlank() }

    if (displaySubtitle == null) {
        Text(
            text = displayTitle,
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = AppColors.Primary,
            maxLines = AppTopBarMaxTitleLines,
            overflow = TextOverflow.Ellipsis
        )
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = displayTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = AppColors.Primary,
                maxLines = AppTopBarMaxTitleLines,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = displaySubtitle,
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.SecondaryText,
                maxLines = AppTopBarMaxSubtitleLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}