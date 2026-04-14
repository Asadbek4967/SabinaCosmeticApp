package com.example.sabinacosmeticapplication.feature.categories.data

import com.example.sabinacosmeticapplication.data.model.AppCategory

object AppCategoryCatalog {

    val rootCategories: List<AppCategory> = listOf(
        women(),
        men(),
        babyKids(),
        byBodyArea(),
        byConcern(),
        vitaminsWellness(),
    )

    private fun women() = AppCategory(
        id = "women",
        title = "Women",
        subtitle = "Face, hair, body, beauty & feminine care",
        iconName = "female",
        slug = "women",
        parentId = null,
        children = listOf(
            AppCategory(
                id = "women_face",
                title = "Face Care",
                subtitle = "Daily skincare and treatment",
                iconName = "face",
                slug = "women-face",
                parentId = "women",
                children = listOf(
                    leaf("women_face_cleanser", "Cleanser", "Foam, gel, oil cleansers", "cleanser", "women-face-cleanser", "women_face"),
                    leaf("women_face_toner", "Toner", "Hydrating and balancing toners", "toner", "women-face-toner", "women_face"),
                    leaf("women_face_serum", "Serum", "Targeted face serums", "serum", "women-face-serum", "women_face"),
                    leaf("women_face_cream", "Cream", "Moisturizing face creams", "cream", "women-face-cream", "women_face"),
                    leaf("women_face_sunscreen", "Sunscreen", "UV care and sun protection", "sun", "women-face-sunscreen", "women_face"),
                    leaf("women_face_mask", "Mask Pack", "Sheet masks and wash-off masks", "mask", "women-face-mask", "women_face"),
                    leaf("women_face_eye_lip", "Eye & Lip Care", "Sensitive point care", "eye", "women-face-eye-lip", "women_face"),
                )
            ),
            AppCategory(
                id = "women_hair",
                title = "Hair Care",
                subtitle = "From scalp to hair ends",
                iconName = "hair",
                slug = "women-hair",
                parentId = "women",
                children = listOf(
                    leaf("women_hair_shampoo", "Shampoo", "Daily cleansing shampoos", "shampoo", "women-hair-shampoo", "women_hair"),
                    leaf("women_hair_conditioner", "Conditioner", "Softening and smoothing care", "conditioner", "women-hair-conditioner", "women_hair"),
                    leaf("women_hair_treatment", "Treatment & Mask", "Repair and deep care", "treatment", "women-hair-treatment", "women_hair"),
                    leaf("women_hair_scalp", "Scalp Care", "Scalp balancing products", "scalp", "women-hair-scalp", "women_hair"),
                    leaf("women_hair_loss", "Hair Loss Care", "Hair thinning support", "hairloss", "women-hair-loss", "women_hair"),
                    leaf("women_hair_growth", "Hair Growth Support", "Growth and strengthening support", "growth", "women-hair-growth", "women_hair"),
                    leaf("women_hair_styling", "Styling", "Heat, curl and styling care", "styling", "women-hair-styling", "women_hair"),
                )
            ),
            AppCategory(
                id = "women_body",
                title = "Body Care",
                subtitle = "Head-to-toe body care",
                iconName = "body",
                slug = "women-body",
                parentId = "women",
                children = listOf(
                    leaf("women_body_wash", "Body Wash", "Daily shower products", "bodywash", "women-body-wash", "women_body"),
                    leaf("women_body_lotion", "Body Lotion", "Hydration and softness", "lotion", "women-body-lotion", "women_body"),
                    leaf("women_body_hand", "Hand Care", "Hand creams and repair", "hand", "women-body-hand", "women_body"),
                    leaf("women_body_foot", "Foot Care", "Heel and foot repair", "foot", "women-body-foot", "women_body"),
                    leaf("women_body_nail", "Nail Care", "Nail and cuticle care", "nail", "women-body-nail", "women_body"),
                    leaf("women_body_feminine", "Feminine Care", "Intimate and women wellness care", "feminine", "women-body-feminine", "women_body"),
                    leaf("women_body_fragrance", "Fragrance", "Perfume and body mist", "perfume", "women-body-fragrance", "women_body"),
                )
            ),
            AppCategory(
                id = "women_makeup",
                title = "Makeup",
                subtitle = "Daily and glam makeup essentials",
                iconName = "makeup",
                slug = "women-makeup",
                parentId = "women",
                children = listOf(
                    leaf("women_makeup_base", "Base Makeup", "Foundation, cushion, concealer", "foundation", "women-makeup-base", "women_makeup"),
                    leaf("women_makeup_lips", "Lip Makeup", "Lipstick, tint and balm", "lip", "women-makeup-lips", "women_makeup"),
                    leaf("women_makeup_eyes", "Eye Makeup", "Mascara, liner, shadow", "eyemakeup", "women-makeup-eyes", "women_makeup"),
                    leaf("women_makeup_remover", "Makeup Remover", "First cleanse and removal", "remover", "women-makeup-remover", "women_makeup"),
                )
            ),
        )
    )

    private fun men() = AppCategory(
        id = "men",
        title = "Men",
        subtitle = "Simple and effective men care",
        iconName = "male",
        slug = "men",
        parentId = null,
        children = listOf(
            leafGroup(
                id = "men_face",
                title = "Face Care",
                subtitle = "Men skincare essentials",
                iconName = "face",
                slug = "men-face",
                parentId = "men",
                children = listOf(
                    leaf("men_face_cleanser", "Cleanser", "Men face wash", "cleanser", "men-face-cleanser", "men_face"),
                    leaf("men_face_toner", "Toner", "Hydration and balancing", "toner", "men-face-toner", "men_face"),
                    leaf("men_face_moisturizer", "Moisturizer", "Cream and lotion", "cream", "men-face-moisturizer", "men_face"),
                    leaf("men_face_sun", "Sunscreen", "Daily UV protection", "sun", "men-face-sun", "men_face"),
                )
            ),
            leafGroup(
                id = "men_hair",
                title = "Hair Care",
                subtitle = "Men scalp and hair support",
                iconName = "hair",
                slug = "men-hair",
                parentId = "men",
                children = listOf(
                    leaf("men_hair_shampoo", "Shampoo", "Daily hair wash", "shampoo", "men-hair-shampoo", "men_hair"),
                    leaf("men_hair_scalp", "Scalp Care", "Scalp balance and cleanliness", "scalp", "men-hair-scalp", "men_hair"),
                    leaf("men_hair_loss", "Hair Loss Care", "Hair thinning support", "hairloss", "men-hair-loss", "men_hair"),
                )
            ),
            leafGroup(
                id = "men_shaving",
                title = "Shaving & Beard",
                subtitle = "Beard, aftershave and grooming",
                iconName = "beard",
                slug = "men-shaving",
                parentId = "men",
                children = listOf(
                    leaf("men_shaving_foam", "Shaving", "Foam, gel and razors", "shaving", "men-shaving-foam", "men_shaving"),
                    leaf("men_shaving_after", "After Shave", "Post-shave soothing care", "aftershave", "men-shaving-after", "men_shaving"),
                    leaf("men_shaving_beard", "Beard Care", "Beard oil and grooming", "beard", "men-shaving-beard", "men_shaving"),
                )
            ),
            leafGroup(
                id = "men_body",
                title = "Body & Fragrance",
                subtitle = "Body wash, lotion and perfume",
                iconName = "body",
                slug = "men-body",
                parentId = "men",
                children = listOf(
                    leaf("men_body_wash", "Body Wash", "Men body cleansing", "bodywash", "men-body-wash", "men_body"),
                    leaf("men_body_lotion", "Body Lotion", "Daily body hydration", "lotion", "men-body-lotion", "men_body"),
                    leaf("men_body_perfume", "Perfume", "Men fragrance selection", "perfume", "men-body-perfume", "men_body"),
                )
            ),
        )
    )

    private fun babyKids() = AppCategory(
        id = "baby_kids",
        title = "Baby & Kids",
        subtitle = "Gentle care for babies and kids",
        iconName = "baby",
        slug = "baby-kids",
        parentId = null,
        children = listOf(
            leafGroup(
                id = "baby_bath",
                title = "Bath & Wash",
                subtitle = "Shampoo and body wash for kids",
                iconName = "bath",
                slug = "baby-bath",
                parentId = "baby_kids",
                children = listOf(
                    leaf("baby_bath_shampoo", "Shampoo", "Gentle cleansing shampoo", "shampoo", "baby-bath-shampoo", "baby_bath"),
                    leaf("baby_bath_wash", "Body Wash", "Mild body cleansing", "bodywash", "baby-bath-wash", "baby_bath"),
                )
            ),
            leafGroup(
                id = "baby_skin",
                title = "Skin Care",
                subtitle = "Soft baby and kids skincare",
                iconName = "skin",
                slug = "baby-skin",
                parentId = "baby_kids",
                children = listOf(
                    leaf("baby_skin_lotion", "Lotion", "Daily moisturizing care", "lotion", "baby-skin-lotion", "baby_skin"),
                    leaf("baby_skin_cream", "Cream", "Barrier and moisture care", "cream", "baby-skin-cream", "baby_skin"),
                    leaf("baby_skin_sun", "Sunscreen", "Gentle sun protection", "sun", "baby-skin-sun", "baby_skin"),
                )
            ),
            leafGroup(
                id = "baby_sensitive",
                title = "Sensitive & Rash Care",
                subtitle = "For delicate and irritated skin",
                iconName = "sensitive",
                slug = "baby-sensitive",
                parentId = "baby_kids",
                children = listOf(
                    leaf("baby_sensitive_rash", "Rash Care", "Soothing and diaper rash support", "rash", "baby-sensitive-rash", "baby_sensitive"),
                    leaf("baby_sensitive_barrier", "Barrier Repair", "Protective skin barrier care", "barrier", "baby-sensitive-barrier", "baby_sensitive"),
                )
            ),
            leafGroup(
                id = "baby_vitamins",
                title = "Kids Vitamins",
                subtitle = "Daily vitamin support for kids",
                iconName = "vitamin",
                slug = "baby-vitamins",
                parentId = "baby_kids",
                children = listOf(
                    leaf("baby_vitamins_daily", "Daily Vitamins", "Basic daily vitamin support", "vitamin", "baby-vitamins-daily", "baby_vitamins"),
                    leaf("baby_vitamins_digestive", "Digestive Support", "Gut balance and probiotics", "digestive", "baby-vitamins-digestive", "baby_vitamins"),
                )
            ),
        )
    )

    private fun byBodyArea() = AppCategory(
        id = "by_body_area",
        title = "By Body Area",
        subtitle = "Find products from head to toe",
        iconName = "bodymap",
        slug = "by-body-area",
        parentId = null,
        children = listOf(
            leaf("area_scalp", "Scalp", "Scalp-focused care", "scalp", "area-scalp", "by_body_area"),
            leaf("area_hair", "Hair", "Hair repair and styling", "hair", "area-hair", "by_body_area"),
            leaf("area_face", "Face", "Face cleansing and treatment", "face", "area-face", "by_body_area"),
            leaf("area_eyes", "Eyes", "Eye area products", "eye", "area-eyes", "by_body_area"),
            leaf("area_lips", "Lips", "Lip nourishment and color", "lip", "area-lips", "by_body_area"),
            leaf("area_body", "Body", "Body wash and moisturizers", "body", "area-body", "by_body_area"),
            leaf("area_hands", "Hands", "Hand moisturizers and repair", "hand", "area-hands", "by_body_area"),
            leaf("area_feet", "Feet", "Foot and heel care", "foot", "area-feet", "by_body_area"),
            leaf("area_nails", "Nails", "Nail beauty and repair", "nail", "area-nails", "by_body_area"),
        )
    )

    private fun byConcern() = AppCategory(
        id = "by_concern",
        title = "Problem Solutions",
        subtitle = "Shop by concern or goal",
        iconName = "concern",
        slug = "by-concern",
        parentId = null,
        children = listOf(
            leaf("concern_acne", "Acne Care", "Pimple and blemish support", "acne", "concern-acne", "by_concern"),
            leaf("concern_brightening", "Brightening", "Dark spot and dullness care", "brightening", "concern-brightening", "by_concern"),
            leaf("concern_dry_skin", "Dry Skin", "Deep hydration and barrier support", "dry", "concern-dry-skin", "by_concern"),
            leaf("concern_sensitive", "Sensitive Skin", "Gentle soothing products", "sensitive", "concern-sensitive", "by_concern"),
            leaf("concern_anti_aging", "Anti-aging", "Firmness and wrinkle care", "aging", "concern-anti-aging", "by_concern"),
            leaf("concern_pores", "Pore Care", "Sebum and pore tightening", "pore", "concern-pores", "by_concern"),
            leaf("concern_hair_loss", "Hair Loss Care", "Hair fall and thin hair support", "hairloss", "concern-hair-loss", "by_concern"),
            leaf("concern_scalp", "Dandruff & Scalp", "Flakes and scalp balance", "dandruff", "concern-scalp", "by_concern"),
            leaf("concern_digestive", "Digestive Support", "Gut and stomach support", "digestive", "concern-digestive", "by_concern"),
            leaf("concern_immunity", "Immunity Support", "Daily immune wellness", "immune", "concern-immunity", "by_concern"),
        )
    )

    private fun vitaminsWellness() = AppCategory(
        id = "vitamins_wellness",
        title = "Vitamins & Wellness",
        subtitle = "Beauty and daily wellness support",
        iconName = "vitamin",
        slug = "vitamins-wellness",
        parentId = null,
        children = listOf(
            leaf("wellness_multivitamin", "Multivitamins", "Daily nutrition support", "vitamin", "wellness-multivitamin", "vitamins_wellness"),
            leaf("wellness_collagen", "Collagen", "Beauty supplement support", "collagen", "wellness-collagen", "vitamins_wellness"),
            leaf("wellness_biotin", "Biotin", "Hair and nail support", "biotin", "wellness-biotin", "vitamins_wellness"),
            leaf("wellness_probiotics", "Probiotics", "Digestive balance support", "probiotic", "wellness-probiotics", "vitamins_wellness"),
            leaf("wellness_women", "Women Vitamins", "Women-focused nutrition", "female", "wellness-women", "vitamins_wellness"),
            leaf("wellness_men", "Men Vitamins", "Men daily health support", "male", "wellness-men", "vitamins_wellness"),
            leaf("wellness_kids", "Kids Vitamins", "Vitamins for growing kids", "baby", "wellness-kids", "vitamins_wellness"),
        )
    )

    private fun leaf(
        id: String,
        title: String,
        subtitle: String,
        iconName: String,
        slug: String,
        parentId: String,
    ) = AppCategory(
        id = id,
        title = title,
        subtitle = subtitle,
        iconName = iconName,
        slug = slug,
        parentId = parentId,
        children = emptyList(),
    )

    private fun leafGroup(
        id: String,
        title: String,
        subtitle: String,
        iconName: String,
        slug: String,
        parentId: String,
        children: List<AppCategory>,
    ) = AppCategory(
        id = id,
        title = title,
        subtitle = subtitle,
        iconName = iconName,
        slug = slug,
        parentId = parentId,
        children = children,
    )
}