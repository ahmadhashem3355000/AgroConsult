package com.agroconsult.app.ml

import com.agroconsult.app.data.models.Product
import com.agroconsult.app.data.models.User
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * نموذج تعلم آلي لتوصيات المنتجات
 * يستخدم تقنية التصفية التعاونية (Collaborative Filtering)
 * والتصفية القائمة على المحتوى (Content-Based Filtering)
 */
class RecommendationEngine {

    /**
     * حساب التشابه بين منتجين باستخدام مسافة Cosine
     */
    private fun calculateProductSimilarity(
        product1: Product,
        product2: Product
    ): Double {
        val vector1 = createProductVector(product1)
        val vector2 = createProductVector(product2)
        return cosineSimilarity(vector1, vector2)
    }

    /**
     * تحويل المنتج إلى متجه رقمي
     */
    private fun createProductVector(product: Product): DoubleArray {
        return doubleArrayOf(
            product.price / 1000.0,                    // السعر معياري
            product.rating,                             // التقييم
            product.reviewCount.toDouble() / 100.0,    // عدد التقييمات
            getCategory Value(product.category),       // فئة المنتج
            if (product.inStock) 1.0 else 0.0         // توفر المنتج
        )
    }

    /**
     * تحويل اسم الفئة إلى قيمة رقمية
     */
    private fun getCategoryValue(categoryName: String): Double {
        return when (categoryName) {
            "VEGETABLES" -> 0.0
            "FRUITS" -> 1.0
            "GRAINS" -> 2.0
            "SEEDS" -> 3.0
            "FERTILIZERS" -> 4.0
            "PESTICIDES" -> 5.0
            "EQUIPMENT" -> 6.0
            "TOOLS" -> 7.0
            "IRRIGATION" -> 8.0
            else -> 4.5
        }
    }

    /**
     * حساب تشابه Cosine بين متجهين
     */
    private fun cosineSimilarity(vector1: DoubleArray, vector2: DoubleArray): Double {
        if (vector1.size != vector2.size) return 0.0

        var dotProduct = 0.0
        var magnitude1 = 0.0
        var magnitude2 = 0.0

        for (i in vector1.indices) {
            dotProduct += vector1[i] * vector2[i]
            magnitude1 += vector1[i].pow(2)
            magnitude2 += vector2[i].pow(2)
        }

        magnitude1 = sqrt(magnitude1)
        magnitude2 = sqrt(magnitude2)

        return if (magnitude1 > 0 && magnitude2 > 0) {
            dotProduct / (magnitude1 * magnitude2)
        } else {
            0.0
        }
    }

    /**
     * الحصول على توصيات بناءً على المنتج المفضل للمستخدم
     */
    fun getContentBasedRecommendations(
        userFavoriteProduct: Product,
        allProducts: List<Product>,
        topN: Int = 5
    ): List<Product> {
        return allProducts
            .filter { it.id != userFavoriteProduct.id }
            .sortedByDescending { calculateProductSimilarity(userFavoriteProduct, it) }
            .take(topN)
    }

    /**
     * التصفية التعاونية - توصيات بناءً على تقييمات المستخدمين المشابهين
     */
    fun getCollaborativeRecommendations(
        userId: String,
        userRatings: Map<String, Double>,
        allUserRatings: Map<String, Map<String, Double>>,
        allProducts: List<Product>,
        topN: Int = 5
    ): List<Product> {
        // حساب التشابه بين المستخدم الحالي والمستخدمين الآخرين
        val similarUsers = findSimilarUsers(
            userId,
            userRatings,
            allUserRatings
        )

        // جمع المنتجات التي قيمها المستخدمون المشابهون بتقييمات عالية
        val recommendations = mutableMapOf<String, Double>()

        for ((similarUserId, similarity) in similarUsers) {
            val similarUserRatings = allUserRatings[similarUserId] ?: continue
            for ((productId, rating) in similarUserRatings) {
                if (productId !in userRatings) {
                    recommendations[productId] = (recommendations[productId] ?: 0.0) + (rating * similarity)
                }
            }
        }

        // ترجيع المنتجات مرتبة حسب الدرجة
        return recommendations
            .entries
            .sortedByDescending { it.value }
            .mapNotNull { entry ->
                allProducts.find { it.id == entry.key }
            }
            .take(topN)
    }

    /**
     * إيجاد المستخدمين المشابهين
     */
    private fun findSimilarUsers(
        userId: String,
        userRatings: Map<String, Double>,
        allUserRatings: Map<String, Map<String, Double>>
    ): Map<String, Double> {
        val similarUsers = mutableMapOf<String, Double>()

        for ((otherUserId, otherRatings) in allUserRatings) {
            if (otherUserId == userId) continue

            val similarity = calculateUserSimilarity(userRatings, otherRatings)
            if (similarity > 0.3) { // عتبة التشابه
                similarUsers[otherUserId] = similarity
            }
        }

        return similarUsers.toList()
            .sortedByDescending { it.second }
            .take(10)
            .toMap()
    }

    /**
     * حساب التشابه بين المستخدمين بناءً على التقييمات
     */
    private fun calculateUserSimilarity(
        ratings1: Map<String, Double>,
        ratings2: Map<String, Double>
    ): Double {
        val commonProducts = ratings1.keys.intersect(ratings2.keys)
        if (commonProducts.isEmpty()) return 0.0

        val vector1 = commonProducts.map { ratings1[it] ?: 0.0 }.toDoubleArray()
        val vector2 = commonProducts.map { ratings2[it] ?: 0.0 }.toDoubleArray()

        return cosineSimilarity(vector1, vector2)
    }

    /**
     * نموذج هجين يجمع بين التصفية التعاونية والقائمة على المحتوى
     */
    fun getHybridRecommendations(
        userId: String,
        userFavoriteProducts: List<Product>,
        userRatings: Map<String, Double>,
        allUserRatings: Map<String, Map<String, Double>>,
        allProducts: List<Product>,
        topN: Int = 5,
        contentWeight: Double = 0.4,
        collaborativeWeight: Double = 0.6
    ): List<Product> {
        val contentRecs = if (userFavoriteProducts.isNotEmpty()) {
            // دمج توصيات المحتوى من جميع المنتجات المفضلة
            userFavoriteProducts
                .flatMap {
                    getContentBasedRecommendations(it, allProducts, topN * 2)
                }
                .distinctBy { it.id }
                .take(topN * 2)
        } else {
            emptyList()
        }

        val collaborativeRecs = getCollaborativeRecommendations(
            userId,
            userRatings,
            allUserRatings,
            allProducts,
            topN * 2
        )

        // دمج النتائج بالأوزان المحددة
        val scores = mutableMapOf<String, Double>()

        contentRecs.forEachIndexed { index, product ->
            val score = (1.0 - index.toDouble() / contentRecs.size) * contentWeight
            scores[product.id] = (scores[product.id] ?: 0.0) + score
        }

        collaborativeRecs.forEachIndexed { index, product ->
            val score = (1.0 - index.toDouble() / collaborativeRecs.size) * collaborativeWeight
            scores[product.id] = (scores[product.id] ?: 0.0) + score
        }

        return scores
            .entries
            .sortedByDescending { it.value }
            .mapNotNull { entry ->
                allProducts.find { it.id == entry.key }
            }
            .take(topN)
    }

    /**
     * التنبؤ بتقييم المستخدم للمنتج
     */
    fun predictRating(
        userId: String,
        productId: String,
        userRatings: Map<String, Double>,
        allUserRatings: Map<String, Map<String, Double>>,
        product: Product
    ): Double {
        // الحصول على متوسط تقييمات المستخدمين المشابهين
        val userSimilarities = findSimilarUsers(userId, userRatings, allUserRatings)

        if (userSimilarities.isEmpty()) {
            return product.rating // إرجاع متوسط التقييم العام
        }

        var totalScore = 0.0
        var totalWeight = 0.0

        for ((similarUserId, similarity) in userSimilarities) {
            val similarUserRating = allUserRatings[similarUserId]?.get(productId)
            if (similarUserRating != null) {
                totalScore += similarUserRating * similarity
                totalWeight += similarity
            }
        }

        return if (totalWeight > 0) totalScore / totalWeight else product.rating
    }

    /**
     * الحصول على المنتجات الاتجاهية (Trending)
     */
    fun getTrendingProducts(
        allProducts: List<Product>,
        topN: Int = 10
    ): List<Product> {
        return allProducts
            .sortedWith(
                compareBy<Product> { -it.rating }
                    .thenBy { -it.reviewCount }
                    .thenBy { it.price }
            )
            .take(topN)
    }

    /**
     * الحصول على المنتجات الأفضل سعراً
     */
    fun getBestPriceProducts(
        allProducts: List<Product>,
        category: String? = null,
        topN: Int = 10
    ): List<Product> {
        val filtered = if (category != null) {
            allProducts.filter { it.category.toString() == category }
        } else {
            allProducts
        }

        return filtered
            .filter { it.inStock }
            .sortedWith(
                compareBy<Product> { it.price }
                    .thenBy { -it.rating }
            )
            .take(topN)
    }

    /**
     * الحصول على المنتجات الأعلى تقييماً
     */
    fun getTopRatedProducts(
        allProducts: List<Product>,
        minReviews: Int = 5,
        topN: Int = 10
    ): List<Product> {
        return allProducts
            .filter { it.reviewCount >= minReviews }
            .sortedByDescending { it.rating }
            .take(topN)
    }
}
