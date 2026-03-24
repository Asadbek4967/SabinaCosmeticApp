package com.example.sabinacosmeticapplication.domain.usecase.product

import com.example.sabinacosmeticapplication.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<com.example.sabinacosmeticapplication.data.model.Product> {
        return repository.getAllProducts()
    }
}