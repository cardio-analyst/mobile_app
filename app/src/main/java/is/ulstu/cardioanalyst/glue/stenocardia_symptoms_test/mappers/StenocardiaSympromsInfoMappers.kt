package `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test.mappers

import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import com.example.questionnaires_list.domain.entities.StenocardiaSymptoms

fun StenocardiaSymptomsDataEntity.toStenocardiaSymptoms() = StenocardiaSymptoms(
    anginaScore = anginaScore,
)