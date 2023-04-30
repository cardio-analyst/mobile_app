package `is`.ulstu.cardioanalyst.glue.feedback.mappers

import com.example.data.repositories.feedback.sources.entities.FeedbackDataEntity
import com.example.data.repositories.feedback.sources.entities.FeedbackResponseDataEntity
import com.example.feedback.domain.entities.Feedback
import com.example.feedback.domain.entities.FeedbackResponse

fun Feedback.toFeedbackDataEntity() = FeedbackDataEntity(
    mark = mark,
    message = message,
)

fun FeedbackResponseDataEntity.toFeedbackResponse() = FeedbackResponse(
    result = result,
)