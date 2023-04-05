package `is`.ulstu.cardioanalyst.glue.basic_indicators.mappers

import com.example.basic_indicators.domain.entities.*
import com.example.data.repositories.basic_indicators.sources.entities.*

fun UpdateBasicIndicatorResponseDataEntity.toUpdateBasicIndicatorResponseEntity() =
    UpdateBasicIndicatorResponseEntity(
        result = result,
    )

fun UpdateBasicIndicatorIdEntity.toUpdateBasicIndicatorIdDataEntity() =
    UpdateBasicIndicatorIdDataEntity(
        basicIndicatorId = basicIndicatorId,
        weight = weight,
        height = height,
        bodyMassIndex = bodyMassIndex,
        waistSize = waistSize,
        gender = gender,
        sbpLevel = sbpLevel,
        smoking = smoking,
        totalCholesterolLevel = totalCholesterolLevel,
        cvEventsRiskValue = cvEventsRiskValue,
        idealCardiovascularAgesRange = idealCardiovascularAgesRange,
    )

fun GetBasicIndicatorResponseDataEntity.toGetBasicIndicatorResponseEntity() =
    GetBasicIndicatorResponseEntity(
        id = id,
        weight = weight,
        height = height,
        bodyMassIndex = bodyMassIndex,
        waistSize = waistSize,
        gender = gender,
        sbpLevel = sbpLevel,
        smoking = smoking,
        totalCholesterolLevel = totalCholesterolLevel,
        cvEventsRiskValue = cvEventsRiskValue,
        idealCardiovascularAgesRange = idealCardiovascularAgesRange,
        createdAt = createdAt,
        scale = scale,
    )

fun CreateBasicIndicatorRequestEntity.toCreateBasicIndicatorRequestDataEntity() =
    CreateBasicIndicatorRequestDataEntity(
        weight = weight,
        height = height,
        bodyMassIndex = bodyMassIndex,
        waistSize = waistSize,
        gender = gender,
        sbpLevel = sbpLevel,
        smoking = smoking,
        totalCholesterolLevel = totalCholesterolLevel,
        cvEventsRiskValue = cvEventsRiskValue,
        idealCardiovascularAgesRange = idealCardiovascularAgesRange,
    )

fun CreateBasicIndicatorResponseDataEntity.toCreateBasicIndicatorResponseEntity() =
    CreateBasicIndicatorResponseEntity(
        result = result,
    )

fun GetCVERiskRequestEntity.toGetCVERiskRequestDataEntity() =
    GetCVERiskRequestDataEntity(
        gender = gender,
        smoking = smoking,
        sbpLevel = sbpLevel,
        totalCholesterolLevel = totalCholesterolLevel,
    )

fun GetCVERiskResponseDataEntity.toGetCVERiskResponseEntity() =
    GetCVERiskResponseEntity(
        value = value,
        scale = scale,
    )

fun GetIdealAgeResponseDataEntity.toGetIdealAgeResponseEntity() =
    GetIdealAgeResponseEntity(
        value = value,
        scale = scale,
    )