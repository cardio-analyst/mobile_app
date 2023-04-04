package `is`.ulstu.cardioanalyst.glue.laboratory_research.mappers

import com.example.data.repositories.laboratory_research.sources.entities.*
import com.example.laboratory_research.domain.entities.*

fun UpdateLaboratoryResearchResponseDataEntity.toUpdateLaboratoryResearchResponseEntity() =
    UpdateLaboratoryResearchResponseEntity(
        result = result,
    )

fun UpdateLaboratoryResearchIdEntity.toUpdateLaboratoryResearchIdDataEntity() =
    UpdateLaboratoryResearchIdDataEntity(
        laboratoryResearchId = laboratoryResearchId,
        highDensityCholesterol = highDensityCholesterol,
        lowDensityCholesterol = lowDensityCholesterol,
        triglycerides = triglycerides,
        lipoprotein = lipoprotein,
        highlySensitiveCReactiveProtein = highlySensitiveCReactiveProtein,
        atherogenicityCoefficient = atherogenicityCoefficient,
        creatinine = creatinine,
        atheroscleroticPlaquesPresence = atheroscleroticPlaquesPresence,
    )

fun GetLaboratoryResearchResponseDataEntity.toGetLaboratoryResearchResponseEntity() =
    GetLaboratoryResearchResponseEntity(
        id = id,
        highDensityCholesterol = highDensityCholesterol,
        lowDensityCholesterol = lowDensityCholesterol,
        triglycerides = triglycerides,
        lipoprotein = lipoprotein,
        highlySensitiveCReactiveProtein = highlySensitiveCReactiveProtein,
        atherogenicityCoefficient = atherogenicityCoefficient,
        creatinine = creatinine,
        atheroscleroticPlaquesPresence = atheroscleroticPlaquesPresence,
        createdAt = createdAt,
    )

fun CreateLaboratoryResearchRequestEntity.toCreateLaboratoryResearchRequestDataEntity() =
    CreateLaboratoryResearchRequestDataEntity(
        highDensityCholesterol = highDensityCholesterol,
        lowDensityCholesterol = lowDensityCholesterol,
        triglycerides = triglycerides,
        lipoprotein = lipoprotein,
        highlySensitiveCReactiveProtein = highlySensitiveCReactiveProtein,
        atherogenicityCoefficient = atherogenicityCoefficient,
        creatinine = creatinine,
        atheroscleroticPlaquesPresence = atheroscleroticPlaquesPresence,
    )

fun CreateLaboratoryResearchResponseDataEntity.toCreateLaboratoryResearchResponseEntity() =
    CreateLaboratoryResearchResponseEntity(
        result = result,
    )