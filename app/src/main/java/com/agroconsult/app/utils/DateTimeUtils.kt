package com.agroconsult.app.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    fun formatTime(date: Date): String {
        return timeFormat.format(date)
    }

    fun formatDateTime(date: Date): String {
        return dateTimeFormat.format(date)
    }

    fun getCurrentDate(): String {
        return formatDate(Date())
    }

    fun getCurrentDateTime(): String {
        return formatDateTime(Date())
    }

    fun parseDate(dateString: String): Date? {
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    fun parseDatetime(dateTimeString: String): Date? {
        return try {
            dateTimeFormat.parse(dateTimeString)
        } catch (e: Exception) {
            null
        }
    }

    fun getRelativeTime(date: Date): String {
        val now = Date()
        val difference = now.time - date.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "الآن"
            minutes < 60 -> "منذ $minutes دقيقة"
            hours < 24 -> "منذ $hours ساعة"
            days < 7 -> "منذ $days أيام"
            else -> formatDate(date)
        }
    }
}
