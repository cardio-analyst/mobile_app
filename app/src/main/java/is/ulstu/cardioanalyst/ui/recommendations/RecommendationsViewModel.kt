package `is`.ulstu.cardioanalyst.ui.recommendations

import androidx.lifecycle.viewModelScope
import com.example.common.flows.ResultState
import com.example.data.repositories.recommendations.IRecommendationsDataRepository
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseEntity
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationsViewModel @Inject constructor(
    uiAction: UiAction,
    private val recommendationsRepository: IRecommendationsDataRepository,
) : BaseViewModel(uiAction) {

    private val _recommendations =
        SingleLiveEvent<ResultState<List<GetRecommendationsResponseEntity>>>()
    val recommendations = _recommendations.share()


    private fun getUserRecommendations() = viewModelScope.safeLaunch {
        recommendationsRepository.getRecommendations().collect {
            _recommendations.value = it
        }
    }

    fun getOrReloadRecommendations() =
        if (firstLoadFlag)
            getUserRecommendations()
        else
            recommendationsRepository.reloadGetRecommendations()

}