package `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test.mappers

import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import com.example.questionnaires_list.domain.entities.StenocardiaSymptoms
import com.example.stenocardia_symptoms.domain.entities.StenocardiaSymptomsEntity

fun StenocardiaSymptomsDataEntity.toStenocardiaSymptoms() = StenocardiaSymptoms(
    anginaScore = anginaScore,
)

fun StenocardiaSymptomsDataEntity.toStenocardiaSymptomsEntity() = StenocardiaSymptomsEntity(
    anginaScore = anginaScore,
)

fun StenocardiaSymptomsEntity.toStenocardiaSymptomsDataEntity() = StenocardiaSymptomsDataEntity(
    anginaScore = anginaScore,
)