package `is`.ulstu.cardioanalyst.di

import `is`.ulstu.cardioanalyst.models.settings.AppSettings
import `is`.ulstu.cardioanalyst.models.settings.SharedPreferencesAppSettings
import `is`.ulstu.cardioanalyst.models.settings.SharedPreferencesUserSettings
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindAppSettings(
        appSettings: SharedPreferencesAppSettings
    ): AppSettings

    @Binds
    abstract fun bindUserSettings(
        userSettings: SharedPreferencesUserSettings
    ): UserSettings

}