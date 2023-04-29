package `is`.ulstu.cardioanalyst.glue.treatment_adherence_test.mappers

import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import com.example.questionnaires_list.domain.entities.TreatmentAdherence
import com.example.treatment_adherence.domain.entities.TreatmentAdherenceEntity

fun TreatmentAdherenceDataEntity.toTreatmentAdherence() = TreatmentAdherence(
    adherenceDrugTherapy = adherenceDrugTherapy,
    adherenceMedicalSupport = adherenceMedicalSupport,
    adherenceLifestyleMod = adherenceLifestyleMod,
)

fun TreatmentAdherenceDataEntity.toTreatmentAdherenceEntity() = TreatmentAdherenceEntity(
    adherenceDrugTherapy = adherenceDrugTherapy,
    adherenceMedicalSupport = adherenceMedicalSupport,
    adherenceLifestyleMod = adherenceLifestyleMod,
)

fun TreatmentAdherenceEntity.toTreatmentAdherenceDataEntity() = TreatmentAdherenceDataEntity(
    adherenceDrugTherapy = adherenceDrugTherapy,
    adherenceMedicalSupport = adherenceMedicalSupport,
    adherenceLifestyleMod = adherenceLifestyleMod,
)