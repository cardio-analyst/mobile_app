package `is`.ulstu.cardioanalyst.ui.diseases

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class DiseasesViewModel(
    navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val diseasesRepository: IDiseasesRepository = Singletons.diseasesRepository

    private val _diseases = MutableLiveData<Result<DiseasesMainEntity>>()
    val diseases = _diseases.share()

    private val _diseasesSave = MutableLiveData<Result<DiseasesMainEntity>>()
    val diseasesSave = _diseasesSave.share()

    private fun getUserDiseases() = viewModelScope.safeLaunch {
        diseasesRepository.getUserDiseases().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _diseases.value = it
        }
    }

    fun reloadDiseases() = diseasesRepository.reloadGetDiseasesUserRequest()

    fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity) = viewModelScope.safeLaunch {
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

    fun onSuccessSaveToast() = uiActions.toast(Singletons.getString(R.string.user_info_save))

    init {
        diseasesRepository.reloadGetDiseasesUserRequest()
        getUserDiseases()
    }
}