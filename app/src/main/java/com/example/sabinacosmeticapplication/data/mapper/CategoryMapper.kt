package com.example.sabinacosmeticapplication.data.mapper

object CategoryMapper {

    fun toDisplayName(category: String): String {
        return when (normalizeCategoryKey(category)) {
            "skincare" -> "Skin Care"
            "makeup" -> "Makeup"
            "haircare" -> "Hair Care"
            "bodycare" -> "Body Care"
            "fragrance" -> "Fragrance"
            else -> category.trim()
                .split(" ", "_", "-")
                .filter { it.isNotBlank() }
                .joinToString(" ") { word ->
                    word.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                }
        }
    }

    fun toBackendName(category: String): String {
        return normalizeCategoryKey(category)
    }

    fun normalizeCategoryKey(category: String): String {
        return category
            .trim()
            .lowercase()
            .replace("&", "and")
            .replace("_", "")
            .replace("-", "")
            .replace(" ", "")
    }

    fun matchesCategory(
        requestedCategory: String,
        productCategory: String,
        productTitle: String = "",
        productDescription: String = ""
    ): Boolean {
        val requestedKey = normalizeCategoryKey(requestedCategory)
        val categoryKey = normalizeCategoryKey(productCategory)
        val titleKey = normalizeCategoryKey(productTitle)
        val descriptionKey = normalizeCategoryKey(productDescription)

        if (requestedKey.isBlank()) return false
        if (categoryKey == requestedKey) return true

        return when (requestedKey) {
            "skincare" -> {
                categoryKey.contains("skincare") ||
                        titleKey.contains("skincare") ||
                        titleKey.contains("cleanser") ||
                        titleKey.contains("toner") ||
                        titleKey.contains("serum") ||
                        titleKey.contains("cream") ||
                        titleKey.contains("moisturizer") ||
                        descriptionKey.contains("skincare")
            }

            "makeup" -> {
                categoryKey.contains("makeup") ||
                        titleKey.contains("makeup") ||
                        titleKey.contains("lip") ||
                        titleKey.contains("cushion") ||
                        titleKey.contains("foundation") ||
                        titleKey.contains("mascara") ||
                        descriptionKey.contains("makeup")
            }

            "haircare" -> {
                categoryKey.contains("haircare") ||
                        titleKey.contains("hair") ||
                        titleKey.contains("shampoo") ||
                        titleKey.contains("conditioner") ||
                        titleKey.contains("treatment") ||
                        descriptionKey.contains("hair")
            }

            "bodycare" -> {
                categoryKey.contains("bodycare") ||
                        titleKey.contains("body") ||
                        titleKey.contains("lotion") ||
                        titleKey.contains("wash") ||
                        titleKey.contains("scrub") ||
                        descriptionKey.contains("body")
            }

            "fragrance" -> {
                categoryKey.contains("fragrance") ||
                        categoryKey.contains("perfume") ||
                        titleKey.contains("perfume") ||
                        titleKey.contains("mist") ||
                        titleKey.contains("fragrance") ||
                        descriptionKey.contains("fragrance")
            }

            else -> {
                categoryKey.contains(requestedKey) ||
                        titleKey.contains(requestedKey) ||
                        descriptionKey.contains(requestedKey)
            }
        }
    }
}