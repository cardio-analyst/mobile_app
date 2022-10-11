package `is`.ulstu.cardioanalyst.ui.diseases

import `is`.ulstu.cardioanalyst.App
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel

class DiseasesViewModel(
    private val uiActions: UiActions,
    private val userRepository: IUserRepository,
    private val diseasesRepository: IDiseasesRepository
) : BaseViewModel() {
    fun getUserDiseases() = diseasesRepository.getUserDiseases(userRepository.getCurrentUserToken())

    fun setUserDiseases(diseases: Map<String, Boolean>) {
        diseasesRepository.setUserDiseases(userRepository.getCurrentUserToken(), diseases)
        uiActions.toast(App.appResources.getString(R.string.user_info_save))
    }
}