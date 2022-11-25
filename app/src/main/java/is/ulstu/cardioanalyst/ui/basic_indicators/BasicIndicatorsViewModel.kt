package `is`.ulstu.cardioanalyst.ui.basic_indicators

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.basic_indicators.IBasicIndicatorsRepository
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.*
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class BasicIndicatorsViewModel(
    navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val basicIndicatorsRepository: IBasicIndicatorsRepository =
        Singletons.basicIndicatorsRepository

    private val _basicIndicators =
        MutableLiveData<Result<List<GetBasicIndicatorResponseEntity>>>()
    val basicIndicators = _basicIndicators.share()

    private val _createBasicIndicators =
        MutableLiveData<Result<CreateBasicIndicatorResponseEntity>>()
    val createBasicIndicators = _createBasicIndicators.share()

    private val _updateBasicIndicators =
        MutableLiveData<Result<UpdateBasicIndicatorResponseEntity>>()
    val updateBasicIndicators = _updateBasicIndicators.share()

    private val _cveRisk = MutableLiveData<Result<GetCVERiskResponseEntity>>()
    val cveRisk = _cveRisk.share()

    private val _idealAge = MutableLiveData<Result<GetIdealAgeResponseEntity>>()
    val idealAge = _idealAge.share()


    private fun getUserBasicIndicators() = viewModelScope.safeLaunch {
        basicIndicatorsRepository.getBasicIndicators().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _basicIndicators.value = it
        }
    }

    fun reloadBasicIndicators() = basicIndicatorsRepository.reloadBasicIndicators()

    fun createUserBasicIndicators(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity) =
        viewModelScope.safeLaunch {
            basicIndicatorsRepository.createBasicIndicator(
                createBasicIndicatorRequestEntity
            ).collect {
                if (it is Error && it.error is RefreshTokenExpired)
                    throw it.error
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
                    if (it is Error && it.error is RefreshTokenExpired)
                        throw it.error
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

    fun onSuccessCreateToast() = uiActions.toast(Singletons.getString(R.string.user_info_create))

    fun onSuccessChangeToast() = uiActions.toast(Singletons.getString(R.string.user_info_save))

    fun <T : Comparable<T>> onOutOfRangeToast(name: String, range: ClosedFloatingPointRange<T>) =
        uiActions.toast(
            Singletons.getString(
                R.string.out_of_range_toast,
                name,
                range.start,
                range.endInclusive
            )
        )

    fun getDefaultLBasicIndicatorRecord() = GetBasicIndicatorResponseEntity(
        id = null,
        weight = 40.0,
        height = 145.0,
        bodyMassIndex = 16.0,
        waistSize = 50.0,
        gender = Singletons.getString(R.string.default_option_not_choosing),
        sbpLevel = 80.0,
        smoking = false,
        totalCholesterolLevel = 3.0,
        cvEventsRiskValue = 0,
        idealCardiovascularAgesRange = "40",
        createdAt = Singletons.getString(R.string.basic_indicators_default_tooltip)
    )

    init {
        getUserBasicIndicators()
    }
}