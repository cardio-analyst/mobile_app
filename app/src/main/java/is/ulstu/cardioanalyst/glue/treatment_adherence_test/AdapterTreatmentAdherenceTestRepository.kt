package `is`.ulstu.cardioanalyst.glue.treatment_adherence_test

import com.example.common.flows.ResultState
import com.example.data.repositories.treatment_adherence_test.ITreatmentAdherenceTestDataRepository
import com.example.treatment_adherence.domain.TreatmentAdherenceTestRepository
import com.example.treatment_adherence.domain.entities.TreatmentAdherenceEntity
import `is`.ulstu.cardioanalyst.glue.treatment_adherence_test.mappers.toTreatmentAdherenceDataEntity
import `is`.ulstu.cardioanalyst.glue.treatment_adherence_test.mappers.toTreatmentAdherenceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterTreatmentAdherenceTestRepository @Inject constructor(
    private val treatmentAdherenceTestDataRepository: ITreatmentAdherenceTestDataRepository,
) : TreatmentAdherenceTestRepository {

    override val questions: List<TreatmentAdherenceTestRepository.Question> =
        treatmentAdherenceTestDataRepository.questions.map {
            TreatmentAdherenceTestRepository.Question(
                questionNumber = it.questionNumber,
                questionName = it.questionName,
                questionsAnswers = it.questionsAnswers,
            )
        }

    override fun setUserTreatmentAdherence(treatmentAdherenceEntity: TreatmentAdherenceEntity): Flow<ResultState<TreatmentAdherenceEntity>> =
        treatmentAdherenceTestDataRepository.setUserTreatmentAdherence(treatmentAdherenceEntity.toTreatmentAdherenceDataEntity())
            .map { resultState ->
                resultState.map { treatmentAdherenceDataEntity ->
                    treatmentAdherenceDataEntity.toTreatmentAdherenceEntity()
                }
            }

    override fun reloadSetUserTreatmentAdherence(treatmentAdherenceEntity: TreatmentAdherenceEntity) =
        treatmentAdherenceTestDataRepository.reloadSetUserTreatmentAdherence(
            treatmentAdherenceEntity.toTreatmentAdherenceDataEntity()
        )
}