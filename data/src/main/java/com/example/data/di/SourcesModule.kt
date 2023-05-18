package com.example.data.di

import com.example.data.repositories.basic_indicators.sources.BasicIndicatorsSource
import com.example.data.repositories.basic_indicators.sources.RetrofitBasicIndicatorsSource
import com.example.data.repositories.diseases.sources.DiseasesSource
import com.example.data.repositories.diseases.sources.RetrofitDiseasesSource
import com.example.data.repositories.feedback.sources.FeedbackSource
import com.example.data.repositories.feedback.sources.RetrofitFeedbackSource
import com.example.data.repositories.laboratory_research.sources.LaboratoryResearchSource
import com.example.data.repositories.laboratory_research.sources.RetrofitLaboratoryResearchSource
import com.example.data.repositories.lifestyle.sources.LifestyleSource
import com.example.data.repositories.lifestyle.sources.RetrofitLifestyleSource
import com.example.data.repositories.recommendations.sources.RecommendationsSource
import com.example.data.repositories.recommendations.sources.RetrofitRecommendationsSource
import com.example.data.repositories.stenocardia_symptoms_test.sources.RetrofitStenocardiaSymptomsTestSource
import com.example.data.repositories.stenocardia_symptoms_test.sources.StenocardiaSymptomsTestSource
import com.example.data.repositories.treatment_adherence_test.sources.RetrofitTreatmentAdherenceTestSource
import com.example.data.repositories.treatment_adherence_test.sources.TreatmentAdherenceTestSource
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

    @Binds
    abstract fun bindRetrofitStenocardiaSymptomsTestSource(
        retrofitStenocardiaSymptomsTestSource: RetrofitStenocardiaSymptomsTestSource
    ): StenocardiaSymptomsTestSource

    @Binds
    abstract fun bindRetrofitTreatmentAdherenceTestSource(
        retrofitTreatmentAdherenceTestSource: RetrofitTreatmentAdherenceTestSource
    ): TreatmentAdherenceTestSource

    @Binds
    abstract fun bindRetrofitFeedbackSource(
        retrofitFeedbackSource: RetrofitFeedbackSource
    ): FeedbackSource

}