package com.agroconsult.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconsult.app.data.models.Product
import com.agroconsult.app.ml.RecommendationResult
import com.agroconsult.app.ml.RecommendationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val recommendationService: RecommendationService
) : ViewModel() {

    private val _recommendations = MutableStateFlow<List<RecommendationResult>>(emptyList())
    val recommendations: StateFlow<List<RecommendationResult>> = _recommendations

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    /**
     * تحميل التوصيات الشاملة
     */
    fun loadComprehensiveRecommendations(
        userId: String,
        user: com.agroconsult.app.data.models.User,
        allProducts: List<Product>,
        userFavorites: List<Product> = emptyList(),
        userHistory: List<Product> = emptyList()
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val recs = recommendationService.getComprehensiveRecommendations(
                    userId = userId,
                    user = user,
                    allProducts = allProducts,
                    userFavorites = userFavorites,
                    userHistory = userHistory
                )
                _recommendations.value = recs
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في تحميل التوصيات"
                _isLoading.value = false
            }
        }
    }

    /**
     * البحث الذكي
     */
    fun performSmartSearch(
        query: String,
        allProducts: List<Product>
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val searchResults = recommendationService.smartSearch(query, allProducts)
                _recommendations.value = searchResults.map { product ->
                    RecommendationResult(
                        product = product,
                        score = 0.9,
                        type = "SEARCH",
                        confidence = 0.9,
                        reason = "نتيجة بحث متطابقة"
                    )
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في البحث"
                _isLoading.value = false
            }
        }
    }
}
