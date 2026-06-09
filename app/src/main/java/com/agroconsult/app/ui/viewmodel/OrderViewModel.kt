package com.agroconsult.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconsult.app.data.models.Order
import com.agroconsult.app.data.models.Review
import com.agroconsult.app.data.remote.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val firestoreRepository: FirebaseFirestoreRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadOrders(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.getOrders(userId)
                result.onSuccess { orderList ->
                    _orders.value = orderList
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل تحميل الطلبات"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun createOrder(order: Order) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.createOrder(order)
                result.onSuccess { orderId ->
                    _error.value = "تم إنشاء الطلب بنجاح"
                    loadOrders(order.buyerId)
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل إنشاء الطلب"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun addReview(review: Review) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.addReview(review)
                result.onSuccess { reviewId ->
                    _error.value = "تم إضافة المراجعة بنجاح"
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل إضافة المراجعة"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }
}
