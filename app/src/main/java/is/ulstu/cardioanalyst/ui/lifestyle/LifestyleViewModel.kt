package `is`.ulstu.cardioanalyst.ui.lifestyle

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.models.lifestyle.ILifestyleRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.StenocardiaSymptomsTestRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.TreatmentAdherenceTestRepository
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.cardioanalyst.ui.lifestyle.tests.StenocardiaSymptomsTestFragment
import `is`.ulstu.cardioanalyst.ui.lifestyle.tests.TreatmentAdherenceTestFragment
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.SingleLiveEvent
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LifestyleViewModel @Inject constructor(
    private val navigator: Navigator,
    userSettings: UserSettings,
    private val uiActions: UiActions,
    private val lifestyleRepository: ILifestyleRepository,
    private val stenocardiaSymptomsTestRepository: StenocardiaSymptomsTestRepository,
    private val treatmentAdherenceTestRepository: TreatmentAdherenceTestRepository,
) : BaseViewModel(navigator, userSettings, uiActions) {

    private val _lifestyle = SingleLiveEvent<Result<LifestyleMainEntity>>()
    val lifestyle = _lifestyle.share()

    private val _lifestyleSave = SingleLiveEvent<Result<LifestyleMainEntity>>()
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

    fun startStenocardiaSymptomsTest() =
        navigator.launch(StenocardiaSymptomsTestFragment())

    fun startTreatmentAdherenceTest() =
        navigator.launch(TreatmentAdherenceTestFragment())


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