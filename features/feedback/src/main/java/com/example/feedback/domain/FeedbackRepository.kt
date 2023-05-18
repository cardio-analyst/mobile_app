package com.example.feedback.domain

import com.example.common.flows.ResultState
import com.example.feedback.domain.entities.Feedback
import com.example.feedback.domain.entities.FeedbackResponse
import kotlinx.coroutines.flow.Flow

interface FeedbackRepository {
    /**
     * Send feedback to moderators
     * @param feedback [Feedback]
     * @return [Flow]
     */
    fun sendFeedback(feedback: Feedback): Flow<ResultState<FeedbackResponse>>

    /**
     * Reload sending feedback to moderators
     */
    fun reloadSendFeedback(feedback: Feedback)
}