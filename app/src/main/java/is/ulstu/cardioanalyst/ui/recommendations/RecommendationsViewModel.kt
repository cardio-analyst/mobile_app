package `is`.ulstu.cardioanalyst.ui.recommendations

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.laboratory_research.ILaboratoryResearchRepository
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.*
import `is`.ulstu.cardioanalyst.models.recommendations.IRecommendationsRepository
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class RecommendationsViewModel(
    navigator: Navigator,
    uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val recommendationsRepository: IRecommendationsRepository =
        Singletons.recommendationsRepository

    private val _recommendations =
        MutableLiveData<Result<List<GetRecommendationsResponseEntity>>>()
    val recommendations = _recommendations.share()


    private fun getUserRecommendations() = viewModelScope.safeLaunch {
        recommendationsRepository.getRecommendations().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _recommendations.value = it
        }
    }

    fun reloadRecommendations() = recommendationsRepository.reloadGetRecommendations()

    init {
        getUserRecommendations()
    }
}