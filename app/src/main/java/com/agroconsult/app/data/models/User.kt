package com.agroconsult.app.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class User(
    val uid: String = "",
    val email: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val userType: UserType = UserType.FARMER,
    val profileImage: String = "",
    val bio: String = "",
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val isVerified: Boolean = false,
    val specializations: List<String> = emptyList(),
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
    val isActive: Boolean = true
) : Serializable

enum class UserType {
    FARMER, CONSULTANT, SELLER
}

data class ConsultationExpertise(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val icon: String = ""
)
