package com.example.sabinacosmeticapplication.data.remote.source

import com.example.sabinacosmeticapplication.data.remote.api.ProductApiService
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDetailDto
import com.example.sabinacosmeticapplication.data.remote.dto.ProductDto
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: ProductApiService,
) {

    suspend fun getAllProducts(
        search: String? = null,
        categoryId: String? = null,
        featured: Boolean? = null,
        bestSeller: Boolean? = null,
        newArrival: Boolean? = null,
        active: Boolean = true,
        locale: String = DEFAULT_LOCALE,
        page: Int = 1,
        limit: Int = 50,
    ): List<ProductDto> {
        return apiService.getAllProducts(
            search = search,
            categoryId = categoryId,
            featured = featured,
            bestSeller = bestSeller,
            newArrival = newArrival,
            active = active,
            locale = locale,
            page = page,
            limit = limit,
        ).items.orEmpty()
    }

    suspend fun getProductById(
        id: String,
        locale: String = DEFAULT_LOCALE,
    ): ProductDetailDto {
        return apiService.getProductById(
            id = id,
            locale = locale,
        )
    }

    suspend fun getRelatedProducts(
        id: String,
        locale: String = DEFAULT_LOCALE,
        limit: Int = 20,
    ): List<ProductDto> {
        return apiService.getRelatedProducts(
            id = id,
            locale = locale,
            limit = limit,
        ).items.orEmpty()
    }

    private companion object {
        const val DEFAULT_LOCALE = "en"
    }
}