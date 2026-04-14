package com.example.sabinacosmeticapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDetailDto(
    val id: String? = null,
    val categoryId: String? = null,
    val slug: String? = null,
    val brand: String? = null,
    val title: String? = null,
    val localized: ProductLocalizedDto? = null,
    val shortDescription: String? = null,
    val description: String? = null,
    val skinType: String? = null,
    val price: Double? = null,
    val oldPrice: Double? = null,
    val currency: String? = null,
    val stock: Int? = null,
    val thumbnailUrl: String? = null,
    val imageUrl: String? = null,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val sortOrder: Int? = null,
    val isFeatured: Boolean? = null,
    val isBestSeller: Boolean? = null,
    val isNewArrival: Boolean? = null,
    val isActive: Boolean? = null,
    val category: ProductCategoryDto? = null,
    val images: List<ProductImageDto>? = emptyList(),
    val videos: List<ProductVideoDto>? = emptyList(),
    val createdAt: String? = null,
    val updatedAt: String? = null,
)

data class ProductLocalizedDto(
    val title: String? = null,
    val description: String? = null,
    val benefits: String? = null,
    val howToUse: String? = null,
    val ingredients: String? = null,
    val warning: String? = null,
)

data class ProductImageDto(
    val id: String? = null,
    val imageUrl: String? = null,
    val isThumbnail: Boolean? = null,
    val sortOrder: Int? = null,
)

data class ProductVideoDto(
    val id: String? = null,
    val title: String? = null,
    val videoUrl: String? = null,
    val thumbnailUrl: String? = null,
    val sortOrder: Int? = null,
)