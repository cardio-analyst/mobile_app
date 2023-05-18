package com.example.basic_indicators.presentation

import androidx.lifecycle.viewModelScope
import com.example.basic_indicators.R
import com.example.basic_indicators.domain.BasicIndicatorsRepository
import com.example.basic_indicators.domain.entities.*
import com.example.common.flows.ResultState
import com.example.presentation.BaseRouter
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicIndicatorsViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val basicIndicatorsRepository: BasicIndicatorsRepository,
    private val baseRouter: BaseRouter,
) : BaseViewModel(uiActions), BaseRouter by baseRouter {

    private val _basicIndicators =
        SingleLiveEvent<ResultState<List<GetBasicIndicatorResponseEntity>>>()
    val basicIndicators = _basicIndicators.share()

    private val _createBasicIndicators =
        SingleLiveEvent<ResultState<CreateBasicIndicatorResponseEntity>>()
    val createBasicIndicators = _createBasicIndicators.share()

    private val _updateBasicIndicators =
        SingleLiveEvent<ResultState<UpdateBasicIndicatorResponseEntity>>()
    val updateBasicIndicators = _updateBasicIndicators.share()

    val currentBasicIndicatorChanged = SingleLiveEvent<Boolean>()

    val basicIndicatorsChangedMap = mutableMapOf<Long?, GetBasicIndicatorResponseEntity>()

    var viewPagerCurrentPagePosition: Int? = null


    fun getLaboratoryResearchesById(id: Long) =
        _basicIndicators.value?.getValueOrNull()?.first { id == it.id }

    private fun getUserBasicIndicators() = viewModelScope.safeLaunch {
        basicIndicatorsRepository.getBasicIndicators().collect {
            _basicIndicators.value = it
        }
    }

    fun getOrReloadBasicIndicators() =
        if (firstLoadFlag)
            getUserBasicIndicators()
        else
            basicIndicatorsRepository.reloadBasicIndicators()

    fun createUserBasicIndicators(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity) =
        viewModelScope.safeLaunch {
            basicIndicatorsRepository.createBasicIndicator(
                createBasicIndicatorRequestEntity
            ).collect {
                _createBasicIndicators.value = it
            }
        }

    fun getCreateBasicIndicatorEntity(getBasicIndicatorResponseEntity: GetBasicIndicatorResponseEntity) =
        CreateBasicIndicatorRequestEntity(
            weight = getBasicIndicatorResponseEntity.weight,
            height = getBasicIndicatorResponseEntity.height,
            bodyMassIndex = getBasicIndicatorResponseEntity.bodyMassIndex,
            waistSize = getBasicIndicatorResponseEntity.waistSize,
            gender = getBasicIndicatorResponseEntity.gender,
            sbpLevel = getBasicIndicatorResponseEntity.sbpLevel,
            smoking = getBasicIndicatorResponseEntity.smoking,
            totalCholesterolLevel = getBasicIndicatorResponseEntity.totalCholesterolLevel,
            cvEventsRiskValue = getBasicIndicatorResponseEntity.cvEventsRiskValue,
            idealCardiovascularAgesRange = getBasicIndicatorResponseEntity.idealCardiovascularAgesRange,
        )

    fun reloadCreateBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity) =
        basicIndicatorsRepository.reloadCreateBasicIndicator(
            createBasicIndicatorRequestEntity
        )

    fun updateUserBasicIndicator(updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity) =
        viewModelScope.safeLaunch {
            basicIndicatorsRepository.updateBasicIndicator(updateBasicIndicatorIdEntity)
                .collect {
                    _updateBasicIndicators.value = it
                }
        }

    fun getUpdateBasicIndicatorEntity(getBasicIndicatorResponseEntity: GetBasicIndicatorResponseEntity) =
        UpdateBasicIndicatorIdEntity(
            basicIndicatorId = getBasicIndicatorResponseEntity.id!!,
            weight = getBasicIndicatorResponseEntity.weight,
            height = getBasicIndicatorResponseEntity.height,
            bodyMassIndex = getBasicIndicatorResponseEntity.bodyMassIndex,
            waistSize = getBasicIndicatorResponseEntity.waistSize,
            gender = getBasicIndicatorResponseEntity.gender,
            sbpLevel = getBasicIndicatorResponseEntity.sbpLevel,
            smoking = getBasicIndicatorResponseEntity.smoking,
            totalCholesterolLevel = getBasicIndicatorResponseEntity.totalCholesterolLevel,
            cvEventsRiskValue = getBasicIndicatorResponseEntity.cvEventsRiskValue,
            idealCardiovascularAgesRange = getBasicIndicatorResponseEntity.idealCardiovascularAgesRange,
        )

    fun reloadUpdateBasicIndicator(updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity) =
        basicIndicatorsRepository.reloadUpdateBasicIndicator(updateBasicIndicatorIdEntity)

    fun onSuccessCreateToast() = uiActions.toast(R.string.user_info_create)

    fun onSuccessChangeToast() = uiActions.toast(R.string.user_info_save)

    fun <T : Comparable<T>> onOutOfRangeToast(name: String, range: ClosedFloatingPointRange<T>) =
        uiActions.toast(
            messageRes = R.string.out_of_range_toast,
            name,
            range.start,
            range.endInclusive
        )

    fun getDefaultLBasicIndicatorRecord() = GetBasicIndicatorResponseEntity(
        id = null,
        weight = 40.0,
        height = 145.0,
        bodyMassIndex = 16.0,
        waistSize = 50.0,
        gender = uiActions.getString(R.string.default_option_not_choosing),
        sbpLevel = 80.0,
        smoking = false,
        totalCholesterolLevel = 3.0,
        cvEventsRiskValue = 0,
        idealCardiovascularAgesRange = "40",
        createdAt = uiActions.getString(R.string.basic_indicators_default_tooltip),
        scale = "unknown"
    )

}