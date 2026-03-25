package com.example.sabinacosmeticapplication.data.remote.source

import com.example.sabinacosmeticapplication.data.mapper.toProduct
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.api.ProductApiService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: ProductApiService
) {

    suspend fun getAllProducts(): List<Product> {
        val response = apiService.getAllProducts()
        return response
            .map { dto -> dto.toProduct() }
            .filter { product -> product.id.isNotBlank() }
    }

    suspend fun getProductById(id: String): Product {
        return apiService.getProductById(id).toProduct()
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        val normalizedSelectedCategory = category.normalizeCategory()

        return getAllProducts().filter { product ->
            product.category.normalizeCategory() == normalizedSelectedCategory
        }
    }
}

private fun String.normalizeCategory(): String {
    return trim()
        .lowercase()
        .replace("&", "and")
        .replace("-", " ")
        .replace("_", " ")
        .replace("\\s+".toRegex(), "")
}