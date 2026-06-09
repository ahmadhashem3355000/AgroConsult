package com.agroconsult.app.utils

object Constants {
    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_PRODUCTS = "products"
    const val COLLECTION_CONSULTATIONS = "consultations"
    const val COLLECTION_ORDERS = "orders"
    const val COLLECTION_REVIEWS = "reviews"
    const val COLLECTION_MESSAGES = "messages"
    const val COLLECTION_CHATS = "chats"
    const val COLLECTION_NOTIFICATIONS = "notifications"

    // User Types
    const val USER_TYPE_FARMER = "FARMER"
    const val USER_TYPE_CONSULTANT = "CONSULTANT"
    const val USER_TYPE_SELLER = "SELLER"

    // Order Status
    const val ORDER_STATUS_PENDING = "PENDING"
    const val ORDER_STATUS_CONFIRMED = "CONFIRMED"
    const val ORDER_STATUS_PROCESSING = "PROCESSING"
    const val ORDER_STATUS_SHIPPED = "SHIPPED"
    const val ORDER_STATUS_DELIVERED = "DELIVERED"

    // Consultation Status
    const val CONSULTATION_STATUS_PENDING = "PENDING"
    const val CONSULTATION_STATUS_CONFIRMED = "CONFIRMED"
    const val CONSULTATION_STATUS_IN_PROGRESS = "IN_PROGRESS"
    const val CONSULTATION_STATUS_COMPLETED = "COMPLETED"

    // Notification Types
    const val NOTIFICATION_ORDER_CONFIRMED = "ORDER_CONFIRMED"
    const val NOTIFICATION_CONSULTATION_BOOKED = "CONSULTATION_BOOKED"
    const val NOTIFICATION_MESSAGE_RECEIVED = "MESSAGE_RECEIVED"

    // API Timeouts
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
}
