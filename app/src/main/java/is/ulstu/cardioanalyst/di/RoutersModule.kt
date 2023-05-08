package `is`.ulstu.cardioanalyst.di

import com.example.authorization.presentation.AuthorizationRouter
import com.example.feedback.presentation.FeedbackRouter
import com.example.profile.presentation.ProfileRouter
import com.example.questionnaires_list.presentation.QuestionnaireRouter
import com.example.recommendations.presentation.RecommendationsRouter
import com.example.registration.presentation.RegistrationRouter
import com.example.report.presentation.ReportRouter
import com.example.stenocardia_symptoms.presentation.StenocardiaSymptomsRouter
import com.example.treatment_adherence.presentation.TreatmentAdherenceRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `is`.ulstu.cardioanalyst.presentation.nav_routers.*

@Module
@InstallIn(SingletonComponent::class)
abstract class RoutersModule {

    @Binds
    abstract fun bindAuthorizationRouter(
        authorizationNavigation: AuthorizationNavigation
    ): AuthorizationRouter

    @Binds
    abstract fun bindRegistrationRouter(
        registrationNavigation: RegistrationNavigation
    ): RegistrationRouter

    @Binds
    abstract fun bindProfileRouter(
        profileNavigation: ProfileNavigation
    ): ProfileRouter

    @Binds
    abstract fun bindQuestionnaireRouter(
        questionnaireNavigation: QuestionnaireNavigation
    ): QuestionnaireRouter

    @Binds
    abstract fun bindReportRouter(
        reportNavigation: ReportNavigation,
    ): ReportRouter

    @Binds
    abstract fun bindFeedbackRouter(
        feedbackNavigation: FeedbackNavigation,
    ): FeedbackRouter

    @Binds
    abstract fun bindStenocardiaSymptomsRouter(
        stenocardiaSymptomsNavigation: StenocardiaSymptomsNavigation,
    ): StenocardiaSymptomsRouter

    @Binds
    abstract fun bindTreatmentAdherenceRouter(
        treatmentAdherenceNavigation: TreatmentAdherenceNavigation,
    ): TreatmentAdherenceRouter

    @Binds
    abstract fun bindRecommendationsRouter(
        recommendationsNavigation: RecommendationsNavigation,
    ): RecommendationsRouter
}