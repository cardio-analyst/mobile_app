package `is`.ulstu.cardioanalyst.app

import `is`.ulstu.cardioanalyst.models.diseases.DiseasesDBRepository
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.cardioanalyst.models.diseases.sources.DiseasesSource
import `is`.ulstu.cardioanalyst.models.laboratory_research.ILaboratoryResearchRepository
import `is`.ulstu.cardioanalyst.models.laboratory_research.LaboratoryResearchDBRepository
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.LaboratoryResearchSource
import `is`.ulstu.cardioanalyst.models.lifestyle.ILifestyleRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.LifestyleDBRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.LifestyleSource
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.StenocardiaSymptomsTestRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.TreatmentAdherenceTestRepository
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

    val lifestyleSource: LifestyleSource by lazy {
        sourcesProvider.getLifestyleSource()
    }

    val laboratoryResearchSource: LaboratoryResearchSource by lazy {
        sourcesProvider.getLaboratoryResearchSource()
    }

    // --- repositories

    val userRepository: IUserRepository by lazy {
        UserDBRepository()
    }

    val diseasesRepository: IDiseasesRepository by lazy {
        DiseasesDBRepository()
    }

    val lifestyleRepository: ILifestyleRepository by lazy {
        LifestyleDBRepository()
    }

    val stenocardiaSymptomsTestRepository: StenocardiaSymptomsTestRepository by lazy {
        StenocardiaSymptomsTestRepository()
    }

    val treatmentAdherenceTestRepository: TreatmentAdherenceTestRepository by lazy {
        TreatmentAdherenceTestRepository()
    }

    val laboratoryResearchRepository: ILaboratoryResearchRepository by lazy {
        LaboratoryResearchDBRepository()
    }

    // --- context methods

    fun getString(id: Int) = appContext.resources.getString(id)

    fun getString(id: Int, vararg formatArgs: Any) =
        appContext.resources.getString(id, *formatArgs)

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}