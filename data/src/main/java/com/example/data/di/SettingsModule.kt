package com.example.data.di

import com.example.data.settings.AppSettings
import com.example.data.settings.SharedPreferencesAppSettings
import com.example.data.settings.SharedPreferencesUserSettings
import com.example.data.settings.UserSettings
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