package `is`.ulstu.cardioanalyst.ui.diseases

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel

class DiseasesViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val diseasesRepository: IDiseasesRepository = Singletons.diseasesRepository
    private val appSettings = Singletons.appSettings

    // Change to safeScope
    fun getUserDiseases() =
        appSettings.getCurrentRefreshToken()?.let { diseasesRepository.getUserDiseases(it) }
            ?: mutableMapOf()

    fun setUserDiseases(diseases: Map<String, Boolean>) {
        appSettings.getCurrentRefreshToken()?.let {
            diseasesRepository.setUserDiseases(it, diseases)
            uiActions.toast(Singletons.getString(R.string.user_info_save))
        }
    }
}