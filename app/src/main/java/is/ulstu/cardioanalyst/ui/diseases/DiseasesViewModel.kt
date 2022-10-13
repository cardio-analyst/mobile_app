package `is`.ulstu.cardioanalyst.ui.diseases

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel

class DiseasesViewModel(
    private val uiActions: UiActions,
) : BaseViewModel() {

    private val diseasesRepository: IDiseasesRepository = Singletons.diseasesRepository
    private val appSettings = Singletons.appSettings

    // Change to safeScope
    fun getUserDiseases() =
        appSettings.getCurrentToken()?.let { diseasesRepository.getUserDiseases(it) }
            ?: mutableMapOf()

    fun setUserDiseases(diseases: Map<String, Boolean>) {
        appSettings.getCurrentToken()?.let {
            diseasesRepository.setUserDiseases(it, diseases)
            uiActions.toast(Singletons.getString(R.string.user_info_save))
        }
    }
}