package `is`.ulstu.cardioanalyst.di

import com.example.authorization.presentation.AuthorizationRouter
import com.example.profile.presentation.ProfileRouter
import com.example.questionnaires_list.presentation.QuestionnaireRouter
import com.example.registration.presentation.RegistrationRouter
import com.example.report.presentation.ReportRouter
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
}