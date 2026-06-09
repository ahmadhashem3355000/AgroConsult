package com.agroconsult.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconsult.app.data.models.Product
import com.agroconsult.app.data.remote.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val firestoreRepository: FirebaseFirestoreRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    init {
        loadProducts()
    }

    fun loadProducts(category: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.getAllProducts(category)
                result.onSuccess { productList ->
                    _products.value = productList
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل تحميل المنتجات"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.searchProducts(query)
                result.onSuccess { productList ->
                    _products.value = productList
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل البحث"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.addProduct(product)
                result.onSuccess { productId ->
                    loadProducts()
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل إضافة المنتج"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
        loadProducts(category)
    }
}
