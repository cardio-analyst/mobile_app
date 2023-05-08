package com.example.data.repositories.feedback

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.feedback.sources.entities.FeedbackDataEntity
import com.example.data.repositories.feedback.sources.entities.FeedbackResponseDataEntity
import kotlinx.coroutines.flow.Flow

interface IFeedbackDataRepository : Repository {

    /**
     * Send feedback to moderators
     * @param feedbackDataEntity [FeedbackDataEntity]
     * @return [Flow]
     */
    fun sendFeedback(feedbackDataEntity: FeedbackDataEntity): Flow<ResultState<FeedbackResponseDataEntity>>

    /**
     * Reload sending feedback to moderators
     */
    fun reloadSendFeedback(feedbackDataEntity: FeedbackDataEntity)
}