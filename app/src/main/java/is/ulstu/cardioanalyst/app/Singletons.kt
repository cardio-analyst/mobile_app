package `is`.ulstu.cardioanalyst.app

import `is`.ulstu.cardioanalyst.models.diseases.DiseasesRAMRepository
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.cardioanalyst.models.settings.AppSettings
import `is`.ulstu.cardioanalyst.models.settings.SharedPreferencesAppSettings
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.models.users.UserDBRepository
import `is`.ulstu.cardioanalyst.models.users.sources.UsersSource
import `is`.ulstu.cardioanalyst.sources.SourceProviderHolder.sourcesProvider
import android.content.Context

object Singletons {
    private lateinit var appContext: Context

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // --- sources

    val usersSource: UsersSource by lazy {
        sourcesProvider.getUsersSource()
    }

    // --- repositories

    val userRepository: IUserRepository by lazy {
        UserDBRepository()
    }

    val diseasesRepository: IDiseasesRepository by lazy {
        DiseasesRAMRepository()
    }

    // --- context methods

    fun getString(id: Int) = appContext.resources.getString(id)

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}