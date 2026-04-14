package com.example.sabinacosmeticapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductsResponseDto(
    val items: List<ProductDto>? = emptyList(),
    val meta: ProductsMetaDto? = null,
)

data class ProductsMetaDto(
    val total: Int? = null,
    val page: Int? = null,
    val limit: Int? = null,
    val totalPages: Int? = null,
)

data class ProductDto(
    val id: String? = null,
    val categoryId: String? = null,
    val title: String? = null,
    val slug: String? = null,
    val brand: String? = null,

    val shortDescription: String? = null,
    val description: String? = null,

    val price: Double? = null,
    val oldPrice: Double? = null,
    val currency: String? = null,
    val stock: Int? = null,

    @SerializedName(
        value = "thumbnailUrl",
        alternate = ["thumbnail"]
    )
    val thumbnailUrl: String? = null,

    @SerializedName(
        value = "imageUrl",
        alternate = [
            "image",
            "featuredImage",
            "featured_image"
        ]
    )
    val imageUrl: String? = null,

    val rating: Double? = null,
    val reviewCount: Int? = null,
    val isFeatured: Boolean? = null,
    val isBestSeller: Boolean? = null,
    val isNewArrival: Boolean? = null,
    val isActive: Boolean? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val category: ProductCategoryDto? = null,
)

data class ProductCategoryDto(
    val id: String? = null,
    val slug: String? = null,
    val nameUz: String? = null,
    val nameRu: String? = null,
    val nameEn: String? = null,
    val nameKo: String? = null,
    val isLeaf: Boolean? = null,
)