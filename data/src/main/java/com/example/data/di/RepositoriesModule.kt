package com.example.data.di

import com.example.data.repositories.basic_indicators.BasicIndicatorsDataDataRepository
import com.example.data.repositories.basic_indicators.IBasicIndicatorsDataDataRepository
import com.example.data.repositories.diseases.DiseasesDataDataRepository
import com.example.data.repositories.diseases.IDiseasesDataDataRepository
import com.example.data.repositories.feedback.FeedbackDataDataRepository
import com.example.data.repositories.feedback.IFeedbackDataDataRepository
import com.example.data.repositories.laboratory_research.ILaboratoryResearchDataDataRepository
import com.example.data.repositories.laboratory_research.LaboratoryResearchDataDataRepository
import com.example.data.repositories.lifestyle.ILifestyleDataDataRepository
import com.example.data.repositories.lifestyle.LifestyleDataDataRepository
import com.example.data.repositories.recommendations.IRecommendationsDataDataRepository
import com.example.data.repositories.recommendations.RecommendationsDataDataRepository
import com.example.data.repositories.stenocardia_symptoms_test.IStenocardiaSymptomsTestDataDataRepository
import com.example.data.repositories.stenocardia_symptoms_test.StenocardiaSymptomsTestDataDataRepository
import com.example.data.repositories.treatment_adherence_test.ITreatmentAdherenceTestDataDataRepository
import com.example.data.repositories.treatment_adherence_test.TreatmentAdherenceTestDataDataRepository
import com.example.data.repositories.users.IUserDataDataRepository
import com.example.data.repositories.users.UserDataDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindBasicIndicatorsRepository(
        basicIndicatorsDBRepository: BasicIndicatorsDataDataRepository
    ): IBasicIndicatorsDataDataRepository

    @Binds
    abstract fun bindDiseasesRepository(
        diseasesDataRepository: DiseasesDataDataRepository
    ): IDiseasesDataDataRepository

    @Binds
    abstract fun bindLaboratoryResearchRepository(
        laboratoryResearchDBRepository: LaboratoryResearchDataDataRepository
    ): ILaboratoryResearchDataDataRepository

    @Binds
    abstract fun bindLifestyleRepository(
        lifestyleDBRepository: LifestyleDataDataRepository
    ): ILifestyleDataDataRepository

    @Binds
    abstract fun bindRecommendationsRepository(
        recommendationsDBRepository: RecommendationsDataDataRepository
    ): IRecommendationsDataDataRepository

    @Binds
    abstract fun bindUserRepository(
        userDBRepository: UserDataDataRepository
    ): IUserDataDataRepository

    @Binds
    abstract fun bindFeedbackDataRepository(
        feedbackDataRepository: FeedbackDataDataRepository
    ): IFeedbackDataDataRepository

    @Binds
    abstract fun bindStenocardiaSymptomsTestDataRepository(
        stenocardiaSymptomsTestDataRepository: StenocardiaSymptomsTestDataDataRepository
    ): IStenocardiaSymptomsTestDataDataRepository

    @Binds
    abstract fun bindTreatmentAdherenceTestDataRepository(
        treatmentAdherenceTestDataRepository: TreatmentAdherenceTestDataDataRepository
    ): ITreatmentAdherenceTestDataDataRepository

}