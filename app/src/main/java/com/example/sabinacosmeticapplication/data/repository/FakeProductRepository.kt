package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.data.model.Product

class FakeProductRepository : ProductRepository {

    private val products: List<Product> = buildProducts()

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

    override fun getProductsByCategory(category: String): List<Product> {
        return products.filter { product ->
            product.category.equals(category, ignoreCase = true)
        }
    }

    private fun buildProducts(): List<Product> {
        val result = mutableListOf<Product>()
        var idCounter = 1

        fun nextId(): String = (idCounter++).toString()

        fun addProduct(
            title: String,
            brand: String,
            category: String,
            priceValue: Int,
            oldPriceValue: Int?,
            discountLabel: String,
            description: String,
            imageUrl: String = categoryImage(category),
            isFlashSale: Boolean = false,
            isBestSeller: Boolean = false
        ) {
            result.add(
                Product(
                    id = nextId(),
                    title = title,
                    brand = brand,
                    category = category,
                    price = formatPrice(priceValue),
                    priceValue = priceValue,
                    oldPrice = oldPriceValue?.let { formatPrice(it) },
                    discountLabel = discountLabel,
                    imageUrl = imageUrl,
                    imageRes = null,
                    description = description,
                    isFlashSale = isFlashSale,
                    isBestSeller = isBestSeller
                )
            )
        }

        // Skincare (10)
        addProduct(
            title = "COSRX Snail Mucin Daily Repair Essence",
            brand = "COSRX",
            category = "Skincare",
            priceValue = 19000,
            oldPriceValue = 24000,
            discountLabel = "20% OFF",
            description = "Hydrating daily essence to support soft, healthy-looking skin.",
            isFlashSale = true,
            isBestSeller = true
        )
        addProduct(
            title = "Round Lab Birch Moisture Skin",
            brand = "Round Lab",
            category = "Skincare",
            priceValue = 22000,
            oldPriceValue = 27000,
            discountLabel = "New",
            description = "Fresh moisture skin toner for dry and tired skin."
        )
        addProduct(
            title = "Etude SoonJung Barrier Relief Skin",
            brand = "Etude",
            category = "Skincare",
            priceValue = 18000,
            oldPriceValue = 23000,
            discountLabel = "Soft Deal",
            description = "Gentle skincare for sensitive skin barrier support."
        )
        addProduct(
            title = "Dr.Jart Ceramidin Skin Prep",
            brand = "Dr.Jart",
            category = "Skincare",
            priceValue = 26000,
            oldPriceValue = 32000,
            discountLabel = "Best Pick",
            description = "Ceramide prep care for stronger daily moisture barrier."
        )
        addProduct(
            title = "Innisfree Green Tea Hydration Skin",
            brand = "Innisfree",
            category = "Skincare",
            priceValue = 17000,
            oldPriceValue = 21000,
            discountLabel = "Save",
            description = "Refreshing green tea skincare with lightweight hydration.",
            isBestSeller = true
        )
        addProduct(
            title = "Abib Rice Probiotics Skin Booster",
            brand = "Abib",
            category = "Skincare",
            priceValue = 24000,
            oldPriceValue = 29000,
            discountLabel = "Trending",
            description = "Rice probiotic skincare booster for smoother daily care."
        )
        addProduct(
            title = "Huxley Sahara Moist Comfort Skin",
            brand = "Huxley",
            category = "Skincare",
            priceValue = 25000,
            oldPriceValue = 31000,
            discountLabel = "Hot",
            description = "Elegant moisture comfort skin for balanced hydration."
        )
        addProduct(
            title = "Pyunkang Yul Daily Balance Skin",
            brand = "Pyunkang Yul",
            category = "Skincare",
            priceValue = 19500,
            oldPriceValue = 23500,
            discountLabel = "Deal",
            description = "Minimal skincare focused on balance and calmness.",
            isFlashSale = true
        )
        addProduct(
            title = "Torriden Dive-In Water Skin",
            brand = "Torriden",
            category = "Skincare",
            priceValue = 23000,
            oldPriceValue = 28000,
            discountLabel = "Fresh",
            description = "Watery skincare formula with light fresh finish."
        )
        addProduct(
            title = "Isntree Hyaluronic Fresh Skin",
            brand = "Isntree",
            category = "Skincare",
            priceValue = 21000,
            oldPriceValue = 25000,
            discountLabel = "Popular",
            description = "Hydration-rich skincare for daily moisture support."
        )

        // Serum (10)
        addProduct(
            title = "Beauty of Joseon Glow Deep Serum",
            brand = "Beauty of Joseon",
            category = "Serum",
            priceValue = 21000,
            oldPriceValue = 26000,
            discountLabel = "Glow",
            description = "Brightening serum for dull and uneven skin tone.",
            isBestSeller = true
        )
        addProduct(
            title = "Anua Peach Niacin Glow Serum",
            brand = "Anua",
            category = "Serum",
            priceValue = 23000,
            oldPriceValue = 28000,
            discountLabel = "Hot",
            description = "Niacinamide serum for radiant and balanced skin."
        )
        addProduct(
            title = "SKIN1004 Centella Calm Serum",
            brand = "SKIN1004",
            category = "Serum",
            priceValue = 25000,
            oldPriceValue = 30000,
            discountLabel = "Calming",
            description = "Centella-based serum for soothing irritated skin.",
            isFlashSale = true
        )
        addProduct(
            title = "Some By Mi Retinol Intense Serum",
            brand = "Some By Mi",
            category = "Serum",
            priceValue = 27000,
            oldPriceValue = 33000,
            discountLabel = "Popular",
            description = "Retinol support for texture and fine line care."
        )
        addProduct(
            title = "Purito Unscented Barrier Serum",
            brand = "Purito",
            category = "Serum",
            priceValue = 22000,
            oldPriceValue = 26000,
            discountLabel = "Clean",
            description = "Gentle serum for barrier recovery and comfort."
        )
        addProduct(
            title = "Axis-Y Dark Spot Correcting Serum",
            brand = "Axis-Y",
            category = "Serum",
            priceValue = 24000,
            oldPriceValue = 29000,
            discountLabel = "Best Seller",
            description = "Helps brighten post-acne marks and uneven tone.",
            isBestSeller = true
        )
        addProduct(
            title = "COSRX Propolis Glow Serum",
            brand = "COSRX",
            category = "Serum",
            priceValue = 21500,
            oldPriceValue = 25500,
            discountLabel = "Glow",
            description = "Nourishing serum for healthy glow and comfort."
        )
        addProduct(
            title = "Dr.Althea Vitamin C Boost Serum",
            brand = "Dr.Althea",
            category = "Serum",
            priceValue = 26500,
            oldPriceValue = 31500,
            discountLabel = "Fresh",
            description = "Vitamin C serum for brighter looking skin."
        )
        addProduct(
            title = "Tiam Vita B3 Source Serum",
            brand = "Tiam",
            category = "Serum",
            priceValue = 20500,
            oldPriceValue = 24500,
            discountLabel = "Save",
            description = "Niacinamide serum for tone improvement.",
            isFlashSale = true
        )
        addProduct(
            title = "Round Lab Soybean Nourish Serum",
            brand = "Round Lab",
            category = "Serum",
            priceValue = 25000,
            oldPriceValue = 30000,
            discountLabel = "New",
            description = "Comforting serum for dry and tired skin."
        )

        // Sun Care (10)
        addProduct(
            title = "Beauty of Joseon Relief Sun SPF50+",
            brand = "Beauty of Joseon",
            category = "Sun Care",
            priceValue = 15000,
            oldPriceValue = 19000,
            discountLabel = "Hot",
            description = "Lightweight daily sunscreen with soft finish.",
            isFlashSale = true,
            isBestSeller = true
        )
        addProduct(
            title = "Round Lab Birch Sun Cream",
            brand = "Round Lab",
            category = "Sun Care",
            priceValue = 18000,
            oldPriceValue = 22000,
            discountLabel = "UV Care",
            description = "Hydrating sunscreen for everyday outdoor protection."
        )
        addProduct(
            title = "Isntree Watery Sun Gel",
            brand = "Isntree",
            category = "Sun Care",
            priceValue = 17000,
            oldPriceValue = 21000,
            discountLabel = "Fresh",
            description = "Moist gel-type sunscreen with comfortable wear."
        )
        addProduct(
            title = "SKIN1004 Hyalu-Cica Sun Serum",
            brand = "SKIN1004",
            category = "Sun Care",
            priceValue = 19000,
            oldPriceValue = 23000,
            discountLabel = "Popular",
            description = "Serum-like sunscreen with hydrating texture."
        )
        addProduct(
            title = "Tocobo Bio Watery Sun Cream",
            brand = "Tocobo",
            category = "Sun Care",
            priceValue = 18500,
            oldPriceValue = 22500,
            discountLabel = "New",
            description = "Light sunscreen with fresh non-sticky finish."
        )
        addProduct(
            title = "Etude Director's Moist Sun",
            brand = "Etude",
            category = "Sun Care",
            priceValue = 16000,
            oldPriceValue = 20000,
            discountLabel = "Save",
            description = "Smooth sunscreen for daily city wear."
        )
        addProduct(
            title = "Dr.G Green Mild Up Sun",
            brand = "Dr.G",
            category = "Sun Care",
            priceValue = 21000,
            oldPriceValue = 26000,
            discountLabel = "Calm",
            description = "Gentle formula for sensitive skin users.",
            isBestSeller = true
        )
        addProduct(
            title = "AHC Natural Perfection Sun Stick",
            brand = "AHC",
            category = "Sun Care",
            priceValue = 23000,
            oldPriceValue = 28000,
            discountLabel = "Stick",
            description = "Easy reapplication sun stick for busy days."
        )
        addProduct(
            title = "Innisfree Daily UV Defense",
            brand = "Innisfree",
            category = "Sun Care",
            priceValue = 17500,
            oldPriceValue = 21500,
            discountLabel = "Daily",
            description = "Comfortable sunscreen for normal to dry skin."
        )
        addProduct(
            title = "Missha All Around Safe Block",
            brand = "Missha",
            category = "Sun Care",
            priceValue = 15500,
            oldPriceValue = 19500,
            discountLabel = "Deal",
            description = "Classic sunscreen for full daily protection.",
            isFlashSale = true
        )

        // Cream (10)
        addProduct(
            title = "Laneige Water Bank Blue Cream",
            brand = "Laneige",
            category = "Cream",
            priceValue = 28000,
            oldPriceValue = 34000,
            discountLabel = "Hydration",
            description = "Barrier cream with long-lasting moisture care.",
            isBestSeller = true
        )
        addProduct(
            title = "Dr.Jart Ceramidin Cream",
            brand = "Dr.Jart",
            category = "Cream",
            priceValue = 32000,
            oldPriceValue = 39000,
            discountLabel = "Best Seller",
            description = "Rich ceramide cream for dry weakened skin."
        )
        addProduct(
            title = "Etude SoonJung Barrier Cream",
            brand = "Etude",
            category = "Cream",
            priceValue = 21000,
            oldPriceValue = 26000,
            discountLabel = "Calming",
            description = "Gentle cream for sensitive and reactive skin."
        )
        addProduct(
            title = "Aestura Atobarrier 365 Cream",
            brand = "Aestura",
            category = "Cream",
            priceValue = 34000,
            oldPriceValue = 41000,
            discountLabel = "Barrier",
            description = "Deep barrier care for dryness and rough texture."
        )
        addProduct(
            title = "Illiyoon Ceramide Ato Cream",
            brand = "Illiyoon",
            category = "Cream",
            priceValue = 25000,
            oldPriceValue = 30000,
            discountLabel = "Popular",
            description = "Moisturizing cream with strong comfort effect."
        )
        addProduct(
            title = "Innisfree Cherry Blossom Jelly Cream",
            brand = "Innisfree",
            category = "Cream",
            priceValue = 22000,
            oldPriceValue = 27000,
            discountLabel = "Glow",
            description = "Bouncy brightening cream for fresh skin finish.",
            isFlashSale = true
        )
        addProduct(
            title = "Hanyul Yuja Sleeping Cream",
            brand = "Hanyul",
            category = "Cream",
            priceValue = 30000,
            oldPriceValue = 36000,
            discountLabel = "Night",
            description = "Night cream for nourishment and brightness."
        )
        addProduct(
            title = "Round Lab Dokdo Cream",
            brand = "Round Lab",
            category = "Cream",
            priceValue = 24000,
            oldPriceValue = 29000,
            discountLabel = "Fresh",
            description = "Comfortable cream with smooth moisturizing feel."
        )
        addProduct(
            title = "Abib Jericho Rose Nutrition Cream",
            brand = "Abib",
            category = "Cream",
            priceValue = 29000,
            oldPriceValue = 35000,
            discountLabel = "Nourish",
            description = "Rich cream for dry and tired skin."
        )
        addProduct(
            title = "COSRX Comfort Ceramide Cream",
            brand = "COSRX",
            category = "Cream",
            priceValue = 23000,
            oldPriceValue = 28000,
            discountLabel = "Deal",
            description = "Daily moisture cream with soothing care.",
            isBestSeller = true
        )

        // Toner (10)
        addProduct(
            title = "Anua Heartleaf 77 Soothing Toner",
            brand = "Anua",
            category = "Toner",
            priceValue = 21000,
            oldPriceValue = 26000,
            discountLabel = "New",
            description = "Heartleaf toner for calming and refreshing care.",
            isBestSeller = true
        )
        addProduct(
            title = "Round Lab Dokdo Toner",
            brand = "Round Lab",
            category = "Toner",
            priceValue = 20000,
            oldPriceValue = 24500,
            discountLabel = "Popular",
            description = "Hydrating toner for smooth skin texture."
        )
        addProduct(
            title = "Pyunkang Yul Essence Toner",
            brand = "Pyunkang Yul",
            category = "Toner",
            priceValue = 19000,
            oldPriceValue = 23000,
            discountLabel = "Soft",
            description = "Minimalist toner with comfortable hydration."
        )
        addProduct(
            title = "Isntree Green Tea Fresh Toner",
            brand = "Isntree",
            category = "Toner",
            priceValue = 18500,
            oldPriceValue = 22500,
            discountLabel = "Fresh",
            description = "Oil-balancing toner with green tea benefits."
        )
        addProduct(
            title = "Etude SoonJung pH 5.5 Toner",
            brand = "Etude",
            category = "Toner",
            priceValue = 17500,
            oldPriceValue = 21500,
            discountLabel = "Calm",
            description = "Gentle toner for sensitive skin daily use.",
            isFlashSale = true
        )
        addProduct(
            title = "Beauty of Joseon Ginseng Water",
            brand = "Beauty of Joseon",
            category = "Toner",
            priceValue = 22000,
            oldPriceValue = 27000,
            discountLabel = "Glow",
            description = "Hydrating toner-essence with nourishing feel."
        )
        addProduct(
            title = "Abib Heartleaf Calming Toner",
            brand = "Abib",
            category = "Toner",
            priceValue = 21500,
            oldPriceValue = 26000,
            discountLabel = "Calming",
            description = "Daily toner for redness and stress relief."
        )
        addProduct(
            title = "Torriden Balanceful Toner",
            brand = "Torriden",
            category = "Toner",
            priceValue = 20500,
            oldPriceValue = 24500,
            discountLabel = "Trending",
            description = "Fresh toner with soft light finish."
        )
        addProduct(
            title = "Some By Mi AHA BHA PHA Toner",
            brand = "Some By Mi",
            category = "Toner",
            priceValue = 23000,
            oldPriceValue = 28000,
            discountLabel = "Exfoliating",
            description = "Daily exfoliating toner for clearer skin.",
            isBestSeller = true
        )
        addProduct(
            title = "Mamonde Rose Water Toner",
            brand = "Mamonde",
            category = "Toner",
            priceValue = 19500,
            oldPriceValue = 23500,
            discountLabel = "Deal",
            description = "Soft rose toner for daily hydration."
        )

        // Cleanser (10)
        addProduct(
            title = "COSRX Low pH Morning Cleanser",
            brand = "COSRX",
            category = "Cleanser",
            priceValue = 12000,
            oldPriceValue = 15000,
            discountLabel = "Daily",
            description = "Low pH cleanser for gentle morning cleansing.",
            isBestSeller = true
        )
        addProduct(
            title = "Round Lab Mugwort Cleanser",
            brand = "Round Lab",
            category = "Cleanser",
            priceValue = 14500,
            oldPriceValue = 18000,
            discountLabel = "Calm",
            description = "Soft foam cleanser for sensitive skin."
        )
        addProduct(
            title = "Anua Heartleaf Foam Cleanser",
            brand = "Anua",
            category = "Cleanser",
            priceValue = 15000,
            oldPriceValue = 18500,
            discountLabel = "Popular",
            description = "Refreshing cleanser with gentle daily finish."
        )
        addProduct(
            title = "Etude SoonJung Whip Cleanser",
            brand = "Etude",
            category = "Cleanser",
            priceValue = 13500,
            oldPriceValue = 17000,
            discountLabel = "Soft",
            description = "Whipped cleanser for barrier-friendly wash."
        )
        addProduct(
            title = "Innisfree Blueberry Cleanser",
            brand = "Innisfree",
            category = "Cleanser",
            priceValue = 12500,
            oldPriceValue = 16000,
            discountLabel = "Fresh",
            description = "Refreshing facial cleanser for balanced skin.",
            isFlashSale = true
        )
        addProduct(
            title = "Dr.G pH Cleansing Gel Foam",
            brand = "Dr.G",
            category = "Cleanser",
            priceValue = 15500,
            oldPriceValue = 19000,
            discountLabel = "Clean",
            description = "Mild foam cleanser with non-stripping feel."
        )
        addProduct(
            title = "Abib Acne Foam Cleanser",
            brand = "Abib",
            category = "Cleanser",
            priceValue = 16000,
            oldPriceValue = 19500,
            discountLabel = "Hot",
            description = "Foam cleanser with fresh clean finish."
        )
        addProduct(
            title = "SKIN1004 Centella Ampoule Foam",
            brand = "SKIN1004",
            category = "Cleanser",
            priceValue = 16500,
            oldPriceValue = 20000,
            discountLabel = "Gentle",
            description = "Centella foam for soft cleansing routine."
        )
        addProduct(
            title = "Some By Mi Snail Cleanser",
            brand = "Some By Mi",
            category = "Cleanser",
            priceValue = 17000,
            oldPriceValue = 21000,
            discountLabel = "Repair",
            description = "Nourishing cleanser with soft foam texture."
        )
        addProduct(
            title = "Huxley Cleansing Gel Be Clean",
            brand = "Huxley",
            category = "Cleanser",
            priceValue = 18000,
            oldPriceValue = 22000,
            discountLabel = "Premium",
            description = "Gel cleanser with smooth elegant wash.",
            isBestSeller = true
        )

        // Lip Care (10)
        addProduct(
            title = "Laneige Lip Sleeping Mask Berry",
            brand = "Laneige",
            category = "Lip Care",
            priceValue = 19000,
            oldPriceValue = 23000,
            discountLabel = "Best Seller",
            description = "Overnight lip mask for smoother lips.",
            isBestSeller = true
        )
        addProduct(
            title = "Tocobo Vita Glazed Lip Mask",
            brand = "Tocobo",
            category = "Lip Care",
            priceValue = 17000,
            oldPriceValue = 21000,
            discountLabel = "Glow",
            description = "Nourishing lip care with glossy comfort."
        )
        addProduct(
            title = "Innisfree Dewy Tint Lip Balm",
            brand = "Innisfree",
            category = "Lip Care",
            priceValue = 14500,
            oldPriceValue = 18000,
            discountLabel = "Tint",
            description = "Hydrating lip balm with soft tint touch."
        )
        addProduct(
            title = "Etude Ginger Sugar Lip Balm",
            brand = "Etude",
            category = "Lip Care",
            priceValue = 13000,
            oldPriceValue = 16500,
            discountLabel = "Soft",
            description = "Classic lip balm for dry cracked lips."
        )
        addProduct(
            title = "A'pieu Honey & Milk Lip Oil",
            brand = "A'pieu",
            category = "Lip Care",
            priceValue = 14000,
            oldPriceValue = 17500,
            discountLabel = "Oil",
            description = "Comforting lip oil with glossy finish."
        )
        addProduct(
            title = "rom&nd Glasting Melting Balm",
            brand = "rom&nd",
            category = "Lip Care",
            priceValue = 16000,
            oldPriceValue = 19500,
            discountLabel = "Popular",
            description = "Tinted balm with moisturizing texture.",
            isFlashSale = true
        )
        addProduct(
            title = "Peripera Ink Mood Lip Balm",
            brand = "Peripera",
            category = "Lip Care",
            priceValue = 15000,
            oldPriceValue = 18500,
            discountLabel = "Hot",
            description = "Soft lip care with vivid moisture feel."
        )
        addProduct(
            title = "Mamonde Ceramide Lip Essence",
            brand = "Mamonde",
            category = "Lip Care",
            priceValue = 13500,
            oldPriceValue = 17000,
            discountLabel = "Repair",
            description = "Lip essence for everyday nourishment."
        )
        addProduct(
            title = "Hanyul Pure Artemisia Lip Balm",
            brand = "Hanyul",
            category = "Lip Care",
            priceValue = 15500,
            oldPriceValue = 19000,
            discountLabel = "Calm",
            description = "Soft balm for delicate lips."
        )
        addProduct(
            title = "Abib Protective Lip Balm Block",
            brand = "Abib",
            category = "Lip Care",
            priceValue = 14500,
            oldPriceValue = 18000,
            discountLabel = "Daily",
            description = "Daily protective lip balm for dryness.",
            isBestSeller = true
        )

        // Ampoule (10)
        addProduct(
            title = "SKIN1004 Madagascar Centella Ampoule",
            brand = "SKIN1004",
            category = "Ampoule",
            priceValue = 24000,
            oldPriceValue = 29000,
            discountLabel = "Calming",
            description = "Simple centella ampoule for irritation care.",
            isBestSeller = true
        )
        addProduct(
            title = "Abib Heartleaf Recovery Ampoule",
            brand = "Abib",
            category = "Ampoule",
            priceValue = 25000,
            oldPriceValue = 30000,
            discountLabel = "Fresh",
            description = "Ampoule support for smoother calmer skin."
        )
        addProduct(
            title = "Missha Night Repair Ampoule",
            brand = "Missha",
            category = "Ampoule",
            priceValue = 31000,
            oldPriceValue = 38000,
            discountLabel = "Premium",
            description = "Night ampoule for glow and elasticity support."
        )
        addProduct(
            title = "IOPE Stem III Ampoule",
            brand = "IOPE",
            category = "Ampoule",
            priceValue = 39000,
            oldPriceValue = 47000,
            discountLabel = "Luxury",
            description = "Advanced ampoule for firm healthy skin look."
        )
        addProduct(
            title = "Torriden Dive-In Moisture Ampoule",
            brand = "Torriden",
            category = "Ampoule",
            priceValue = 23000,
            oldPriceValue = 28000,
            discountLabel = "Hydration",
            description = "Hydrating ampoule with watery finish."
        )
        addProduct(
            title = "Isntree Onion Newpair Ampoule",
            brand = "Isntree",
            category = "Ampoule",
            priceValue = 26000,
            oldPriceValue = 31500,
            discountLabel = "Repair",
            description = "Ampoule focused on calming and mark care."
        )
        addProduct(
            title = "Dr.Ceuracle Kombucha Ampoule",
            brand = "Dr.Ceuracle",
            category = "Ampoule",
            priceValue = 29500,
            oldPriceValue = 35000,
            discountLabel = "Glow",
            description = "Nourishing ampoule for radiant healthy look.",
            isFlashSale = true
        )
        addProduct(
            title = "Some By Mi Panthenol Ampoule",
            brand = "Some By Mi",
            category = "Ampoule",
            priceValue = 25500,
            oldPriceValue = 30500,
            discountLabel = "Barrier",
            description = "Barrier support ampoule for sensitive skin."
        )
        addProduct(
            title = "Beauty of Joseon Revive Ampoule",
            brand = "Beauty of Joseon",
            category = "Ampoule",
            priceValue = 22500,
            oldPriceValue = 27000,
            discountLabel = "Eye Care",
            description = "Light ampoule for smoother eye area and glow."
        )
        addProduct(
            title = "Huxley Grab Water Essence Ampoule",
            brand = "Huxley",
            category = "Ampoule",
            priceValue = 30000,
            oldPriceValue = 36000,
            discountLabel = "Moisture",
            description = "Elegant moisture ampoule for daily use.",
            isBestSeller = true
        )

        return result
    }

    private fun categoryImage(category: String): String {
        return when (category) {
            "Skincare" -> "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?auto=format&fit=crop&w=900&q=80"
            "Serum" -> "https://images.unsplash.com/photo-1556228578-8c89e6adf883?auto=format&fit=crop&w=900&q=80"
            "Sun Care" -> "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?auto=format&fit=crop&w=900&q=80"
            "Cream" -> "https://images.unsplash.com/photo-1571781926291-c477ebfd024b?auto=format&fit=crop&w=900&q=80"
            "Toner" -> "https://images.unsplash.com/photo-1612817159949-195b6eb9e31a?auto=format&fit=crop&w=900&q=80"
            "Cleanser" -> "https://images.unsplash.com/photo-1556229010-aa3f7ff66b24?auto=format&fit=crop&w=900&q=80"
            "Lip Care" -> "https://images.unsplash.com/photo-1586495777744-4413f21062fa?auto=format&fit=crop&w=900&q=80"
            "Ampoule" -> "https://images.unsplash.com/photo-1625772452859-1c03d5bf1137?auto=format&fit=crop&w=900&q=80"
            else -> "https://images.unsplash.com/photo-1596462502278-27bfdc403348?auto=format&fit=crop&w=900&q=80"
        }
    }

    private fun formatPrice(value: Int): String {
        return "₩${value.toString().reversed().chunked(3).joinToString(",").reversed()}"
    }
}