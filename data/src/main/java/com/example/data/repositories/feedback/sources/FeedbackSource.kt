package com.example.data.repositories.feedback.sources

import com.example.data.repositories.feedback.sources.entities.FeedbackDataEntity
import com.example.data.repositories.feedback.sources.entities.FeedbackResponseDataEntity

interface FeedbackSource {
    /**
     * Send feedback to moderators
     */
    suspend fun sendFeedback(feedbackDataEntity: FeedbackDataEntity): FeedbackResponseDataEntity
}