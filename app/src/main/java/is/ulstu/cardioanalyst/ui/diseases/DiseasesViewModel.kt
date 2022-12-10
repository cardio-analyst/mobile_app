package `is`.ulstu.cardioanalyst.ui.diseases

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.SingleLiveEvent
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiseasesViewModel @Inject constructor(
    navigator: Navigator,
    userSettings: UserSettings,
    private val uiActions: UiActions,
    private val diseasesRepository: IDiseasesRepository,
) : BaseViewModel(navigator, userSettings, uiActions) {

    private val _diseases = SingleLiveEvent<Result<DiseasesMainEntity>>()
    val diseases = _diseases.share()

    private val _diseasesSave = SingleLiveEvent<Result<DiseasesMainEntity>>()
    val diseasesSave = _diseasesSave.share()

    private fun getUserDiseases() = viewModelScope.safeLaunch {
        diseasesRepository.getUserDiseases().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _diseases.value = it
        }
    }

    fun getOrReloadDiseases() =
        if (firstLoadFlag)
            getUserDiseases()
        else
            diseasesRepository.reloadGetDiseasesUserRequest()


    fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity) =
        viewModelScope.safeLaunch {
            diseasesRepository.setUserDiseases(diseasesMainEntity).collect {
                if (it is Error && it.error is RefreshTokenExpired)
                    throw it.error
                _diseasesSave.value = it
                if (it is Success)
                    _diseases.value = Success(diseasesMainEntity)
            }
        }

    fun reloadDiseasesSave(diseasesMainEntity: DiseasesMainEntity) =
        diseasesRepository.reloadSetDiseasesUserRequest(diseasesMainEntity)

    fun onSuccessSaveToast() = uiActions.toast(R.string.user_info_save)

}