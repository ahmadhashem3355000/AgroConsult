package com.agroconsult.app.ml

import com.agroconsult.app.data.models.Product
import javax.inject.Inject
import javax.inject.Singleton

/**
 * خدمة التوصيات المدمجة
 * تجمع جميع محركات التوصيات والتحليل
 */
@Singleton
class RecommendationService @Inject constructor() {

    private val recommendationEngine = RecommendationEngine()
    private val userBehaviorPredictor = UserBehaviorPredictor()
    private val filteringEngine = AdvancedFilteringEngine()
    private val trendAnalysisEngine = TrendAnalysisEngine()

    /**
     * الحصول على توصيات شاملة للمستخدم
     */
    fun getComprehensiveRecommendations(
        userId: String,
        user: com.agroconsult.app.data.models.User,
        allProducts: List<Product>,
        userFavorites: List<Product> = emptyList(),
        userHistory: List<Product> = emptyList(),
        userRatings: Map<String, Double> = emptyMap(),
        allUserRatings: Map<String, Map<String, Double>> = emptyMap(),
        topN: Int = 10
    ): List<RecommendationResult> {
        val recommendations = mutableListOf<RecommendationResult>()

        // 1. توصيات هجينة
        if (userFavorites.isNotEmpty()) {
            val hybridRecs = recommendationEngine.getHybridRecommendations(
                userId = userId,
                userFavoriteProducts = userFavorites,
                userRatings = userRatings,
                allUserRatings = allUserRatings,
                allProducts = allProducts,
                topN = topN / 2
            )

            hybridRecs.forEach { product ->
                val probability = userBehaviorPredictor.predictPurchaseProbability(
                    user = user,
                    product = product,
                    userHistory = userHistory
                )
                val confidence = userBehaviorPredictor.calculateConfidence(
                    probability = probability,
                    userHistorySize = userHistory.size
                )

                recommendations.add(
                    RecommendationResult(
                        product = product,
                        score = probability,
                        type = "HYBRID",
                        confidence = confidence,
                        reason = "استنادًا إلى تفضيلاتك"
                    )
                )
            }
        }

        // 2. المنتجات الاتجاهية
        val trendingProducts = recommendationEngine.getTrendingProducts(
            allProducts = allProducts,
            topN = topN / 3
        )

        trendingProducts.forEach { product ->
            if (product !in recommendations.map { it.product }) {
                recommendations.add(
                    RecommendationResult(
                        product = product,
                        score = 0.7,
                        type = "TRENDING",
                        confidence = 0.8,
                        reason = "منتج اتجاهي محبوب"
                    )
                )
            }
        }

        // 3. أفضل الأسعار
        val bestPriceProducts = recommendationEngine.getBestPriceProducts(
            allProducts = allProducts,
            topN = topN / 3
        )

        bestPriceProducts.forEach { product ->
            if (product !in recommendations.map { it.product }) {
                recommendations.add(
                    RecommendationResult(
                        product = product,
                        score = 0.6,
                        type = "BEST_PRICE",
                        confidence = 0.85,
                        reason = "أفضل سعر في الفئة"
                    )
                )
            }
        }

        return recommendations
            .distinctBy { it.product.id }
            .sortedByDescending { it.score }
            .take(topN)
    }

    /**
     * البحث الذكي
     */
    fun smartSearch(
        query: String,
        allProducts: List<Product>
    ): List<Product> {
        return filteringEngine.smartSearch(query, allProducts)
    }

    /**
     * تصفية منتقدمة
     */
    fun filterProducts(
        products: List<Product>,
        filters: com.agroconsult.app.ml.ProductFilters
    ): List<Product> {
        return filteringEngine.filterProducts(products, filters)
    }

    /**
     * تجميع المنتجات
     */
    fun clusterProducts(
        products: List<Product>
    ): Map<String, List<Product>> {
        return filteringEngine.clusterSimilarProducts(products)
    }
}

/**
 * نتيجة التوصية
 */
data class RecommendationResult(
    val product: Product,
    val score: Double,           // درجة التوصية (0-1)
    val type: String,            // نوع التوصية
    val confidence: Double,       // درجة الثقة
    val reason: String           // السبب باللغة العربية
)
