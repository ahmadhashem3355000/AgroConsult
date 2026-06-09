package com.agroconsult.app.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class Consultation(
    val id: String = "",
    val consultantId: String = "",
    val consultantName: String = "",
    val consultantImage: String = "",
    val farmerId: String = "",
    val farmerName: String = "",
    val type: ConsultationType = ConsultationType.TEXT,
    val topic: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val duration: Int = 30, // in minutes
    val status: ConsultationStatus = ConsultationStatus.PENDING,
    val scheduledTime: Timestamp? = null,
    val startTime: Timestamp? = null,
    val endTime: Timestamp? = null,
    val notes: String = "",
    val rating: Double = 0.0,
    val review: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
) : Serializable

enum class ConsultationType {
    TEXT, VIDEO, AUDIO
}

enum class ConsultationStatus {
    PENDING, CONFIRMED, IN_PROGRESS, COMPLETED, CANCELLED
}

data class ConsultationSlot(
    val id: String = "",
    val consultantId: String = "",
    val dayOfWeek: Int = 0, // 0 = Sunday, 6 = Saturday
    val startTime: String = "",
    val endTime: String = "",
    val isAvailable: Boolean = true
) : Serializable
