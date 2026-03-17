package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.model.Product

class FakeProductRepository : ProductRepository {

    private val products = listOf(
        Product(
            id = "1",
            title = "COSRX Advanced Snail 96 Mucin Power Essence",
            brand = "COSRX",
            category = "Essence",
            price = "₩19,000",
            priceValue = 19000,
            oldPrice = "₩24,000",
            discountLabel = "20% OFF",
            imageUrl = "https://via.placeholder.com/300.png?text=COSRX",
            description = "Hydrating essence with snail mucin that helps support the skin barrier and improve moisture balance.",
            isFlashSale = true,
            isBestSeller = true
        ),
        Product(
            id = "2",
            title = "Laneige Water Sleeping Mask",
            brand = "Laneige",
            category = "Mask",
            price = "₩28,000",
            priceValue = 28000,
            oldPrice = "₩34,000",
            discountLabel = "Best Seller",
            imageUrl = "https://via.placeholder.com/300.png?text=Laneige",
            description = "Overnight sleeping mask that helps deeply hydrate tired skin and leaves it soft by morning.",
            isFlashSale = false,
            isBestSeller = true
        ),
        Product(
            id = "2",
            title = "Laneige Water Sleeping Mask",
            brand = "Laneige",
            category = "Mask",
            price = "₩28,000",
            priceValue = 28000,
            oldPrice = "₩34,000",
            discountLabel = "Best Seller",
            imageUrl = "https://via.placeholder.com/300.png?text=Laneige",
            description = "Overnight sleeping mask that helps deeply hydrate tired skin and leaves it soft by morning.",
            isFlashSale = false,
            isBestSeller = true
        ),
        Product(
            id = "4",
            title = "Anua Heartleaf 77 Soothing Toner",
            brand = "Anua",
            category = "Toner",
            price = "₩21,000",
            priceValue = 21000,
            oldPrice = "₩26,000",
            discountLabel = "New",
            imageUrl = "https://via.placeholder.com/300.png?text=Anua",
            description = "A calming toner formulated with heartleaf extract to soothe sensitive skin and reduce irritation.",
            isFlashSale = false,
            isBestSeller = false
        )
    )

    override fun getAllProducts(): List<Product> = products

    override fun getProductById(productId: String): Product? {
        return products.find { it.id == productId }
    }

    override fun searchProducts(query: String): List<Product> {
        if (query.isBlank()) return products

        val normalizedQuery = query.trim().lowercase()

        return products.filter { product ->
            product.title.lowercase().contains(normalizedQuery) ||
                    product.brand.lowercase().contains(normalizedQuery) ||
                    product.category.lowercase().contains(normalizedQuery)
        }
    }
}