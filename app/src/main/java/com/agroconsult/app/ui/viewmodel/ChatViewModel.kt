package com.agroconsult.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agroconsult.app.data.models.Message
import com.agroconsult.app.data.models.Chat
import com.agroconsult.app.data.remote.FirebaseFirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val firestoreRepository: FirebaseFirestoreRepository
) : ViewModel() {

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadChats(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.getChats(userId)
                result.onSuccess { chatList ->
                    _chats.value = chatList
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل تحميل الرسائل"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = firestoreRepository.getMessages(chatId)
                result.onSuccess { messageList ->
                    _messages.value = messageList
                    _isLoading.value = false
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل تحميل الرسائل"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
                _isLoading.value = false
            }
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            try {
                val result = firestoreRepository.sendMessage(message)
                result.onSuccess { messageId ->
                    // Message sent successfully
                }.onFailure { exception ->
                    _error.value = exception.message ?: "فشل إرسال الرسالة"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "خطأ في الاتصال"
            }
        }
    }
}
