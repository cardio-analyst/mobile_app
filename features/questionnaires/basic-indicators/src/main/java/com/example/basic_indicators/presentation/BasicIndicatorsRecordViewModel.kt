package com.example.basic_indicators.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.basic_indicators.domain.BasicIndicatorsRepository
import com.example.basic_indicators.domain.entities.GetCVERiskRequestEntity
import com.example.basic_indicators.domain.entities.GetCVERiskResponseEntity
import com.example.basic_indicators.domain.entities.GetIdealAgeResponseEntity
import com.example.common.RefreshTokenExpired
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.presentation.BaseViewModel
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicIndicatorsRecordViewModel @Inject constructor(
    val uiActions: UiAction,
    private val basicIndicatorsRepository: BasicIndicatorsRepository,
) : BaseViewModel() {

    private val _cveRisk = MutableLiveData<ResultState<GetCVERiskResponseEntity>>()
    val cveRisk = _cveRisk.share()

    private val _idealAge = MutableLiveData<ResultState<GetIdealAgeResponseEntity>>()
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