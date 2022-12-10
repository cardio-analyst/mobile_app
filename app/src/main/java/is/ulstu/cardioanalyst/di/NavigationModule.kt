package `is`.ulstu.cardioanalyst.di

import `is`.ulstu.foundation.navigator.IntermediateNavigator
import `is`.ulstu.foundation.navigator.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun bindNavigator(
        intermediateNavigator: IntermediateNavigator
    ): Navigator

}