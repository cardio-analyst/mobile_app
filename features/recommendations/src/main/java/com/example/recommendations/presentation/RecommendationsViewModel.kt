package com.example.recommendations.presentation

import androidx.lifecycle.viewModelScope
import com.example.common.flows.ResultState
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import com.example.recommendations.domain.RecommendationsRepository
import com.example.recommendations.domain.entities.GetRecommendationsResponseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationsViewModel @Inject constructor(
    uiAction: UiAction,
    private val recommendationsRepository: RecommendationsRepository,
    private val recommendationsRouter: RecommendationsRouter,
) : BaseViewModel(uiAction), RecommendationsRouter by recommendationsRouter {

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