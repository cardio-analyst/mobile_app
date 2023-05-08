package com.example.lifestyle.presentation

import androidx.lifecycle.viewModelScope
import com.example.common.flows.ResultState
import com.example.common.flows.Success
import com.example.lifestyle.R
import com.example.lifestyle.domain.LifestyleRepository
import com.example.lifestyle.domain.entities.LifestyleEntity
import com.example.presentation.BaseRouter
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LifestyleViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val lifestyleRepository: LifestyleRepository,
    private val baseRouter: BaseRouter,
) : BaseViewModel(uiActions), BaseRouter by baseRouter {

    private val _lifestyle = SingleLiveEvent<ResultState<LifestyleEntity>>()
    val lifestyle = _lifestyle.share()

    private val _lifestyleSave = SingleLiveEvent<ResultState<LifestyleEntity>>()
    val lifestyleSave = _lifestyleSave.share()


    private fun getUserLifestyle() = viewModelScope.safeLaunch {
        lifestyleRepository.getUserLifestyle().collect {
            _lifestyle.value = it
        }
    }

    fun getOrReloadLifestyle() =
        if (firstLoadFlag)
            getUserLifestyle()
        else
            lifestyleRepository.reloadGetLifestyleUserRequest()

    fun setUserLifestyle(lifestyleMainEntity: LifestyleEntity) = viewModelScope.safeLaunch {
        lifestyleRepository.setUserLifestyle(lifestyleMainEntity).collect {
            _lifestyleSave.value = it
            if (it is Success)
                _lifestyle.value = Success(lifestyleMainEntity)
        }
    }

    fun reloadLifestyleSave(lifestyleMainEntity: LifestyleEntity) =
        lifestyleRepository.reloadSetLifestyleUserRequest(lifestyleMainEntity)

    fun onSuccessSaveToast() = uiActions.toast(R.string.user_info_save)

    val familyStatusValues = listOf(
        uiActions.getString(R.string.default_option_not_choosing),
        uiActions.getString(R.string.family_status_married),
        uiActions.getString(R.string.family_status_not_married),
        uiActions.getString(R.string.family_status_divorced),
        uiActions.getString(R.string.family_status_widower),
    )

    val eventParticipationValues = listOf(
        uiActions.getString(R.string.default_option_not_choosing),
        uiActions.getString(R.string.event_participation_one_in_week),
        uiActions.getString(R.string.event_participation_more_than_one_in_week)
    )

    val physicalActivityValues = listOf(
        uiActions.getString(R.string.physical_activity_one_in_week),
        uiActions.getString(R.string.physical_activity_more_than_one_in_week),
        uiActions.getString(R.string.physical_activity_one_in_day),
    )

    val workStatusValues = listOf(
        uiActions.getString(R.string.work_status_worker),
        uiActions.getString(R.string.work_status_unemployed),
        uiActions.getString(R.string.work_status_looking_for_job),
        uiActions.getString(R.string.work_status_pensioner),
    )

    val lifeValues = listOf(
        uiActions.getString(R.string.life_values_active_life),
        uiActions.getString(R.string.life_values_life_wisdom),
        uiActions.getString(R.string.life_values_health),
        uiActions.getString(R.string.life_values_interesting_job),
        uiActions.getString(R.string.life_values_beauty_of_nature_and_art),
        uiActions.getString(R.string.life_values_love),
        uiActions.getString(R.string.life_values_financially_secure_life),
        uiActions.getString(R.string.life_values_having_good_and_true_friends),
        uiActions.getString(R.string.life_values_public_vocation),
        uiActions.getString(R.string.life_values_knowledge_and_intellectual_development),
        uiActions.getString(R.string.life_values_productive_life),
        uiActions.getString(R.string.life_values_entertainment),
        uiActions.getString(R.string.life_values_autonomy),
        uiActions.getString(R.string.life_values_happy_family_life),
        uiActions.getString(R.string.life_values_creativity),
        uiActions.getString(R.string.life_values_self_confidence),
    )

}