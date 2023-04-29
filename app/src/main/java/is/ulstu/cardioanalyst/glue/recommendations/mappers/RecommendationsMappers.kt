package `is`.ulstu.cardioanalyst.glue.recommendations.mappers

import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseDataEntity
import com.example.recommendations.domain.entities.GetRecommendationsResponseEntity

fun GetRecommendationsResponseDataEntity.toGetRecommendationsResponseEntity() =
    GetRecommendationsResponseEntity(
        what = what,
        why = why,
        how = how,
    )