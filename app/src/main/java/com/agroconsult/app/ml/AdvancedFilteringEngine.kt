package com.agroconsult.app.ml

import com.agroconsult.app.data.models.Product

/**
 * محرك التصفية المتقدم
 * يدمج عدة استراتيجيات للتوصية بناءً على السياق
 */
class AdvancedFilteringEngine {

    /**
     * تصفية منتجات متقدمة مع معايير متعددة
     */
    fun filterProducts(
        products: List<Product>,
        filters: ProductFilters
    ): List<Product> {
        return products
            .filter { product ->
                // التصفية حسب السعر
                product.price >= filters.minPrice &&
                        product.price <= filters.maxPrice &&
                        // التصفية حسب التقييم
                        product.rating >= filters.minRating &&
                        // التصفية حسب التوفر
                        (!filters.inStockOnly || product.inStock) &&
                        // التصفية حسب الفئة
                        (filters.categories.isEmpty() || product.category.toString() in filters.categories) &&
                        // التصفية حسب اسم البائع
                        (filters.sellerIds.isEmpty() || product.sellerId in filters.sellerIds) &&
                        // التصفية حسب عدد التقييمات الدنيا
                        product.reviewCount >= filters.minReviews
            }
            .let { filteredList ->
                // الترتيب
                when (filters.sortBy) {
                    SortType.PRICE_LOW_TO_HIGH -> filteredList.sortedBy { it.price }
                    SortType.PRICE_HIGH_TO_LOW -> filteredList.sortedByDescending { it.price }
                    SortType.RATING -> filteredList.sortedByDescending { it.rating }
                    SortType.NEWEST -> filteredList.sortedByDescending { it.createdAt.toDate().time }
                    SortType.MOST_POPULAR -> filteredList.sortedByDescending { it.reviewCount }
                }
            }
    }

    /**
     * البحث الذكي عن المنتجات مع تصنيف النتائج
     */
    fun smartSearch(
        query: String,
        products: List<Product>
    ): List<Product> {
        val queryTokens = query.lowercase().split(" ")

        return products
            .mapNotNull { product ->
                val score = calculateSearchScore(product, queryTokens)
                if (score > 0) product to score else null
            }
            .sortedByDescending { it.second }
            .map { it.first }
    }

    /**
     * حساب درجة التطابق للبحث
     */
    private fun calculateSearchScore(
        product: Product,
        queryTokens: List<String>
    ): Double {
        var score = 0.0
        val productNameLower = product.name.lowercase()
        val productDescLower = product.description.lowercase()

        for (token in queryTokens) {
            when {
                productNameLower.startsWith(token) -> score += 3.0
                productNameLower.contains(" $token ") -> score += 2.5
                productNameLower.contains(token) -> score += 2.0
                productDescLower.contains(token) -> score += 1.0
            }
        }

        // إضافة تأثير التقييم
        score += product.rating * 0.5

        // إضافة تأثير التوفر
        if (product.inStock) score += 1.0

        return score
    }

    /**
     * تجميع المنتجات المشابهة
     */
    fun clusterSimilarProducts(
        products: List<Product>
    ): Map<String, List<Product>> {
        return products.groupBy { it.category.toString() }
    }

    /**
     * إيجاد بدائل منتج معين
     */
    fun findProductAlternatives(
        product: Product,
        allProducts: List<Product>,
        maxResults: Int = 5
    ): List<Product> {
        return allProducts
            .filter { it.id != product.id && it.category == product.category }
            .sortedWith(
                compareBy<Product> { kotlin.math.abs(it.price - product.price) }
                    .thenBy { -it.rating }
            )
            .take(maxResults)
    }
}

/**
 * معايير التصفية
 */
data class ProductFilters(
    val minPrice: Double = 0.0,
    val maxPrice: Double = Double.MAX_VALUE,
    val minRating: Double = 0.0,
    val minReviews: Int = 0,
    val inStockOnly: Boolean = false,
    val categories: List<String> = emptyList(),
    val sellerIds: List<String> = emptyList(),
    val sortBy: SortType = SortType.NEWEST
)

/**
 * أنواع الترتيب
 */
enum class SortType {
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    RATING,
    NEWEST,
    MOST_POPULAR
}
