package `is`.ulstu.cardioanalyst.di

import com.example.presentation.uiactions.AndroidUiAction
import com.example.presentation.uiactions.UiAction
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UiActionsModule {

    @Binds
    abstract fun bindUiActions(
        androidUiActions: AndroidUiAction
    ): UiAction

}