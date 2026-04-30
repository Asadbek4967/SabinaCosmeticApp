package com.example.sabinacosmeticapplication.core.util

object CategoryNormalizer {

    fun normalize(raw: String?): String {
        val value = raw
            ?.trim()
            ?.lowercase()
            ?.replace("_", " ")
            ?.replace("-", " ")
            ?.replace(Regex("\\s+"), " ")
            .orEmpty()

        return when (value) {
            "skincare", "skin care" -> "Skincare"
            "makeup", "make up" -> "Makeup"
            "haircare", "hair care" -> "Haircare"
            "bodycare", "body care" -> "Bodycare"
            "fragrance", "perfume" -> "Fragrance"
            "" -> "Uncategorized"
            else -> value.split(" ")
                .joinToString(" ") { word ->
                    word.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase() else char.toString()
                    }
                }
        }
    }
}