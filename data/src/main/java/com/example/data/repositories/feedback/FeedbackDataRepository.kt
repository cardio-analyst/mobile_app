package com.example.data.repositories.feedback

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.feedback.sources.FeedbackSource
import com.example.data.repositories.feedback.sources.entities.FeedbackDataEntity
import com.example.data.repositories.feedback.sources.entities.FeedbackResponseDataEntity
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedbackDataRepository @Inject constructor(
    private val feedbackSource: FeedbackSource,
    private val userRepository: IUserDataRepository,
) : IFeedbackDataRepository {

    private val sendFeedbackLazyFlowSubject =
        LazyFlowSubject<FeedbackDataEntity, FeedbackResponseDataEntity> { feedbackDataEntity ->
            doSendFeedback(feedbackDataEntity)
        }


    override fun sendFeedback(feedbackDataEntity: FeedbackDataEntity): Flow<ResultState<FeedbackResponseDataEntity>> =
        sendFeedbackLazyFlowSubject.listen(feedbackDataEntity)

    override fun reloadSendFeedback(feedbackDataEntity: FeedbackDataEntity) =
        sendFeedbackLazyFlowSubject.reloadArgument(feedbackDataEntity)

    private suspend fun doSendFeedback(feedbackDataEntity: FeedbackDataEntity): FeedbackResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            feedbackSource.sendFeedback(feedbackDataEntity)
        }


}