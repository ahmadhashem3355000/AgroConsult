# نماذج التعلم الآلي - التوثيق الشامل

## نظرة عامة

يحتوي التطبيق على نظام متقدم للتعلم الآلي يوفر توصيات ذكية ومخصصة للمستخدمين.

## المكونات الرئيسية

### 1. RecommendationEngine

**الوظيفة**: محرك التوصيات الأساسي

#### الخوارزميات المستخدمة:

**أ) التصفية القائمة على المحتوى (Content-Based Filtering)**
```kotlin
getContentBasedRecommendations(
    userFavoriteProduct,
    allProducts,
    topN = 5
)
```
- تحسب التشابه بين المنتجات
- تستخدم مسافة Cosine
- توصي بمنتجات مشابهة للمفضلة

**ب) التصفية التعاونية (Collaborative Filtering)**
```kotlin
getCollaborativeRecommendations(
    userId,
    userRatings,
    allUserRatings,
    allProducts
)
```
- تجد مستخدمين متشابهين
- توصي بناءً على تقييمات مشابهة
- تتعامل مع عدم توفر بيانات

**ج) نموذج هجين (Hybrid Model)**
```kotlin
getHybridRecommendations(
    userId,
    userFavoriteProducts,
    userRatings,
    allUserRatings,
    allProducts,
    contentWeight = 0.4,
    collaborativeWeight = 0.6
)
```
- يدمج المحتوى والتصفية التعاونية
- يعطي أوزان مختلفة لكل استراتيجية
- أفضل النتائج

#### خوارزميات إضافية:

```kotlin
// المنتجات الاتجاهية
getTrendingProducts(allProducts, topN = 10)

// أفضل الأسعار
getBestPriceProducts(allProducts, category, topN = 10)

// الأعلى تقييماً
getTopRatedProducts(allProducts, minReviews = 5, topN = 10)

// التنبؤ بالتقييم
predictRating(userId, productId, userRatings, allUserRatings, product)
```

### 2. UserBehaviorPredictor

**الوظيفة**: التنبؤ بسلوك المستخدم

#### الخوارزميات:

**أ) Logistic Regression**
```kotlin
val probability = predictPurchaseProbability(
    user,
    product,
    userHistory
)
```
- تنبؤ احتمالي بالشراء (0-1)
- يأخذ في الاعتبار:
  - تقييم المنتج
  - السعر
  - عدد التقييمات
  - فئة المنتج
  - التوفر

**ب) تحليل الفئة المفضلة**
```kotlin
val bestCategory = predictBestCategory(
    user,
    allCategories,
    userHistory
)
```
- تحديد الفئة الأفضل للمستخدم
- بناءً على السجل

**ج) درجة الثقة**
```kotlin
val confidence = calculateConfidence(
    probability,
    userHistorySize
)
```
- تزيد مع عدد الشراءات السابقة

### 3. AdvancedFilteringEngine

**الوظيفة**: تصفية وبحث متقدم

#### المميزات:

**أ) التصفية المتقدمة**
```kotlin
filterProducts(products, ProductFilters(
    minPrice = 0.0,
    maxPrice = 1000.0,
    minRating = 3.0,
    minReviews = 5,
    inStockOnly = true,
    categories = listOf("VEGETABLES"),
    sortBy = SortType.PRICE_LOW_TO_HIGH
))
```

**أنواع الترتيب:**
- `PRICE_LOW_TO_HIGH` - الأرخص أولاً
- `PRICE_HIGH_TO_LOW` - الأغلى أولاً
- `RATING` - الأعلى تقييماً
- `NEWEST` - الأحدث أولاً
- `MOST_POPULAR` - الأكثر شهرة

**ب) البحث الذكي**
```kotlin
smartSearch("بذور الطماطم", products)
```
- يعطي نقاط للتطابق:
  - البداية: +3.0
  - داخل كلمة: +2.0-2.5
  - في الوصف: +1.0
- ينظر في التقييم والتوفر

**ج) تجميع المنتجات**
```kotlin
clusterSimilarProducts(products)
// عودة: Map<Category, List<Products>>
```

**د) البدائل**
```kotlin
findProductAlternatives(product, allProducts, maxResults = 5)
```
- منتجات مشابهة بأسعار قريبة

### 4. TrendAnalysisEngine

**الوظيفة**: تحليل الاتجاهات والأنماط

#### المميزات:

**أ) درجة الاتجاه**
```kotlin
val score = calculateTrendScore(product, historicalData)
```
- يحسب معدل النمو
- يحلل متوسط التقييم
- درجة بين 0-1

**ب) توقعات الطلب**
```kotlin
val forecast = forecastDemand(product, historicalData)
// DemandForecast(
//   estimatedDemand = 45.5,
//   confidence = 0.85,
//   trend = "INCREASING"
// )
```

**ج) تحليل الموسمية**
```kotlin
identifySeasonality(products, historicalData)
// Map<Category, SeasonalityPattern>
```
- أوقات الذروة
- أوقات الركود
- مؤشر الموسمية

**د) الفئات الاتجاهية**
```kotlin
getTrendingCategories(products, historicalData)
// List<Pair<Category, TrendScore>>
```

### 5. RecommendationService

**الوظيفة**: خدمة متكاملة للتوصيات

#### الميزات:

```kotlin
getComprehensiveRecommendations(
    userId,
    user,
    allProducts,
    userFavorites,
    userHistory,
    userRatings,
    allUserRatings,
    topN = 10
)
```

ترجع قائمة `RecommendationResult` تحتوي على:
- `product` - المنتج
- `score` - درجة التوصية (0-1)
- `type` - نوع التوصية (HYBRID, TRENDING, BEST_PRICE)
- `confidence` - درجة الثقة
- `reason` - السبب بالعربية

## مثال الاستخدام

### في ViewModel:

```kotlin
@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val recommendationService: RecommendationService
) : ViewModel() {

    fun loadRecommendations() {
        viewModelScope.launch {
            val recommendations = recommendationService.getComprehensiveRecommendations(
                userId = "user123",
                user = currentUser,
                allProducts = allProducts,
                userFavorites = favorites,
                userHistory = purchaseHistory
            )
            _recommendations.value = recommendations
        }
    }
}
```

### في UI:

```kotlin
@Composable
fun RecommendationsScreen(viewModel: RecommendationViewModel) {
    val recommendations by viewModel.recommendations.collectAsState()

    LazyColumn {
        items(recommendations) { rec ->
            RecommendationCard(
                product = rec.product,
                score = rec.score,
                reason = rec.reason,
                confidence = rec.confidence
            )
        }
    }
}
```

## المعادلات الرياضية

### Cosine Similarity
```
similarity = (A · B) / (||A|| × ||B||)
```

### Logistic Regression
```
P(y=1|x) = 1 / (1 + e^(-z))
```

### Trend Score
```
trend_score = 0.6 × tanh(growth_rate) + 0.4 × (avg_rating / 5.0)
```

## أمثلة عملية

### 1. توصيات للمزارع الجديد
```kotlin
// بدون سجل شراء - نركز على المنتجات الاتجاهية
val newFarmerRecs = recommendationService.getComprehensiveRecommendations(
    userId = "new_user",
    user = newFarmer,
    allProducts = allProducts
    // بقية المعاملات اختياري
)
```

### 2. توصيات لمزارع متقدم
```kotlin
// لديه سجل شراء - نستخدم كل الخوارزميات
val advancedRecs = recommendationService.getComprehensiveRecommendations(
    userId = userId,
    user = user,
    allProducts = allProducts,
    userFavorites = user.favoriteProducts,
    userHistory = user.purchaseHistory,
    userRatings = user.ratings,
    allUserRatings = systemRatings
)
```

### 3. بحث ذكي
```kotlin
val results = recommendationService.smartSearch(
    query = "بذور خضراء رخيصة",
    allProducts = allProducts
)
// يعود بأفضل النتائج المرتبطة
```

## التحسينات المستقبلية

1. **Deep Learning** - استخدام Neural Networks
2. **Context-Aware** - أخذ الموقع والوقت بعين الاعتبار
3. **Real-time Learning** - تحديث النموذج بناءً على التفاعلات
4. **Explainable AI** - شرح التوصيات بشكل أفضل
5. **A/B Testing** - اختبار استراتيجيات مختلفة

## الملاحظات

- جميع الخوارزميات محسنة للأداء
- تدعم البيانات الناقصة
- معايرة تلقائية للمدخلات
- توثيق شامل بالعربية

---

**آخر تحديث**: 2025-06-09
**الإصدار**: 1.0.0
