package `is`.ulstu.cardioanalyst.glue.feedback.mappers

import com.example.data.repositories.feedback.sources.entities.FeedbackDataEntity
import com.example.data.repositories.feedback.sources.entities.FeedbackResponseDataEntity
import com.example.feedback.domain.entities.Feedback
import com.example.feedback.domain.entities.FeedbackResponse
import `is`.ulstu.cardioanalyst.BuildConfig

fun Feedback.toFeedbackDataEntity() = FeedbackDataEntity(
    mark = mark,
    message = message,
    version = BuildConfig.VERSION_NAME,
)

fun FeedbackResponseDataEntity.toFeedbackResponse() = FeedbackResponse(
    result = result,
)