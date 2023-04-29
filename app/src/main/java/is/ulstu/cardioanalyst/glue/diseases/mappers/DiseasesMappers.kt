package `is`.ulstu.cardioanalyst.glue.diseases.mappers

import com.example.data.repositories.diseases.sources.entities.DiseasesDataEntity
import com.example.diseases.domain.entities.DiseasesEntity

fun DiseasesDataEntity.toDiseasesEntity() = DiseasesEntity(
    cvdPredisposed = cvdPredisposed,
    takesStatins = takesStatins,
    hasChronicKidneyDisease = hasChronicKidneyDisease,
    hasArterialHypertension = hasArterialHypertension,
    hasIschemicHeartDisease = hasIschemicHeartDisease,
    hasTypeTwoDiabetes = hasTypeTwoDiabetes,
    hadInfarctionOrStroke = hadInfarctionOrStroke,
    hasAtherosclerosis = hasAtherosclerosis,
    hasOtherCVD = hasOtherCVD,
)

fun DiseasesEntity.toDiseasesDataEntity() = DiseasesDataEntity(
    cvdPredisposed = cvdPredisposed,
    takesStatins = takesStatins,
    hasChronicKidneyDisease = hasChronicKidneyDisease,
    hasArterialHypertension = hasArterialHypertension,
    hasIschemicHeartDisease = hasIschemicHeartDisease,
    hasTypeTwoDiabetes = hasTypeTwoDiabetes,
    hadInfarctionOrStroke = hadInfarctionOrStroke,
    hasAtherosclerosis = hasAtherosclerosis,
    hasOtherCVD = hasOtherCVD,
)