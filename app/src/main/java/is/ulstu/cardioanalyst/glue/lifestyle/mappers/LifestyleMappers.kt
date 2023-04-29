package `is`.ulstu.cardioanalyst.glue.lifestyle.mappers

import com.example.data.repositories.lifestyle.sources.entities.LifestyleDataEntity
import com.example.lifestyle.domain.entities.LifestyleEntity

fun LifestyleDataEntity.toLifestyleEntity() = LifestyleEntity(
    familyStatus = familyStatus,
    eventsParticipation = eventsParticipation,
    physicalActivity = physicalActivity,
    workStatus = workStatus,
    significantValueHigh = significantValueHigh,
    significantValueMedium = significantValueMedium,
    significantValueLow = significantValueLow,
)

fun LifestyleEntity.toLifestyleDataEntity() = LifestyleDataEntity(
    familyStatus = familyStatus,
    eventsParticipation = eventsParticipation,
    physicalActivity = physicalActivity,
    workStatus = workStatus,
    significantValueHigh = significantValueHigh,
    significantValueMedium = significantValueMedium,
    significantValueLow = significantValueLow,
)