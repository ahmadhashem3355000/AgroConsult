package com.agroconsult.app.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class Order(
    val id: String = "",
    val buyerId: String = "",
    val buyerName: String = "",
    val items: List<CartItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val status: OrderStatus = OrderStatus.PENDING,
    val deliveryAddress: String = "",
    val paymentMethod: PaymentMethod = PaymentMethod.CREDIT_CARD,
    val paymentId: String = "",
    val isPaid: Boolean = false,
    val estimatedDeliveryDate: Timestamp? = null,
    val actualDeliveryDate: Timestamp? = null,
    val notes: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
) : Serializable

enum class OrderStatus {
    PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED
}

enum class PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, WALLET, BANK_TRANSFER
}

data class Review(
    val id: String = "",
    val productId: String? = null,
    val consultantId: String? = null,
    val sellerId: String? = null,
    val userId: String = "",
    val userName: String = "",
    val rating: Double = 0.0,
    val comment: String = "",
    val images: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
) : Serializable
