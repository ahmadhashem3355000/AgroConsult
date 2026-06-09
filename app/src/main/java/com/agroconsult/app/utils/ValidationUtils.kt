package com.agroconsult.app.utils

import android.util.Patterns

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidPhoneNumber(phone: String): Boolean {
        return phone.isNotEmpty() && phone.length >= 10 && phone.all { it.isDigit() }
    }

    fun isValidFullName(name: String): Boolean {
        return name.isNotEmpty() && name.length >= 3 && name.contains(" ").not()
    }

    fun isValidPrice(price: String): Boolean {
        return try {
            price.toDouble() > 0
        } catch (e: Exception) {
            false
        }
    }

    fun isValidQuantity(quantity: String): Boolean {
        return try {
            quantity.toInt() > 0
        } catch (e: Exception) {
            false
        }
    }

    fun validateEmail(email: String): String? {
        return when {
            email.isEmpty() -> "البريد الإلكتروني مطلوب"
            !isValidEmail(email) -> "البريد الإلكتروني غير صحيح"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return when {
            password.isEmpty() -> "كلمة المرور مطلوبة"
            !isValidPassword(password) -> "كلمة المرور يجب أن تكون 6 أحرف على الأقل"
            else -> null
        }
    }

    fun validatePhoneNumber(phone: String): String? {
        return when {
            phone.isEmpty() -> "رقم الهاتف مطلوب"
            !isValidPhoneNumber(phone) -> "رقم الهاتف غير صحيح"
            else -> null
        }
    }
}
