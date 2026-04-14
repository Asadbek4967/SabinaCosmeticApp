package com.example.sabinacosmeticapplication.data.remote.source

import com.example.sabinacosmeticapplication.data.remote.api.ProductApiService
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDetailDto
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: ProductApiService
) {

    suspend fun getAllProducts(
        search: String? = null,
        categoryId: String? = null,
        featured: Boolean? = null,
        active: Boolean = true,
        page: Int = 1,
        limit: Int = 50
    ): List<ProductDto> {
        return apiService.getAllProducts(
            search = search,
            categoryId = categoryId,
            featured = featured,
            active = active,
            page = page,
            limit = limit
        ).items.orEmpty()
    }

    suspend fun getProductById(id: String): ProductDetailDto {
        return apiService.getProductById(id = id)
    }
}