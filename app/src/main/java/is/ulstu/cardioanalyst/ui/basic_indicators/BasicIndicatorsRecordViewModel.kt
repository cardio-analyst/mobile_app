package `is`.ulstu.cardioanalyst.ui.basic_indicators

import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.basic_indicators.IBasicIndicatorsRepository
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.GetCVERiskRequestEntity
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.GetCVERiskResponseEntity
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.GetIdealAgeResponseEntity
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class BasicIndicatorsRecordViewModel(
    navigator: Navigator,
    val uiActions: UiActions
) : BaseViewModel(navigator) {

    private val basicIndicatorsRepository: IBasicIndicatorsRepository =
        Singletons.basicIndicatorsRepository

    private val _cveRisk = MutableLiveData<Result<GetCVERiskResponseEntity>>()
    val cveRisk = _cveRisk.share()

    private val _idealAge = MutableLiveData<Result<GetIdealAgeResponseEntity>>()
    val idealAge = _idealAge.share()

    fun getCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity) =
        viewModelScope.safeLaunch {
            basicIndicatorsRepository.getCVERisk(
                getCVERiskRequestEntity
            ).collect {
                if (it is Error && it.error is RefreshTokenExpired)
                    throw it.error
                _cveRisk.value = it
            }
        }

    fun getIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity) =
        viewModelScope.safeLaunch {
            basicIndicatorsRepository.getIdealAge(
                getCVERiskRequestEntity
            ).collect {
                if (it is Error && it.error is RefreshTokenExpired)
                    throw it.error
                _idealAge.value = it
            }
        }
}