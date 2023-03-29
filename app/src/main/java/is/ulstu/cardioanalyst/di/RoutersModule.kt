package `is`.ulstu.cardioanalyst.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `is`.ulstu.cardioanalyst.presentation.nav_routers.AuthorizationNavigation
import com.example.authorization.presentation.AuthorizationRouter

@Module
@InstallIn(SingletonComponent::class)
abstract class RoutersModule {

    @Binds
    abstract fun bindAuthorizationRouter(
        authorizationNavigation: AuthorizationNavigation
    ): AuthorizationRouter
}