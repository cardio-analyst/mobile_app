package `is`.ulstu.cardioanalyst.ui.lifestyle

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.lifestyle.ILifestyleRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.cardioanalyst.ui.lifestyle.tests.LifestyleTestListener
import `is`.ulstu.cardioanalyst.ui.lifestyle.tests.StenocardiaSymptomsTestFragment
import `is`.ulstu.cardioanalyst.ui.lifestyle.tests.TreatmentAdherenceTestFragment
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class LifestyleViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val lifestyleRepository: ILifestyleRepository = Singletons.lifestyleRepository

    private val _lifestyle = MutableLiveData<Result<LifestyleMainEntity>>()
    val lifestyle = _lifestyle.share()

    private val _lifestyleSave = MutableLiveData<Result<LifestyleMainEntity>>()
    val lifestyleSave = _lifestyleSave.share()

    private fun getUserLifestyle() = viewModelScope.safeLaunch {
        lifestyleRepository.getUserLifestyle().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _lifestyle.value = it
        }
    }

    fun reloadLifestyle() = lifestyleRepository.reloadGetLifestyleUserRequest()

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

    fun onSuccessSaveToast() = uiActions.toast(Singletons.getString(R.string.user_info_save))

    fun startStenocardiaSymptomsTest(lifestyleTestListener: LifestyleTestListener) =
        navigator.launch(StenocardiaSymptomsTestFragment.Screen(lifestyleTestListener))

    fun startTreatmentAdherenceTest(lifestyleTestListener: LifestyleTestListener) =
        navigator.launch(TreatmentAdherenceTestFragment.Screen(lifestyleTestListener))

    init {
        lifestyleRepository.reloadGetLifestyleUserRequest()
        getUserLifestyle()
    }
}