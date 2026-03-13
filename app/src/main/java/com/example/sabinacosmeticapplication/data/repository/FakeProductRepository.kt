package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.model.Product

class FakeProductRepository : ProductRepository {

    private val products = listOf(
        Product(
            id = "1",
            title = "Hydrating Toner",
            brand = "Laneige",
            category = "Toner",
            price = "₩18,000",
            priceValue = 18000,
            originalPrice = "₩22,000",
            originalPriceValue = 22000,
            discountPercent = 18,
            rating = 4.8,
            reviewCount = 128,
            imageUrl = "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?q=80&w=1200&auto=format&fit=crop",
            description = "Terni namlantirishga yordam beradigan yengil va kundalik toner.",
            isBestSeller = true,
            isFlashSale = true
        ),
        Product(
            id = "2",
            title = "Vitamin C Serum",
            brand = "COSRX",
            category = "Serum",
            price = "₩24,000",
            priceValue = 24000,
            originalPrice = "₩30,000",
            originalPriceValue = 30000,
            discountPercent = 20,
            rating = 4.9,
            reviewCount = 203,
            imageUrl = "https://images.unsplash.com/photo-1556228578-8c89e6adf883?q=80&w=1200&auto=format&fit=crop",
            description = "Teri rangini yorqinlashtirish va tonni tekislash uchun serum.",
            isBestSeller = true,
            isFlashSale = false
        ),
        Product(
            id = "3",
            title = "Cleansing Foam",
            brand = "Innisfree",
            category = "Cleanser",
            price = "₩12,000",
            priceValue = 12000,
            originalPrice = "₩15,000",
            originalPriceValue = 15000,
            discountPercent = 20,
            rating = 4.6,
            reviewCount = 97,
            imageUrl = "https://images.unsplash.com/photo-1612817288484-6f916006741a?q=80&w=1200&auto=format&fit=crop",
            description = "Yuzni yumshoq tozalovchi ko‘pik, kundalik foydalanish uchun.",
            isBestSeller = false,
            isFlashSale = true
        ),
        Product(
            id = "4",
            title = "Sun Cream SPF50",
            brand = "Beauty of Joseon",
            category = "Sun Care",
            price = "₩16,000",
            priceValue = 16000,
            originalPrice = "₩20,000",
            originalPriceValue = 20000,
            discountPercent = 20,
            rating = 4.9,
            reviewCount = 341,
            imageUrl = "https://images.unsplash.com/photo-1596755389378-c31d21fd1273?q=80&w=1200&auto=format&fit=crop",
            description = "Quyoshdan himoya qiluvchi kundalik SPF50 krem.",
            isBestSeller = true,
            isFlashSale = true
        ),
        Product(
            id = "5",
            title = "Retinol Night Cream",
            brand = "Some By Mi",
            category = "Cream",
            price = "₩29,000",
            priceValue = 29000,
            originalPrice = "₩35,000",
            originalPriceValue = 35000,
            discountPercent = 17,
            rating = 4.7,
            reviewCount = 89,
            imageUrl = "https://images.unsplash.com/photo-1571781926291-c477ebfd024b?q=80&w=1200&auto=format&fit=crop",
            description = "Kechki parvarish uchun anti-aging va retinol asosli krem.",
            isBestSeller = false,
            isFlashSale = false
        ),
        Product(
            id = "6",
            title = "Centella Ampoule",
            brand = "Skin1004",
            category = "Ampoule",
            price = "₩21,000",
            priceValue = 21000,
            originalPrice = "₩26,000",
            originalPriceValue = 26000,
            discountPercent = 19,
            rating = 4.8,
            reviewCount = 156,
            imageUrl = "https://images.unsplash.com/photo-1625772452859-1c03d5bf1137?q=80&w=1200&auto=format&fit=crop",
            description = "Sezgir teri uchun tinchlantiruvchi centella ampoule.",
            isBestSeller = true,
            isFlashSale = false
        ),
        Product(
            id = "7",
            title = "Ceramide Cream",
            brand = "Illiyoon",
            category = "Cream",
            price = "₩19,000",
            priceValue = 19000,
            originalPrice = "₩24,000",
            originalPriceValue = 24000,
            discountPercent = 21,
            rating = 4.7,
            reviewCount = 112,
            imageUrl = "https://images.unsplash.com/photo-1601049541289-9b1b7bbbfe19?q=80&w=1200&auto=format&fit=crop",
            description = "Teri barrierini mustahkamlovchi ceramide krem.",
            isBestSeller = false,
            isFlashSale = true
        ),
        Product(
            id = "8",
            title = "Lip Sleeping Mask",
            brand = "Laneige",
            category = "Lip Care",
            price = "₩17,000",
            priceValue = 17000,
            originalPrice = "₩21,000",
            originalPriceValue = 21000,
            discountPercent = 19,
            rating = 4.9,
            reviewCount = 267,
            imageUrl = "https://images.unsplash.com/photo-1586495777744-4413f21062fa?q=80&w=1200&auto=format&fit=crop",
            description = "Lablarni kechasi davomida yumshatib, namlantirib turadi.",
            isBestSeller = true,
            isFlashSale = false
        )
    )

    override fun getAllProducts(): List<Product> = products

    override fun searchProducts(query: String): List<Product> {
        val normalizedQuery = query.trim()

        if (normalizedQuery.isBlank()) return emptyList()

        return products.filter { product ->
            product.title.contains(normalizedQuery, ignoreCase = true) ||
                    product.brand.contains(normalizedQuery, ignoreCase = true) ||
                    product.category.contains(normalizedQuery, ignoreCase = true) ||
                    product.description.contains(normalizedQuery, ignoreCase = true)
        }
    }

    override fun getProductById(productId: String): Product? {
        return products.find { it.id == productId }
    }
}