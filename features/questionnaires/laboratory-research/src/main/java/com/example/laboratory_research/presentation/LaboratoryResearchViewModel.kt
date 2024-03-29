package com.example.laboratory_research.presentation

import androidx.lifecycle.viewModelScope
import com.example.common.flows.ResultState
import com.example.laboratory_research.R
import com.example.laboratory_research.domain.LaboratoryResearchRepository
import com.example.laboratory_research.domain.entities.*
import com.example.presentation.BaseRouter
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaboratoryResearchViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val laboratoryResearchRepository: LaboratoryResearchRepository,
    private val baseRouter: BaseRouter,
) : BaseViewModel(uiActions), BaseRouter by baseRouter {

    private val _laboratoryResearches =
        SingleLiveEvent<ResultState<List<GetLaboratoryResearchResponseEntity>>>()
    val laboratoryResearches = _laboratoryResearches.share()

    private val _createLaboratoryResearch =
        SingleLiveEvent<ResultState<CreateLaboratoryResearchResponseEntity>>()
    val createLaboratoryResearch = _createLaboratoryResearch.share()

    private val _updateLaboratoryResearch =
        SingleLiveEvent<ResultState<UpdateLaboratoryResearchResponseEntity>>()
    val updateLaboratoryResearch = _updateLaboratoryResearch.share()

    val currentLaboratoryResearchChanged = SingleLiveEvent<Boolean>()

    val laboratoryResearchChangedMap = mutableMapOf<Long?, GetLaboratoryResearchResponseEntity>()

    var viewPagerCurrentPagePosition: Int? = null


    fun getLaboratoryResearchesById(id: Long) =
        _laboratoryResearches.value?.getValueOrNull()?.first { id == it.id }

    private fun getUserLaboratoryResearches() = viewModelScope.safeLaunch {
        laboratoryResearchRepository.getLaboratoryResearches().collect {
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