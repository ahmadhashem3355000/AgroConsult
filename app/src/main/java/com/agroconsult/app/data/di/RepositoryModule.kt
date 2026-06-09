package com.agroconsult.app.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.agroconsult.app.data.remote.FirebaseAuthRepository
import com.agroconsult.app.data.remote.FirebaseFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFirebaseAuthRepository(firebaseAuth: FirebaseAuth): FirebaseAuthRepository {
        return FirebaseAuthRepository(firebaseAuth)
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestoreRepository(firebaseFirestore: FirebaseFirestore): FirebaseFirestoreRepository {
        return FirebaseFirestoreRepository(firebaseFirestore)
    }
}
