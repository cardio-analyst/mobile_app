package com.example.data.repositories.feedback.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.feedback.sources.entities.FeedbackDataEntity
import com.example.data.repositories.feedback.sources.entities.FeedbackResponseDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitFeedbackSource @Inject constructor(
    config: RetrofitConfig,
) : BaseRetrofitSource(config), FeedbackSource {

    private val feedbackApi = retrofit.create(FeedbackApi::class.java)

    override suspend fun sendFeedback(
        feedbackDataEntity: FeedbackDataEntity
    ): FeedbackResponseDataEntity =
        wrapRetrofitExceptions { feedbackApi.sendFeedback(feedbackDataEntity) }
}