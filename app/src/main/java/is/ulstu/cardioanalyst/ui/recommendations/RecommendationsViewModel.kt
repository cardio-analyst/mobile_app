package `is`.ulstu.cardioanalyst.ui.recommendations

import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.models.recommendations.IRecommendationsRepository
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
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
class RecommendationsViewModel @Inject constructor(
    uiActions: UiActions,
    private val recommendationsRepository: IRecommendationsRepository,
) : BaseViewModel(uiActions) {

    private val _recommendations =
        SingleLiveEvent<Result<List<GetRecommendationsResponseEntity>>>()
    val recommendations = _recommendations.share()


    private fun getUserRecommendations() = viewModelScope.safeLaunch {
        recommendationsRepository.getRecommendations().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _recommendations.value = it
        }
    }

    fun getOrReloadRecommendations() =
        if (firstLoadFlag)
            getUserRecommendations()
        else
            recommendationsRepository.reloadGetRecommendations()

}