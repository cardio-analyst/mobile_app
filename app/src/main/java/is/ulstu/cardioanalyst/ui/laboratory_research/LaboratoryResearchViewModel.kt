package `is`.ulstu.cardioanalyst.ui.laboratory_research

import androidx.lifecycle.viewModelScope
import com.example.common.RefreshTokenExpired
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.data.repositories.laboratory_research.ILaboratoryResearchDataRepository
import com.example.data.repositories.laboratory_research.sources.entities.*
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.R
import javax.inject.Inject

@HiltViewModel
class LaboratoryResearchViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val laboratoryResearchRepository: ILaboratoryResearchDataRepository,
) : BaseViewModel(uiActions) {

    private val _laboratoryResearches =
        SingleLiveEvent<ResultState<List<GetLaboratoryResearchResponseEntity>>>()
    val laboratoryResearches = _laboratoryResearches.share()

    private val _createLaboratoryResearch =
        SingleLiveEvent<ResultState<CreateLaboratoryResearchResponseEntity>>()
    val createLaboratoryResearch = _createLaboratoryResearch.share()

    private val _updateLaboratoryResearch =
        SingleLiveEvent<ResultState<UpdateLaboratoryResearchResponseEntity>>()
    val updateLaboratoryResearch = _updateLaboratoryResearch.share()


    private fun getUserLaboratoryResearches() = viewModelScope.safeLaunch {
        laboratoryResearchRepository.getLaboratoryResearches().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _laboratoryResearches.value = it
        }
    }

    fun getOrReloadLaboratoryResearches() =
        if (firstLoadFlag)
            getUserLaboratoryResearches()
        else
            laboratoryResearchRepository.reloadGetLaboratoryResearches()

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

    fun onSuccessCreateToast() = uiActions.toast(R.string.user_info_create)

    fun onSuccessChangeToast() = uiActions.toast(R.string.user_info_save)

    fun <T : Comparable<T>> onOutOfRangeToast(name: String, range: ClosedFloatingPointRange<T>) =
        uiActions.toast(
            messageRes = R.string.out_of_range_toast,
            name,
            range.start,
            range.endInclusive
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
        createdAt = uiActions.getString(R.string.lab_res_default_tooltip),
    )

}