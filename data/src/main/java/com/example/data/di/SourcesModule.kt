package com.example.data.di

import com.example.data.repositories.basic_indicators.sources.BasicIndicatorsSource
import com.example.data.repositories.basic_indicators.sources.RetrofitBasicIndicatorsSource
import com.example.data.repositories.diseases.sources.DiseasesSource
import com.example.data.repositories.diseases.sources.RetrofitDiseasesSource
import com.example.data.repositories.laboratory_research.sources.LaboratoryResearchSource
import com.example.data.repositories.laboratory_research.sources.RetrofitLaboratoryResearchSource
import com.example.data.repositories.lifestyle.sources.LifestyleSource
import com.example.data.repositories.lifestyle.sources.RetrofitLifestyleSource
import com.example.data.repositories.recommendations.sources.RecommendationsSource
import com.example.data.repositories.recommendations.sources.RetrofitRecommendationsSource
import com.example.data.repositories.users.sources.RetrofitUsersSource
import com.example.data.repositories.users.sources.UsersSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindBasicIndicatorsSource(
        retrofitBasicIndicatorsSource: RetrofitBasicIndicatorsSource
    ): BasicIndicatorsSource

    @Binds
    abstract fun bindRetrofitDiseasesSource(
        retrofitDiseasesSource: RetrofitDiseasesSource
    ): DiseasesSource

    @Binds
    abstract fun bindRetrofitLaboratoryResearchSource(
        retrofitLaboratoryResearchSource: RetrofitLaboratoryResearchSource
    ): LaboratoryResearchSource

    @Binds
    abstract fun bindRetrofitLifestyleSource(
        retrofitLifestyleSource: RetrofitLifestyleSource
    ): LifestyleSource

    @Binds
    abstract fun bindRetrofitRecommendationsSource(
        retrofitRecommendationsSource: RetrofitRecommendationsSource
    ): RecommendationsSource

    @Binds
    abstract fun bindRetrofitUsersSource(
        retrofitUsersSource: RetrofitUsersSource
    ): UsersSource

}