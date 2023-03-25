package `is`.ulstu.cardioanalyst.ui.diseases

import androidx.lifecycle.viewModelScope
import com.example.common.RefreshTokenExpired
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.common.flows.Success
import com.example.data.repositories.diseases.IDiseasesDataRepository
import com.example.data.repositories.diseases.sources.entities.DiseasesMainEntity
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.R
import javax.inject.Inject

@HiltViewModel
class DiseasesViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val diseasesRepository: IDiseasesDataRepository,
) : BaseViewModel(uiActions) {

    private val _diseases = SingleLiveEvent<ResultState<DiseasesMainEntity>>()
    val diseases = _diseases.share()

    private val _diseasesSave = SingleLiveEvent<ResultState<DiseasesMainEntity>>()
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