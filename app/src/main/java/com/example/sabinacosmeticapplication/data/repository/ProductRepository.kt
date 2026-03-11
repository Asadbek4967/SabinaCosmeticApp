package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.model.Product

interface ProductRepository {
    fun getAllProducts(): List<Product>
    fun searchProducts(query: String): List<Product>
    fun getProductById(productId: String): Product?
}