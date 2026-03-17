package com.example.sabinacosmeticapplication.domain.usecase

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): List<Product> {
        return repository.getAllProducts()
    }
}