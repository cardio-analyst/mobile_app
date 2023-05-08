package `is`.ulstu.cardioanalyst.glue.feedback

import com.example.common.flows.ResultState
import com.example.data.repositories.feedback.IFeedbackDataDataRepository
import com.example.feedback.domain.FeedbackRepository
import com.example.feedback.domain.entities.Feedback
import com.example.feedback.domain.entities.FeedbackResponse
import `is`.ulstu.cardioanalyst.glue.feedback.mappers.toFeedbackDataEntity
import `is`.ulstu.cardioanalyst.glue.feedback.mappers.toFeedbackResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterFeedbackRepository @Inject constructor(
    private val feedbackRepository: IFeedbackDataDataRepository,
) : FeedbackRepository {

    override fun sendFeedback(feedback: Feedback): Flow<ResultState<FeedbackResponse>> =
        feedbackRepository.sendFeedback(feedback.toFeedbackDataEntity()).map { resultState ->
            resultState.map { feedbackResponseDataEntity ->
                feedbackResponseDataEntity.toFeedbackResponse()
            }
        }

    override fun reloadSendFeedback(feedback: Feedback) =
        feedbackRepository.reloadSendFeedback(feedback.toFeedbackDataEntity())
}