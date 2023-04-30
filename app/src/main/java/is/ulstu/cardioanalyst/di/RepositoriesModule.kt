package `is`.ulstu.cardioanalyst.di

import com.example.authorization.domain.UserSignInRepository
import com.example.basic_indicators.domain.BasicIndicatorsRepository
import com.example.diseases.domain.DiseasesRepository
import com.example.feedback.domain.FeedbackRepository
import com.example.laboratory_research.domain.LaboratoryResearchRepository
import com.example.lifestyle.domain.LifestyleRepository
import com.example.profile.domain.UserInfoRepository
import com.example.questionnaires_list.domain.StenocardiaSymptomsTestInfoRepository
import com.example.questionnaires_list.domain.TreatmentAdherenceTestInfoRepository
import com.example.recommendations.domain.RecommendationsRepository
import com.example.registration.domain.UserSignUpRepository
import com.example.report.domain.ReportRepository
import com.example.stenocardia_symptoms.domain.StenocardiaSymptomsTestRepository
import com.example.treatment_adherence.domain.TreatmentAdherenceTestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `is`.ulstu.cardioanalyst.glue.basic_indicators.AdapterBasicIndicatorsRepository
import `is`.ulstu.cardioanalyst.glue.diseases.AdapterDiseasesRepository
import `is`.ulstu.cardioanalyst.glue.feedback.AdapterFeedbackRepository
import `is`.ulstu.cardioanalyst.glue.laboratory_research.AdapterLaboratoryResearchRepository
import `is`.ulstu.cardioanalyst.glue.lifestyle.AdapterLifestyleRepository
import `is`.ulstu.cardioanalyst.glue.recommendations.AdapterRecommendationsRepository
import `is`.ulstu.cardioanalyst.glue.report.AdapterReportRepository
import `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test.AdapterStenocardiaSymptomsTestInfoRepository
import `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test.AdapterStenocardiaSymptomsTestRepository
import `is`.ulstu.cardioanalyst.glue.treatment_adherence_test.AdapterTreatmentAdherenceTestInfoRepository
import `is`.ulstu.cardioanalyst.glue.treatment_adherence_test.AdapterTreatmentAdherenceTestRepository
import `is`.ulstu.cardioanalyst.glue.user.AdapterUserInfoRepository
import `is`.ulstu.cardioanalyst.glue.user.AdapterUserSignInRepository
import `is`.ulstu.cardioanalyst.glue.user.AdapterUserSignUpRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindUserSignInRepository(
        adapterUserSignInRepository: AdapterUserSignInRepository
    ): UserSignInRepository

    @Binds
    abstract fun bindUserSignUpRepository(
        adapterUserSignUpRepository: AdapterUserSignUpRepository
    ): UserSignUpRepository

    @Binds
    abstract fun bindUserInfoRepository(
        adapterUserInfoRepository: AdapterUserInfoRepository
    ): UserInfoRepository

    @Binds
    abstract fun bindRecommendationsRepository(
        adapterRecommendationsRepository: AdapterRecommendationsRepository
    ): RecommendationsRepository

    @Binds
    abstract fun bindDiseasesRepository(
        adapterDiseasesRepository: AdapterDiseasesRepository
    ): DiseasesRepository

    @Binds
    abstract fun bindLaboratoryResearchRepository(
        adapterLaboratoryResearchRepository: AdapterLaboratoryResearchRepository
    ): LaboratoryResearchRepository

    @Binds
    abstract fun bindBasicIndicatorsRepository(
        adapterBasicIndicatorsRepository: AdapterBasicIndicatorsRepository
    ): BasicIndicatorsRepository

    @Binds
    abstract fun bindReportRepository(
        adapterReportRepository: AdapterReportRepository
    ): ReportRepository

    @Binds
    abstract fun bindFeedbackRepository(
        adapterFeedbackRepository: AdapterFeedbackRepository
    ): FeedbackRepository

    @Binds
    abstract fun bindLifestyleRepository(
        adapterLifestyleRepository: AdapterLifestyleRepository
    ): LifestyleRepository

    @Binds
    abstract fun bindStenocardiaSymptomsTestInfoRepository(
        adapterStenocardiaSymptomsTestInfoRepository: AdapterStenocardiaSymptomsTestInfoRepository
    ): StenocardiaSymptomsTestInfoRepository

    @Binds
    abstract fun bindStenocardiaSymptomsTestRepository(
        adapterStenocardiaSymptomsTestRepository: AdapterStenocardiaSymptomsTestRepository
    ): StenocardiaSymptomsTestRepository

    @Binds
    abstract fun bindTreatmentAdherenceTestInfoRepository(
        adapterTreatmentAdherenceTestInfoRepository: AdapterTreatmentAdherenceTestInfoRepository
    ): TreatmentAdherenceTestInfoRepository

    @Binds
    abstract fun bindTreatmentAdherenceTestRepository(
        adapterTreatmentAdherenceTestRepository: AdapterTreatmentAdherenceTestRepository
    ): TreatmentAdherenceTestRepository
}