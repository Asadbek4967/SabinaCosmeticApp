package com.example.sabinacosmeticapplication.domain.usecase

import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.data.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(productId: String): Product? {
        return repository.getProductById(productId)
    }
}