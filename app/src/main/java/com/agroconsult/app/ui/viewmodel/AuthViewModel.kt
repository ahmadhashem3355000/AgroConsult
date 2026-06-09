package com.agroconsult.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconsult.app.data.models.User
import com.agroconsult.app.data.remote.FirebaseAuthRepository
import com.agroconsult.app.data.remote.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val firestoreRepository: FirebaseFirestoreRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = firebaseAuthRepository.loginUser(email, password)
                result.onSuccess { user ->
                    if (user != null) {
                        val userProfile = firestoreRepository.getUser(user.uid)
                        userProfile.onSuccess { userData ->
                            _currentUser.value = userData
                            _authState.value = AuthState.Success("تم تسجيل الدخول بنجاح")
                        }.onFailure { error ->
                            _authState.value = AuthState.Error(error.message ?: "خطأ غير معروف")
                        }
                    }
                }.onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "فشل تسجيل الدخول")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "خطأ في الاتصال")
            }
        }
    }

    fun register(user: User, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = firebaseAuthRepository.registerUser(user.email, password)
                result.onSuccess { firebaseUser ->
                    if (firebaseUser != null) {
                        val newUser = user.copy(uid = firebaseUser.uid)
                        val createResult = firestoreRepository.createUser(newUser)
                        createResult.onSuccess {
                            _currentUser.value = newUser
                            _authState.value = AuthState.Success("تم التسجيل بنجاح")
                        }.onFailure { error ->
                            _authState.value = AuthState.Error(error.message ?: "خطأ في إنشاء الحساب")
                        }
                    }
                }.onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "فشل التسجيل")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "خطأ في الاتصال")
            }
        }
    }

    fun logout() {
        firebaseAuthRepository.logoutUser()
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = firebaseAuthRepository.resetPassword(email)
                result.onSuccess {
                    _authState.value = AuthState.Success("تم إرسال بريد إعادة تعيين كلمة المرور")
                }.onFailure { error ->
                    _authState.value = AuthState.Error(error.message ?: "فشل الإرسال")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "خطأ في الاتصال")
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val error: String) : AuthState()
}
