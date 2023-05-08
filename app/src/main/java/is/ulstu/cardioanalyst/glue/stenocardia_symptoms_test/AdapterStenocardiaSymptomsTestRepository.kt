package `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test

import com.example.common.flows.ResultState
import com.example.data.repositories.stenocardia_symptoms_test.IStenocardiaSymptomsTestDataDataRepository
import com.example.stenocardia_symptoms.domain.StenocardiaSymptomsTestRepository
import com.example.stenocardia_symptoms.domain.entities.StenocardiaSymptomsEntity
import `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test.mappers.toStenocardiaSymptomsDataEntity
import `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test.mappers.toStenocardiaSymptomsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterStenocardiaSymptomsTestRepository @Inject constructor(
    private val stenocardiaSymptomsTestDataRepository: IStenocardiaSymptomsTestDataDataRepository,
) : StenocardiaSymptomsTestRepository {

    override val questions: List<StenocardiaSymptomsTestRepository.Question> =
        stenocardiaSymptomsTestDataRepository.questions.map {
            StenocardiaSymptomsTestRepository.Question(
                questionName = it.questionName,
                questionsAnswers = it.questionsAnswers,
                choiceMode = it.choiceMode,
            )
        }

    override fun setUserStenocardiaSymptoms(stenocardiaSymptomsEntity: StenocardiaSymptomsEntity): Flow<ResultState<StenocardiaSymptomsEntity>> =
        stenocardiaSymptomsTestDataRepository.setUserStenocardiaSymptoms(stenocardiaSymptomsEntity.toStenocardiaSymptomsDataEntity())
            .map { resultState ->
                resultState.map { stenocardiaSymptomsDataEntity ->
                    stenocardiaSymptomsDataEntity.toStenocardiaSymptomsEntity()
                }
            }

    override fun reloadSetUserStenocardiaSymptoms(stenocardiaSymptomsEntity: StenocardiaSymptomsEntity) =
        stenocardiaSymptomsTestDataRepository.reloadSetUserStenocardiaSymptoms(
            stenocardiaSymptomsEntity.toStenocardiaSymptomsDataEntity()
        )

}