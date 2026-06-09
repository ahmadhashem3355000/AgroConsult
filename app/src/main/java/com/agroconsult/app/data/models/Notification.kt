package com.agroconsult.app.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class Notification(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val message: String = "",
    val type: NotificationType = NotificationType.INFO,
    val relatedId: String? = null,
    val isRead: Boolean = false,
    val createdAt: Timestamp = Timestamp.now()
) : Serializable

enum class NotificationType {
    ORDER_CONFIRMED,
    ORDER_SHIPPED,
    ORDER_DELIVERED,
    CONSULTATION_BOOKED,
    CONSULTATION_STARTED,
    MESSAGE_RECEIVED,
    PRODUCT_AVAILABLE,
    SPECIAL_OFFER,
    SYSTEM_UPDATE,
    INFO
}
