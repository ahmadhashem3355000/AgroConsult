package com.agroconsult.app.data.models

import com.google.firebase.Timestamp
import java.io.Serializable

data class Product(
    val id: String = "",
    val sellerId: String = "",
    val sellerName: String = "",
    val sellerImage: String = "",
    val name: String = "",
    val description: String = "",
    val category: ProductCategory = ProductCategory.VEGETABLES,
    val price: Double = 0.0,
    val quantity: Int = 0,
    val unit: String = "",
    val images: List<String> = emptyList(),
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val inStock: Boolean = true,
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
) : Serializable

enum class ProductCategory {
    VEGETABLES, FRUITS, GRAINS, SEEDS, FERTILIZERS, PESTICIDES, EQUIPMENT, TOOLS, IRRIGATION
}

data class CartItem(
    val productId: String = "",
    val product: Product? = null,
    val quantity: Int = 0,
    val addedAt: Timestamp = Timestamp.now()
) : Serializable
