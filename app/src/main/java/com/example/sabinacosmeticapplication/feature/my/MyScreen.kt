package com.example.sabinacosmeticapplication.feature.my

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HeadsetMic
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sabinacosmeticapplication.ui.theme.AppColors
import com.example.sabinacosmeticapplication.ui.theme.AppDimens
import com.example.sabinacosmeticapplication.ui.theme.AppShapes

import androidx.compose.foundation.layout.padding

private val MyTopSafeSpacing = 12.dp

@Composable
fun MyScreen(
    padding: PaddingValues,
    uiState: MyUiState,
    onAction: (MyUiAction) -> Unit
) {
    if (uiState.isLogoutDialogVisible) {
        LogoutConfirmDialog(
            isLoading = uiState.isLoggingOut,
            onDismiss = { onAction(MyUiAction.LogoutDismiss) },
            onConfirm = { onAction(MyUiAction.LogoutConfirm) }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background),
        contentPadding = PaddingValues(
            start = AppDimens.ScreenHorizontal,
            end = AppDimens.ScreenHorizontal,
            top = padding.calculateTopPadding() + MyTopSafeSpacing,
            bottom = padding.calculateBottomPadding() + AppDimens.Space24
        ),
        verticalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        item {
            MyHeader(
                title = uiState.title,
                subtitle = uiState.subtitle
            )
        }

        item {
            MyInfoBanner()
        }

        item {
            SectionTitle(text = "Quick Actions")
        }

        item {
            QuickActionRow(
                first = QuickActionData(
                    title = "Orders",
                    subtitle = "Track placed orders",
                    icon = Icons.Outlined.Inventory2,
                    onClick = { onAction(MyUiAction.OrdersClick) }
                ),
                second = QuickActionData(
                    title = "Wishlist",
                    subtitle = "Saved products",
                    icon = Icons.Outlined.FavoriteBorder,
                    onClick = { onAction(MyUiAction.WishlistClick) }
                )
            )
        }

        item {
            QuickActionRow(
                first = QuickActionData(
                    title = "Address",
                    subtitle = "Delivery places",
                    icon = Icons.Outlined.LocationOn
                ),
                second = QuickActionData(
                    title = "Payments",
                    subtitle = "Billing methods",
                    icon = Icons.Outlined.CreditCard
                )
            )
        }

        item {
            SectionTitle(text = "Account")
        }

        item {
            MenuCardItem(
                title = "Notifications",
                subtitle = "Manage alerts and updates",
                icon = Icons.Outlined.NotificationsNone
            )
        }

        item {
            MenuCardItem(
                title = "Settings",
                subtitle = "Appearance and app preferences",
                icon = Icons.Outlined.Settings
            )
        }

        item {
            MenuCardItem(
                title = "Help Center",
                subtitle = "Support and customer service",
                icon = Icons.Outlined.HeadsetMic
            )
        }

        item {
            OutlinedActionCard(
                title = "Logout",
                subtitle = "Sign out from your account on this device",
                icon = Icons.Outlined.ExitToApp,
                isLoading = uiState.isLoggingOut,
                onClick = { onAction(MyUiAction.LogoutClick) }
            )
        }

        item {
            Spacer(
                modifier = Modifier.windowInsetsBottomHeight(
                    WindowInsets.safeDrawing
                )
            )
        }
    }
}

private data class QuickActionData(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val onClick: (() -> Unit)? = null
)

@Composable
private fun MyHeader(
    title: String,
    subtitle: String
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space18),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(AppColors.Background, CircleShape)
                    .padding(AppDimens.Space16),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }

            Spacer(modifier = Modifier.padding(start = AppDimens.Space14))

            androidx.compose.foundation.layout.Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.SecondaryText,
                    modifier = Modifier.padding(top = AppDimens.Space4)
                )
            }
        }
    }
}

@Composable
private fun MyInfoBanner() {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space16,
                    vertical = AppDimens.Space14
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
        ) {
            Box(
                modifier = Modifier
                    .background(AppColors.Primary.copy(alpha = 0.08f), AppShapes.Pill)
                    .padding(AppDimens.Space10),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }

            androidx.compose.foundation.layout.Column {
                Text(
                    text = "Manage your account",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = "Orders, wishlist, support, and preferences in one place.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = AppColors.Primary
    )
}

@Composable
private fun QuickActionRow(
    first: QuickActionData,
    second: QuickActionData
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space12)
    ) {
        QuickActionCard(
            modifier = Modifier.weight(1f),
            data = first
        )
        QuickActionCard(
            modifier = Modifier.weight(1f),
            data = second
        )
    }
}

@Composable
private fun QuickActionCard(
    modifier: Modifier = Modifier,
    data: QuickActionData
) {
    Card(
        modifier = if (data.onClick != null) {
            modifier.clickable(onClick = data.onClick)
        } else {
            modifier
        },
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(AppDimens.Space16),
            verticalArrangement = Arrangement.spacedBy(AppDimens.Space10)
        ) {
            Box(
                modifier = Modifier
                    .background(AppColors.Background, CircleShape)
                    .padding(AppDimens.Space12),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = data.icon,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }

            Text(
                text = data.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.Primary
            )

            Text(
                text = data.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.SecondaryText
            )
        }
    }
}

@Composable
private fun MenuCardItem(
    title: String,
    subtitle: String,
    icon: ImageVector
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(AppColors.Background, CircleShape)
                    .padding(AppDimens.Space12),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppColors.Primary
                )
            }

            Spacer(modifier = Modifier.padding(start = AppDimens.Space12))

            androidx.compose.foundation.layout.Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun OutlinedActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = AppShapes.ExtraLarge,
        colors = CardDefaults.cardColors(containerColor = AppColors.Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.clickable(enabled = !isLoading, onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.Space16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(AppColors.Background, CircleShape)
                    .padding(AppDimens.Space12),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(2.dp)
                    )
                } else {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = AppColors.Primary
                    )
                }
            }

            Spacer(modifier = Modifier.padding(start = AppDimens.Space12))

            androidx.compose.foundation.layout.Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Primary
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.SecondaryText,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun LogoutConfirmDialog(
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            if (!isLoading) onDismiss()
        },
        title = {
            Text("Logout")
        },
        text = {
            Text("Are you sure you want to sign out from this account?")
        },
        confirmButton = {
            OutlinedButton(
                onClick = onConfirm,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Logout")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isLoading
            ) {
                Text("Cancel")
            }
        }
    )
}