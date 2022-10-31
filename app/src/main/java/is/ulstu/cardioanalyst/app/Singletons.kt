package `is`.ulstu.cardioanalyst.app

import `is`.ulstu.cardioanalyst.models.diseases.DiseasesDBRepository
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.cardioanalyst.models.diseases.sources.DiseasesSource
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

    val diseasesSource: DiseasesSource by lazy {
        sourcesProvider.getDiseasesSource()
    }

    // --- repositories

    val userRepository: IUserRepository by lazy {
        UserDBRepository()
    }

    val diseasesRepository: IDiseasesRepository by lazy {
        DiseasesDBRepository()
    }

    // --- context methods

    fun getString(id: Int) = appContext.resources.getString(id)

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}