package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.remote.source.ProductRemoteDataSource
import com.example.sabinacosmeticapplication.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getAllProducts(): List<Product> {
        return remoteDataSource.getAllProducts()
    }

    override suspend fun getProductById(id: String): Product {
        return remoteDataSource.getProductById(id)
    }

    override suspend fun getProductsByCategory(category: String): List<Product> {
        return remoteDataSource.getProductsByCategory(category)
    }

    override suspend fun searchProducts(query: String): List<Product> {
        if (query.isBlank()) return getAllProducts()

        val normalizedQuery = query.trim()

        return remoteDataSource.getAllProducts().filter { product ->
            product.title.contains(normalizedQuery, ignoreCase = true) ||
                    product.brand.contains(normalizedQuery, ignoreCase = true) ||
                    product.category.contains(normalizedQuery, ignoreCase = true) ||
                    product.description.contains(normalizedQuery, ignoreCase = true)
        }
    }
}