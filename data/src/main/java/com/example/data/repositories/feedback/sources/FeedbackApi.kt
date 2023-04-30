package com.example.data.repositories.feedback.sources

import com.example.data.repositories.feedback.sources.entities.FeedbackDataEntity
import com.example.data.repositories.feedback.sources.entities.FeedbackResponseDataEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApi {

    @POST("feedback")
    suspend fun sendFeedback(@Body feedbackDataEntity: FeedbackDataEntity): FeedbackResponseDataEntity
}