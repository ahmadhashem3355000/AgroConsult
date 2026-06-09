package com.agroconsult.app.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.agroconsult.app.ml.RecommendationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MLModule {
    @Singleton
    @Provides
    fun provideRecommendationService(): RecommendationService {
        return RecommendationService()
    }
}
