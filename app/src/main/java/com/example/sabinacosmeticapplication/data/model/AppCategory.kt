package com.example.sabinacosmeticapplication.data.model

data class AppCategory(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val slug: String,
    val parentId: String?,
    val children: List<AppCategory> = emptyList(),
)