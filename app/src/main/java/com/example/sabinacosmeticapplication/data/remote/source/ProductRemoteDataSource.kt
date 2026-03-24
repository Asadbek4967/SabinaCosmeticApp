package com.example.sabinacosmeticapplication.data.remote.source

import com.example.sabinacosmeticapplication.data.mapper.toProduct
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.api.ProductApiService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: ProductApiService
) {

    suspend fun getAllProducts(): List<Product> {
        return apiService.getAllProducts().map { it.toProduct() }
    }

    suspend fun getProductById(id: String): Product {
        return apiService.getProductById(id).toProduct()
    }

    suspend fun getProductsByCategory(category: String): List<Product> {
        return apiService.getProductsByCategory(category).map { it.toProduct() }
    }
}