package com.example.sabinacosmeticapplication.domain.usecase

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String): List<Product> {
        return repository.searchProducts(query)
    }
}