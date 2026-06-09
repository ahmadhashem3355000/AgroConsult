package com.agroconsult.app.data.remote

import com.agroconsult.app.data.models.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class FirebaseFirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // User Operations
    suspend fun createUser(user: User): Result<Unit> {
        return try {
            firestore.collection("users").document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(userId: String): Result<User?> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            Result.success(document.toObject(User::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(userId: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            firestore.collection("users").document(userId).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Product Operations
    suspend fun getAllProducts(category: String? = null, limit: Long = 20): Result<List<Product>> {
        return try {
            val query: Query = if (category != null) {
                firestore.collection("products")
                    .whereEqualTo("category", category)
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .limit(limit)
            } else {
                firestore.collection("products")
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .limit(limit)
            }
            val documents = query.get().await()
            val products = documents.toObjects(Product::class.java)
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchProducts(query: String): Result<List<Product>> {
        return try {
            // Firestore doesn't support LIKE queries, so we fetch and filter
            val documents = firestore.collection("products").get().await()
            val products = documents.toObjects(Product::class.java)
            val filtered = products.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
            Result.success(filtered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addProduct(product: Product): Result<String> {
        return try {
            val docRef = firestore.collection("products").add(product).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Consultation Operations
    suspend fun getAllConsultants(): Result<List<User>> {
        return try {
            val documents = firestore.collection("users")
                .whereEqualTo("userType", "CONSULTANT")
                .get().await()
            val consultants = documents.toObjects(User::class.java)
            Result.success(consultants)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun bookConsultation(consultation: Consultation): Result<String> {
        return try {
            val docRef = firestore.collection("consultations").add(consultation).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConsultations(userId: String): Result<List<Consultation>> {
        return try {
            val documents = firestore.collection("consultations")
                .whereEqualTo("farmerId", userId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get().await()
            val consultations = documents.toObjects(Consultation::class.java)
            Result.success(consultations)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Order Operations
    suspend fun createOrder(order: Order): Result<String> {
        return try {
            val docRef = firestore.collection("orders").add(order).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrders(userId: String): Result<List<Order>> {
        return try {
            val documents = firestore.collection("orders")
                .whereEqualTo("buyerId", userId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get().await()
            val orders = documents.toObjects(Order::class.java)
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Review Operations
    suspend fun addReview(review: Review): Result<String> {
        return try {
            val docRef = firestore.collection("reviews").add(review).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReviews(productId: String? = null, consultantId: String? = null): Result<List<Review>> {
        return try {
            val query: Query = when {
                productId != null -> firestore.collection("reviews")
                    .whereEqualTo("productId", productId)
                consultantId != null -> firestore.collection("reviews")
                    .whereEqualTo("consultantId", consultantId)
                else -> firestore.collection("reviews")
            }
            val documents = query.get().await()
            val reviews = documents.toObjects(Review::class.java)
            Result.success(reviews)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Message Operations
    suspend fun sendMessage(message: Message): Result<String> {
        return try {
            val docRef = firestore.collection("messages").add(message).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMessages(chatId: String): Result<List<Message>> {
        return try {
            val documents = firestore.collection("messages")
                .whereEqualTo("chatId", chatId)
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .get().await()
            val messages = documents.toObjects(Message::class.java)
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getChats(userId: String): Result<List<Chat>> {
        return try {
            val documents = firestore.collection("chats")
                .orderBy("updatedAt", Query.Direction.DESCENDING)
                .get().await()
            val chats = documents.toObjects(Chat::class.java)
                .filter { it.userId1 == userId || it.userId2 == userId }
            Result.success(chats)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
