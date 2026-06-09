package com.agroconsult.app.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class Message(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val senderImage: String = "",
    val receiverId: String = "",
    val receiverName: String = "",
    val content: String = "",
    val imageUrl: String? = null,
    val isRead: Boolean = false,
    val timestamp: Timestamp = Timestamp.now()
) : Serializable

data class Chat(
    val id: String = "",
    val userId1: String = "",
    val user1Name: String = "",
    val user1Image: String = "",
    val userId2: String = "",
    val user2Name: String = "",
    val user2Image: String = "",
    val lastMessage: String = "",
    val lastMessageTime: Timestamp = Timestamp.now(),
    val unreadCount: Int = 0,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
) : Serializable
