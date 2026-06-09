package com.agroconsult.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconsult.app.data.models.Consultation
import com.agroconsult.app.data.models.User
import com.agroconsult.app.data.remote.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsultationViewModel @Inject constructor(
    private val firestoreRepository: FirebaseFirestoreRepository
) : ViewModel() {

    private val _consultants = MutableStateFlow<List<User>>(emptyList())
    val consultants: StateFlow<List<User>> = _consultants

    private val _consultations = MutableStateFlow<List<Consultation>>(emptyList())
    val consultations: StateFlow<List<Consultation>> = _consultations

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadConsultants()
    }

    fun loadConsultants() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.getAllConsultants()
                result.onSuccess { consultantList ->
                    _consultants.value = consultantList
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل تحميل الخبراء"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun loadConsultations(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.getConsultations(userId)
                result.onSuccess { consultationList ->
                    _consultations.value = consultationList
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل تحميل الاستشارات"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun bookConsultation(consultation: Consultation) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.bookConsultation(consultation)
                result.onSuccess { consultationId ->
                    _error.value = "تم حجز الاستشارة بنجاح"
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل حجز الاستشارة"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }
}
