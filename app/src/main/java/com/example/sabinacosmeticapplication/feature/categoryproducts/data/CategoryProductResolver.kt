package com.example.sabinacosmeticapplication.feature.categoryproducts.data

import android.net.Uri
import com.example.sabinacosmeticapplication.data.model.AppCategory
import com.example.sabinacosmeticapplication.data.model.Product
import com.example.sabinacosmeticapplication.feature.categories.data.AppCategoryCatalog
import java.util.Locale

object CategoryProductResolver {

    enum class CategoryFamily {
        SKINCARE,
        HAIR,
        BODY,
        MAKEUP,
        WELLNESS,
        MEN_GROOMING,
        GENERAL,
    }

    data class CategoryProfile(
        val rawRouteValue: String,
        val normalizedKey: String,
        val displayName: String,
        val subtitle: String,
        val keywords: List<String>,
        val fallbackKeywords: List<String>,
        val family: CategoryFamily,
    )

    fun resolve(routeCategory: String): CategoryProfile {
        val decodedRouteValue = Uri.decode(routeCategory).trim()
        val normalizedRouteValue = normalize(decodedRouteValue)

        val allCategories = AppCategoryCatalog.rootCategories.flatMap(::flatten)

        val matchedCategory = allCategories.firstOrNull { category ->
            normalize(category.title) == normalizedRouteValue ||
                    normalize(category.slug) == normalizedRouteValue ||
                    normalize(category.id) == normalizedRouteValue
        }

        if (matchedCategory != null) {
            return CategoryProfile(
                rawRouteValue = decodedRouteValue,
                normalizedKey = normalize(matchedCategory.title),
                displayName = matchedCategory.title,
                subtitle = matchedCategory.subtitle,
                keywords = buildCategoryKeywords(matchedCategory),
                fallbackKeywords = buildFallbackKeywords(matchedCategory.title),
                family = resolveFamily(matchedCategory.title),
            )
        }

        return CategoryProfile(
            rawRouteValue = decodedRouteValue,
            normalizedKey = normalizedRouteValue,
            displayName = decodedRouteValue.ifBlank { "Category" },
            subtitle = "Explore curated products for this category.",
            keywords = buildKeywordList(
                decodedRouteValue,
                defaultAliasesFor(decodedRouteValue),
            ),
            fallbackKeywords = buildFallbackKeywords(decodedRouteValue),
            family = resolveFamily(decodedRouteValue),
        )
    }

    fun calculateMatchScore(
        profile: CategoryProfile,
        product: Product,
    ): Int {
        val categoryText = normalize(product.category)
        val titleText = normalize(product.title)
        val descriptionText = normalize(product.description)
        val brandText = normalize(product.brand)

        val categoryTokens = tokenize(categoryText)
        val titleTokens = tokenize(titleText)
        val descriptionTokens = tokenize(descriptionText)
        val brandTokens = tokenize(brandText)

        var score = 0

        profile.keywords.forEach { keyword ->
            val normalizedKeyword = normalize(keyword)
            if (normalizedKeyword.isBlank()) return@forEach

            val keywordTokens = tokenize(normalizedKeyword)
            val isSingleTokenKeyword = keywordTokens.size == 1

            if (categoryText == normalizedKeyword) score += 140
            if (titleText == normalizedKeyword) score += 120

            if (categoryText.contains(normalizedKeyword)) score += 80
            if (titleText.contains(normalizedKeyword)) score += 72
            if (descriptionText.contains(normalizedKeyword)) score += 38
            if (brandText.contains(normalizedKeyword)) score += 16

            if (isSingleTokenKeyword) {
                val token = keywordTokens.first()
                if (token in categoryTokens) score += 42
                if (token in titleTokens) score += 36
                if (token in descriptionTokens) score += 20
                if (token in brandTokens) score += 8
            }
        }

        if (categoryText.contains(profile.normalizedKey)) score += 95
        if (titleText.contains(profile.normalizedKey)) score += 82
        if (descriptionText.contains(profile.normalizedKey)) score += 34

        if (resolveProductFamily(product) == profile.family) score += 28

        if (product.isBestSeller) score += 8
        if (product.isFlashSale) score += 8
        if (product.hasDiscount) score += 5
        if (product.hasAnyImage) score += 5

        return score
    }

    fun calculateFallbackScore(
        profile: CategoryProfile,
        product: Product,
    ): Int {
        val productFamily = resolveProductFamily(product)
        if (productFamily != profile.family && profile.family != CategoryFamily.GENERAL) {
            return 0
        }

        val searchable = normalize(product.searchableText)
        var score = 0

        profile.fallbackKeywords.forEach { keyword ->
            val normalizedKeyword = normalize(keyword)
            if (normalizedKeyword.isBlank()) return@forEach

            if (searchable.contains(normalizedKeyword)) {
                score += 30
            }
        }

        if (productFamily == profile.family) score += 40

        if (product.isBestSeller) score += 8
        if (product.isFlashSale) score += 8
        if (product.hasDiscount) score += 5
        if (product.hasAnyImage) score += 5

        return score
    }

    private fun buildCategoryKeywords(category: AppCategory): List<String> {
        return buildKeywordList(
            category.title,
            listOf(category.subtitle),
            listOf(category.slug),
            defaultAliasesFor(category.title),
        )
    }

    private fun buildFallbackKeywords(title: String): List<String> {
        return when (resolveFamily(title)) {
            CategoryFamily.SKINCARE -> listOf(
                "cleanser",
                "toner",
                "serum",
                "cream",
                "sunscreen",
                "mask",
                "skincare",
            )

            CategoryFamily.HAIR -> listOf(
                "hair",
                "shampoo",
                "conditioner",
                "treatment",
                "scalp",
                "hair care",
            )

            CategoryFamily.BODY -> listOf(
                "body wash",
                "body lotion",
                "body cream",
                "hand cream",
                "foot cream",
                "fragrance",
                "body care",
            )

            CategoryFamily.MAKEUP -> listOf(
                "makeup",
                "lipstick",
                "foundation",
                "concealer",
                "mascara",
                "lip tint",
            )

            CategoryFamily.WELLNESS -> listOf(
                "vitamin",
                "supplement",
                "probiotic",
                "collagen",
                "biotin",
                "wellness",
            )

            CategoryFamily.MEN_GROOMING -> listOf(
                "shaving",
                "beard",
                "after shave",
                "grooming",
            )

            CategoryFamily.GENERAL -> listOf(
                "beauty",
                "cosmetic",
                "skincare",
                "makeup",
            )
        }
    }

    private fun resolveFamily(title: String): CategoryFamily {
        return when (normalize(title)) {
            "cleanser",
            "toner",
            "serum",
            "cream",
            "sunscreen",
            "mask pack",
            "eye and lip care",
            "face care",
            "skin care",
            "acne care",
            "brightening",
            "dry skin",
            "sensitive skin",
            "anti aging",
            "pore care",
            "rash care" -> CategoryFamily.SKINCARE

            "hair care",
            "scalp care",
            "hair",
            "scalp",
            "hair loss care",
            "hair growth support",
            "dandruff and scalp",
            "styling",
            "shampoo",
            "conditioner",
            "treatment and mask" -> CategoryFamily.HAIR

            "body care",
            "body wash",
            "body lotion",
            "hand care",
            "foot care",
            "nail care",
            "fragrance",
            "feminine care",
            "body",
            "hands",
            "feet",
            "nails" -> CategoryFamily.BODY

            "makeup",
            "base makeup",
            "lip makeup",
            "eye makeup",
            "makeup remover" -> CategoryFamily.MAKEUP

            "multivitamins",
            "probiotics",
            "collagen",
            "biotin",
            "women vitamins",
            "men vitamins",
            "kids vitamins",
            "digestive support",
            "immunity support" -> CategoryFamily.WELLNESS

            "shaving and beard",
            "after shave" -> CategoryFamily.MEN_GROOMING

            else -> CategoryFamily.GENERAL
        }
    }

    private fun resolveProductFamily(product: Product): CategoryFamily {
        val source = normalize(
            listOf(
                product.category,
                product.title,
                product.description,
                product.brand,
            ).joinToString(" ")
        )

        return when {
            containsAny(source, "shaving", "beard", "after shave", "aftershave", "razor") -> CategoryFamily.MEN_GROOMING
            containsAny(source, "vitamin", "supplement", "probiotic", "collagen", "biotin", "wellness") -> CategoryFamily.WELLNESS
            containsAny(source, "lipstick", "lip tint", "foundation", "concealer", "mascara", "eyeliner", "makeup", "cushion") -> CategoryFamily.MAKEUP
            containsAny(source, "body wash", "body lotion", "body cream", "hand cream", "foot cream", "fragrance", "perfume", "feminine wash") -> CategoryFamily.BODY
            containsAny(source, "hair", "shampoo", "conditioner", "scalp", "hair loss", "hair mask", "hair treatment") -> CategoryFamily.HAIR
            containsAny(source, "cleanser", "toner", "serum", "cream", "sunscreen", "mask", "skincare", "eye cream", "lip balm") -> CategoryFamily.SKINCARE
            else -> CategoryFamily.GENERAL
        }
    }

    private fun buildKeywordList(vararg groups: List<String>): List<String> {
        val result = mutableListOf<String>()
        groups.forEach { group ->
            group.forEach { value ->
                val trimmed = value.trim()
                if (trimmed.isNotBlank()) {
                    result.add(trimmed)
                }
            }
        }
        return result.distinctBy { normalize(it) }
    }

    private fun buildKeywordList(
        primary: String,
        vararg groups: List<String>,
    ): List<String> {
        return buildKeywordList(listOf(primary), *groups)
    }

    private fun flatten(category: AppCategory): List<AppCategory> {
        return listOf(category) + category.children.flatMap(::flatten)
    }

    private fun tokenize(value: String): Set<String> {
        return normalize(value)
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .toSet()
    }

    private fun defaultAliasesFor(title: String): List<String> {
        return when (normalize(title)) {
            "cleanser" -> listOf("face wash", "cleansing", "cleansing foam", "cleansing gel", "foam cleanser", "cleansing oil")
            "toner" -> listOf("skin toner", "mist", "hydrating toner")
            "serum" -> listOf("ampoule", "essence", "face serum", "vitamin c", "retinol", "niacinamide")
            "cream" -> listOf("moisturizer", "moisturiser", "gel cream", "face cream")
            "sunscreen" -> listOf("sun cream", "sun care", "spf", "uv protection", "sunblock")
            "mask pack" -> listOf("mask", "sheet mask", "sleeping mask", "wash off mask", "face mask")
            "eye and lip care" -> listOf("eye cream", "lip balm", "lip care", "eye patch")
            "hair care" -> listOf("hair", "shampoo", "conditioner", "hair treatment", "scalp")
            "body care" -> listOf("body wash", "body lotion", "body cream", "hand cream", "foot cream")
            "makeup" -> listOf("foundation", "lipstick", "mascara", "concealer", "makeup")
            "shaving and beard" -> listOf("shaving", "beard", "after shave", "razor")
            "after shave" -> listOf("aftershave", "post shave", "shaving")
            "multivitamins" -> listOf("multivitamin", "daily vitamin", "supplement")
            "probiotics" -> listOf("digestive", "gut health", "probiotic")
            "collagen" -> listOf("beauty supplement", "skin supplement", "collagen drink")
            "biotin" -> listOf("hair vitamin", "nail vitamin", "biotin supplement")
            "hair loss care" -> listOf("hair loss", "hair fall", "thinning hair", "scalp")
            "rash care" -> listOf("soothing", "barrier cream", "sensitive care", "calming")
            else -> emptyList()
        }
    }

    private fun containsAny(
        source: String,
        vararg keywords: String,
    ): Boolean {
        return keywords.any { keyword ->
            source.contains(normalize(keyword))
        }
    }

    private fun normalize(value: String): String {
        return value
            .lowercase(Locale.US)
            .replace("&", " and ")
            .replace("/", " ")
            .replace("-", " ")
            .replace("_", " ")
            .replace(Regex("[^a-z0-9 ]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}