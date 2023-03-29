package `is`.ulstu.cardioanalyst.di

import com.example.authorization.presentation.AuthorizationRouter
import com.example.registration.presentation.RegistrationRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `is`.ulstu.cardioanalyst.presentation.nav_routers.AuthorizationNavigation
import `is`.ulstu.cardioanalyst.presentation.nav_routers.RegistrationNavigation

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
}