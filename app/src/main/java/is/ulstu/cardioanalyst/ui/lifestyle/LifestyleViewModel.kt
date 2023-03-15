package `is`.ulstu.cardioanalyst.ui.lifestyle

import androidx.lifecycle.viewModelScope
import com.example.common.RefreshTokenExpired
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.common.flows.Success
import com.example.presentation.BaseViewModel
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.models.lifestyle.ILifestyleRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.StenocardiaSymptomsTestRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.TreatmentAdherenceTestRepository
import `is`.ulstu.foundation.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class LifestyleViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val lifestyleRepository: ILifestyleRepository,
    private val stenocardiaSymptomsTestRepository: StenocardiaSymptomsTestRepository,
    private val treatmentAdherenceTestRepository: TreatmentAdherenceTestRepository,
) : BaseViewModel(uiActions) {

    private val _lifestyle = SingleLiveEvent<ResultState<LifestyleMainEntity>>()
    val lifestyle = _lifestyle.share()

    private val _lifestyleSave = SingleLiveEvent<ResultState<LifestyleMainEntity>>()
    val lifestyleSave = _lifestyleSave.share()

    fun setCurrentLifestyleData(currentLifestyleData: LifestyleMainEntity?) =
        lifestyleRepository.setCurrentChanges(currentLifestyleData)

    fun getCurrentLifestyleData(): LifestyleMainEntity? {
        val result = lifestyleRepository.getCurrentChanges()
        if (result != null) {
            stenocardiaSymptomsTestRepository.scoreResult?.let { anginaScore ->
                result.anginaScore = anginaScore
            }
            treatmentAdherenceTestRepository.results?.let { results ->
                result.adherenceDrugTherapy = results.first
                result.adherenceMedicalSupport = results.second
                result.adherenceLifestyleMod = results.third
            }
        }
        setCurrentLifestyleData(null)
        return result
    }

    private fun getUserLifestyle() = viewModelScope.safeLaunch {
        lifestyleRepository.getUserLifestyle().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _lifestyle.value = it
        }
    }

    fun getOrReloadLifestyle() =
        if (firstLoadFlag)
            getUserLifestyle()
        else
            lifestyleRepository.reloadGetLifestyleUserRequest()

    fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity) = viewModelScope.safeLaunch {
        lifestyleRepository.setUserLifestyle(lifestyleMainEntity).collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _lifestyleSave.value = it
            if (it is Success)
                _lifestyle.value = Success(lifestyleMainEntity)
        }
    }

    fun reloadLifestyleSave(lifestyleMainEntity: LifestyleMainEntity) =
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