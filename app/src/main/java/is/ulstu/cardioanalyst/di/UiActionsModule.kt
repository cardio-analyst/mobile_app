package `is`.ulstu.cardioanalyst.di

import `is`.ulstu.foundation.uiactions.AndroidUiActions
import `is`.ulstu.foundation.uiactions.UiActions
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UiActionsModule {

    @Binds
    abstract fun bindUiActions(
        androidUiActions: AndroidUiActions
    ): UiActions

}