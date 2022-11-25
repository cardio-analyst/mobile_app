package `is`.ulstu.cardioanalyst.ui.laboratory_research

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.laboratory_research.ILaboratoryResearchRepository
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.*
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class LaboratoryResearchViewModel(
    navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val laboratoryResearchRepository: ILaboratoryResearchRepository =
        Singletons.laboratoryResearchRepository

    private val _laboratoryResearches =
        MutableLiveData<Result<List<GetLaboratoryResearchResponseEntity>>>()
    val laboratoryResearches = _laboratoryResearches.share()

    private val _createLaboratoryResearch =
        MutableLiveData<Result<CreateLaboratoryResearchResponseEntity>>()
    val createLaboratoryResearch = _createLaboratoryResearch.share()

    private val _updateLaboratoryResearch =
        MutableLiveData<Result<UpdateLaboratoryResearchResponseEntity>>()
    val updateLaboratoryResearch = _updateLaboratoryResearch.share()


    private fun getUserLaboratoryResearches() = viewModelScope.safeLaunch {
        laboratoryResearchRepository.getLaboratoryResearches().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _laboratoryResearches.value = it
        }
    }

    fun reloadLaboratoryResearches() = laboratoryResearchRepository.reloadGetLaboratoryResearches()

    fun createUserLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity) =
        viewModelScope.safeLaunch {
            laboratoryResearchRepository.createLaboratoryResearch(
                createLaboratoryResearchRequestEntity
            ).collect {
                if (it is Error && it.error is RefreshTokenExpired)
                    throw it.error
                _createLaboratoryResearch.value = it
            }
        }

    fun getCreateLaboratoryResearchEntity(getLaboratoryResearchResponseEntity: GetLaboratoryResearchResponseEntity) =
        CreateLaboratoryResearchRequestEntity(
            highDensityCholesterol = getLaboratoryResearchResponseEntity.highDensityCholesterol,
            lowDensityCholesterol = getLaboratoryResearchResponseEntity.lowDensityCholesterol,
            triglycerides = getLaboratoryResearchResponseEntity.triglycerides,
            lipoprotein = getLaboratoryResearchResponseEntity.lipoprotein,
            highlySensitiveCReactiveProtein = getLaboratoryResearchResponseEntity.highlySensitiveCReactiveProtein,
            atherogenicityCoefficient = getLaboratoryResearchResponseEntity.atherogenicityCoefficient,
            creatinine = getLaboratoryResearchResponseEntity.creatinine,
            atheroscleroticPlaquesPresence = getLaboratoryResearchResponseEntity.atheroscleroticPlaquesPresence,
        )

    fun reloadCreateLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity) =
        laboratoryResearchRepository.reloadCreateLaboratoryResearch(
            createLaboratoryResearchRequestEntity
        )

    fun updateUserLaboratoryResearch(updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity) =
        viewModelScope.safeLaunch {
            laboratoryResearchRepository.updateLaboratoryResearch(updateLaboratoryResearchIdEntity)
                .collect {
                    if (it is Error && it.error is RefreshTokenExpired)
                        throw it.error
                    _updateLaboratoryResearch.value = it
                }
        }

    fun getUpdateLaboratoryResearchEntity(getLaboratoryResearchResponseEntity: GetLaboratoryResearchResponseEntity) =
        UpdateLaboratoryResearchIdEntity(
            laboratoryResearchId = getLaboratoryResearchResponseEntity.id!!,
            highDensityCholesterol = getLaboratoryResearchResponseEntity.highDensityCholesterol,
            lowDensityCholesterol = getLaboratoryResearchResponseEntity.lowDensityCholesterol,
            triglycerides = getLaboratoryResearchResponseEntity.triglycerides,
            lipoprotein = getLaboratoryResearchResponseEntity.lipoprotein,
            highlySensitiveCReactiveProtein = getLaboratoryResearchResponseEntity.highlySensitiveCReactiveProtein,
            atherogenicityCoefficient = getLaboratoryResearchResponseEntity.atherogenicityCoefficient,
            creatinine = getLaboratoryResearchResponseEntity.creatinine,
            atheroscleroticPlaquesPresence = getLaboratoryResearchResponseEntity.atheroscleroticPlaquesPresence,
        )

    fun reloadUpdateLaboratoryResearch(updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity) =
        laboratoryResearchRepository.reloadUpdateLaboratoryResearch(updateLaboratoryResearchIdEntity)

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

    fun getDefaultLaboratoryResearchRecord() = GetLaboratoryResearchResponseEntity(
        id = null,
        highDensityCholesterol = 0.5,
        lowDensityCholesterol = 0.5,
        triglycerides = 0.2,
        lipoprotein = 0.0,
        highlySensitiveCReactiveProtein = 0.1,
        atherogenicityCoefficient = 0.1,
        creatinine = 20.0,
        atheroscleroticPlaquesPresence = false,
        createdAt = Singletons.getString(R.string.lab_res_default_tooltip),
    )

    init {
        getUserLaboratoryResearches()
    }
}