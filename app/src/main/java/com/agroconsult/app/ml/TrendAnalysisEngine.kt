package com.agroconsult.app.ml

import com.agroconsult.app.data.models.Product
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * محرك تحليل الاتجاهات والأنماط
 * يحلل سلوك المستخدمين والاتجاهات السوقية
 */
class TrendAnalysisEngine {

    /**
     * حساب النقاط الاتجاهية للمنتج
     */
    fun calculateTrendScore(
        product: Product,
        historicalData: List<ProductHistorical>
    ): Double {
        if (historicalData.isEmpty()) return 0.5

        // الحصول على بيانات آخر 30 يوم
        val recentData = historicalData
            .filter {
                ChronoUnit.DAYS.between(it.date, LocalDateTime.now()) <= 30
            }
            .sortedBy { it.date }

        if (recentData.size < 2) return 0.5

        // حساب معدل النمو
        val oldSalesCount = recentData.take(recentData.size / 2).sumOf { it.salesCount }
        val newSalesCount = recentData.takeLast(recentData.size / 2).sumOf { it.salesCount }

        val growthRate = if (oldSalesCount > 0) {
            (newSalesCount - oldSalesCount) / oldSalesCount.toDouble()
        } else {
            0.0
        }

        // حساب متوسط التقييم
        val avgRatingTrend = recentData.map { it.avgRating }.average()

        // الدرجة النهائية
        return (0.6 * kotlin.math.tanh(growthRate)) + (0.4 * (avgRatingTrend / 5.0))
    }

    /**
     * التنبؤ بالطلب على المنتج
     */
    fun forecastDemand(
        product: Product,
        historicalData: List<ProductHistorical>
    ): DemandForecast {
        if (historicalData.size < 7) {
            return DemandForecast(
                estimatedDemand = product.reviewCount.toDouble(),
                confidence = 0.3,
                trend = "NEUTRAL"
            )
        }

        val sortedData = historicalData.sortedBy { it.date }
        val recent7Days = sortedData.takeLast(7).map { it.salesCount.toDouble() }
        val avgDemand = recent7Days.average()
        val trend = calculateTrend(recent7Days)

        return DemandForecast(
            estimatedDemand = avgDemand,
            confidence = kotlin.math.min(historicalData.size.toDouble() / 30.0, 1.0),
            trend = trend
        )
    }

    /**
     * حساب اتجاه الطلب
     */
    private fun calculateTrend(data: List<Double>): String {
        if (data.size < 2) return "NEUTRAL"

        var upCount = 0
        var downCount = 0

        for (i in 1 until data.size) {
            when {
                data[i] > data[i - 1] -> upCount++
                data[i] < data[i - 1] -> downCount++
            }
        }

        return when {
            upCount > downCount -> "INCREASING"
            downCount > upCount -> "DECREASING"
            else -> "NEUTRAL"
        }
    }

    /**
     * تحديد أوقات الذروة والركود
     */
    fun identifySeasonality(
        products: List<Product>,
        historicalData: List<ProductHistorical>
    ): Map<String, SeasonalityPattern> {
        return products
            .groupBy { it.category.toString() }
            .mapValues { (category, categoryProducts) ->
                val categoryData = historicalData
                    .filter { historical ->
                        categoryProducts.any { it.id == historical.productId }
                    }

                analyzeSeasonalPattern(categoryData)
            }
    }

    /**
     * تحليل النمط الموسمي
     */
    private fun analyzeSeasonalPattern(
        data: List<ProductHistorical>
    ): SeasonalityPattern {
        if (data.isEmpty()) {
            return SeasonalityPattern(
                peakMonths = emptyList(),
                lowMonths = emptyList(),
                seasonalityIndex = 0.0
            )
        }

        val monthlyData = data.groupBy { it.date.monthValue }
        val monthlyAverages = monthlyData.mapValues { (_, dayData) ->
            dayData.sumOf { it.salesCount } / dayData.size.toDouble()
        }

        val overallAverage = monthlyAverages.values.average()
        val peakMonths = monthlyAverages
            .filter { it.value > overallAverage * 1.2 }
            .keys.toList()
        val lowMonths = monthlyAverages
            .filter { it.value < overallAverage * 0.8 }
            .keys.toList()

        val variance = monthlyAverages.values
            .sumOf { (it - overallAverage).pow(2) } / monthlyAverages.size
        val seasonalityIndex = kotlin.math.sqrt(variance) / overallAverage

        return SeasonalityPattern(
            peakMonths = peakMonths,
            lowMonths = lowMonths,
            seasonalityIndex = seasonalityIndex
        )
    }

    /**
     * الحصول على الفئات الاتجاهية
     */
    fun getTrendingCategories(
        products: List<Product>,
        historicalData: List<ProductHistorical>
    ): List<Pair<String, Double>> {
        return products
            .groupBy { it.category.toString() }
            .mapValues { (_, categoryProducts) ->
                val categoryData = historicalData
                    .filter { historical ->
                        categoryProducts.any { it.id == historical.productId }
                    }
                calculateTrendScore(categoryProducts[0], categoryData)
            }
            .toList()
            .sortedByDescending { it.second }
    }
}

/**
 * بيانات تاريخية للمنتج
 */
data class ProductHistorical(
    val productId: String,
    val date: LocalDateTime,
    val salesCount: Int,
    val avgRating: Double,
    val reviewCount: Int
)

/**
 * توقعات الطلب
 */
data class DemandForecast(
    val estimatedDemand: Double,
    val confidence: Double,
    val trend: String // INCREASING, DECREASING, NEUTRAL
)

/**
 * نمط الموسمية
 */
data class SeasonalityPattern(
    val peakMonths: List<Int>,
    val lowMonths: List<Int>,
    val seasonalityIndex: Double
)

private fun Double.pow(n: Int): Double {
    return this.let { value ->
        var result = 1.0
        repeat(n) { result *= value }
        result
    }
}
