package com.example.sabinacosmeticapplication.data.source

import com.example.sabinacosmeticapplication.data.remote.api.CategoryApiService
import com.example.sabinacosmeticapplication.data.remote.dto.CategoryTreeDto
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto
import javax.inject.Inject

class CategoryDataSource @Inject constructor(
    private val categoryApiService: CategoryApiService
) {

    suspend fun getCategoryTree(): List<CategoryTreeDto> {
        return categoryApiService.getCategoryTree(active = true)
    }

    suspend fun getCategoryProducts(categoryId: String): List<ProductDto> {
        return categoryApiService.getCategoryProducts(
            categoryId = categoryId,
            active = true,
            page = 1,
            limit = 50,
        ).items.orEmpty()
    }
}