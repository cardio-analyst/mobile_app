package com.example.feedback.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.flows.ResultState
import com.example.feedback.R
import com.example.feedback.domain.FeedbackRepository
import com.example.feedback.domain.entities.Feedback
import com.example.feedback.domain.entities.FeedbackResponse
import com.example.presentation.BaseViewModel
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val uiAction: UiAction,
    private val feedbackRepository: FeedbackRepository,
    private val feedbackRouter: FeedbackRouter,
) : BaseViewModel(uiAction), FeedbackRouter by feedbackRouter {

    private val _sendFeedback = MutableLiveData<ResultState<FeedbackResponse>>()
    val sendFeedback = _sendFeedback.share()


    fun sendFeedback(mark: Int, message: String) = viewModelScope.safeLaunch {
        feedbackRepository.sendFeedback(Feedback(
            mark = mark,
            message = message,
        )).collect {
            _sendFeedback.value = it
        }
    }

    fun reloadSendFeedback(mark: Int, message: String) = feedbackRepository.reloadSendFeedback(
        Feedback(
            mark = mark,
            message = message,
        )
    )

    fun showSuccessToast() = uiAction.toast(R.string.feedback_success_toast_text)
}