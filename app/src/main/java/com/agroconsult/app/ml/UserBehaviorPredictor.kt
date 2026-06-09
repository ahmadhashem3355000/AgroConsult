package com.agroconsult.app.ml

import com.agroconsult.app.data.models.Product
import com.agroconsult.app.data.models.User
import kotlin.math.exp

/**
 * نموذج التنبؤ بسلوك المستخدم
 * يستخدم Logistic Regression للتنبؤ باحتمالية شراء المستخدم للمنتج
 */
class UserBehaviorPredictor {

    /**
     * الأوزان المعايرة (تم تدريبها مسبقاً)
     */
    private val weights = doubleArrayOf(
        0.5,    // وزن التقييم
        0.3,    // وزن السعر
        0.4,    // وزن عدد التقييمات
        0.2,    // وزن الفئة
        0.6     // وزن التوفر
    )

    private val bias = 0.1

    /**
     * التنبؤ باحتمالية أن يشتري المستخدم المنتج
     */
    fun predictPurchaseProbability(
        user: User,
        product: Product,
        userHistory: List<Product> = emptyList()
    ): Double {
        val features = extractFeatures(user, product, userHistory)
        val zScore = calculateZScore(features, weights, bias)
        return sigmoid(zScore)
    }

    /**
     * استخراج الخصائص من المستخدم والمنتج
     */
    private fun extractFeatures(
        user: User,
        product: Product,
        userHistory: List<Product>
    ): DoubleArray {
        val avgProductRating = if (userHistory.isNotEmpty()) {
            userHistory.sumOf { it.rating } / userHistory.size
        } else {
            3.5
        }

        val avgProductPrice = if (userHistory.isNotEmpty()) {
            userHistory.sumOf { it.price } / userHistory.size
        } else {
            0.0
        }

        return doubleArrayOf(
            // تقييم المنتج (معياري)
            normalizeRating(product.rating),
            // الفرق بين سعر المنتج والمتوسط
            normalizePriceDifference(product.price, avgProductPrice),
            // عدد التقييمات (معياري)
            normalizeReviewCount(product.reviewCount),
            // التشابه بين فئات المنتجات المفضلة للمستخدم
            calculateCategorySimilarity(product, userHistory),
            // توفر المنتج
            if (product.inStock) 1.0 else 0.0
        )
    }

    /**
     * تطبيع التقييم (0-5) إلى نطاق (0-1)
     */
    private fun normalizeRating(rating: Double): Double {
        return (rating - 1.0) / 4.0
    }

    /**
     * تطبيع الفرق في السعر
     */
    private fun normalizePriceDifference(price: Double, avgPrice: Double): Double {
        return if (avgPrice > 0) {
            val difference = kotlin.math.abs(price - avgPrice) / avgPrice
            kotlin.math.min(difference, 1.0)
        } else {
            0.5
        }
    }

    /**
     * تطبيع عدد التقييمات
     */
    private fun normalizeReviewCount(count: Int): Double {
        return kotlin.math.min(count.toDouble() / 100.0, 1.0)
    }

    /**
     * حساب التشابه بين فئات المنتجات
     */
    private fun calculateCategorySimilarity(
        product: Product,
        userHistory: List<Product>
    ): Double {
        if (userHistory.isEmpty()) return 0.5

        val productCategory = product.category.toString()
        val similarCount = userHistory.count {
            it.category.toString() == productCategory
        }

        return similarCount.toDouble() / userHistory.size
    }

    /**
     * حساب Z-Score
     */
    private fun calculateZScore(
        features: DoubleArray,
        weights: DoubleArray,
        bias: Double
    ): Double {
        var zScore = bias
        for (i in features.indices) {
            zScore += features[i] * weights[i]
        }
        return zScore
    }

    /**
     * دالة Sigmoid للحصول على احتمالية (0-1)
     */
    private fun sigmoid(x: Double): Double {
        return 1.0 / (1.0 + exp(-x))
    }

    /**
     * التنبؤ بالفئة الأفضل للمستخدم
     */
    fun predictBestCategory(
        user: User,
        allCategories: List<String>,
        userHistory: List<Product>
    ): String {
        val categoryScores = allCategories.map { category ->
            val categoryProducts = userHistory.filter {
                it.category.toString() == category
            }

            val categoryScore = if (categoryProducts.isNotEmpty()) {
                categoryProducts.sumOf { it.rating } / categoryProducts.size
            } else {
                3.0
            }

            category to categoryScore
        }

        return categoryScores.maxByOrNull { it.second }?.first ?: "VEGETABLES"
    }

    /**
     * حساب درجة الثقة للتنبؤ
     */
    fun calculateConfidence(
        probability: Double,
        userHistorySize: Int
    ): Double {
        // الثقة تزيد مع عدد المنتجات المشتراة
        val confidenceMultiplier = kotlin.math.min(userHistorySize / 10.0, 1.0)
        return probability * (0.5 + 0.5 * confidenceMultiplier)
    }
}
