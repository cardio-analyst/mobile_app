package com.example.data.repositories.treatment_adherence_test

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.R
import com.example.data.base.ContextResources
import com.example.data.repositories.treatment_adherence_test.sources.TreatmentAdherenceTestSource
import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreatmentAdherenceTestDataRepository @Inject constructor(
    private val treatmentAdherenceTestSource: TreatmentAdherenceTestSource,
    private val userRepository: IUserDataRepository,
    uiAction: ContextResources,
) : ITreatmentAdherenceTestDataRepository {

    /**
     * Answers Kits for questions
     */
    private val answersKit: List<List<String>> = listOf(
        listOf(
            uiAction.getString(R.string.treatment_adherence_answer_not_important_high),
            uiAction.getString(R.string.treatment_adherence_answer_not_important_medium),
            uiAction.getString(R.string.treatment_adherence_answer_not_important_low),
            uiAction.getString(R.string.treatment_adherence_answer_important_low),
            uiAction.getString(R.string.treatment_adherence_answer_important_medium),
            uiAction.getString(R.string.treatment_adherence_answer_important_high),
        ),
        listOf(
            uiAction.getString(R.string.treatment_adherence_answer_not_important_not),
            uiAction.getString(R.string.treatment_adherence_answer_not_important_medium),
            uiAction.getString(R.string.treatment_adherence_answer_not_important_low),
            uiAction.getString(R.string.treatment_adherence_answer_important_low),
            uiAction.getString(R.string.treatment_adherence_answer_important_medium),
            uiAction.getString(R.string.treatment_adherence_answer_important_high),
        ),
        listOf(
            uiAction.getString(R.string.treatment_adherence_answer_difficult_high),
            uiAction.getString(R.string.treatment_adherence_answer_difficult_medium),
            uiAction.getString(R.string.treatment_adherence_answer_difficult_low),
            uiAction.getString(R.string.treatment_adherence_answer_not_difficult_high),
            uiAction.getString(R.string.treatment_adherence_answer_not_difficult_medium),
            uiAction.getString(R.string.treatment_adherence_answer_not_difficult_low),
        ),
        listOf(
            uiAction.getString(R.string.treatment_adherence_answer_not_will_high),
            uiAction.getString(R.string.treatment_adherence_answer_not_will_medium),
            uiAction.getString(R.string.treatment_adherence_answer_not_will_low),
            uiAction.getString(R.string.treatment_adherence_answer_will_high),
            uiAction.getString(R.string.treatment_adherence_answer_will_medium),
            uiAction.getString(R.string.treatment_adherence_answer_will_low),
        )
    )

    /**
     * Treatment Adherence Test questions
     * Pair<"Question title", "List of available answers">
     */
    override val questions: List<ITreatmentAdherenceTestDataRepository.Question> = listOf(
        ITreatmentAdherenceTestDataRepository.Question(
            0,
            uiAction.getString(R.string.treatment_adherence_question_1),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            1,
            uiAction.getString(R.string.treatment_adherence_question_2),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            2,
            uiAction.getString(R.string.treatment_adherence_question_3),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            3,
            uiAction.getString(R.string.treatment_adherence_question_4),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            4,
            uiAction.getString(R.string.treatment_adherence_question_5),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            5,
            uiAction.getString(R.string.treatment_adherence_question_6),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            6,
            uiAction.getString(R.string.treatment_adherence_question_7),
            answersKit[1]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            7,
            uiAction.getString(R.string.treatment_adherence_question_8),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            8,
            uiAction.getString(R.string.treatment_adherence_question_9),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            9,
            uiAction.getString(R.string.treatment_adherence_question_10),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            10,
            uiAction.getString(R.string.treatment_adherence_question_11),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            11,
            uiAction.getString(R.string.treatment_adherence_question_12),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            12,
            uiAction.getString(R.string.treatment_adherence_question_13),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            13,
            uiAction.getString(R.string.treatment_adherence_question_14),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            14,
            uiAction.getString(R.string.treatment_adherence_question_15),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            15,
            uiAction.getString(R.string.treatment_adherence_question_16),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            16,
            uiAction.getString(R.string.treatment_adherence_question_17),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            17,
            uiAction.getString(R.string.treatment_adherence_question_18),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            18,
            uiAction.getString(R.string.treatment_adherence_question_19),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            19,
            uiAction.getString(R.string.treatment_adherence_question_20),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            20,
            uiAction.getString(R.string.treatment_adherence_question_21),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            21,
            uiAction.getString(R.string.treatment_adherence_question_22),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            22,
            uiAction.getString(R.string.treatment_adherence_question_23),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            23,
            uiAction.getString(R.string.treatment_adherence_question_24),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataRepository.Question(
            24,
            uiAction.getString(R.string.treatment_adherence_question_25),
            answersKit[3]
        ),
    )

    private val treatmentAdherenceLazyFlowSubject =
        LazyFlowSubject<Unit, TreatmentAdherenceDataEntity> {
            doGetUserTreatmentAdherence()
        }

    private val treatmentAdherenceSaveLazyFlowSubject =
        LazyFlowSubject<TreatmentAdherenceDataEntity, TreatmentAdherenceDataEntity> { treatmentAdherenceDataEntity ->
            doSetUserTreatmentAdherence(treatmentAdherenceDataEntity)
        }


    override fun getUserTreatmentAdherence(): Flow<ResultState<TreatmentAdherenceDataEntity>> =
        treatmentAdherenceLazyFlowSubject.listen(Unit)

    private suspend fun doGetUserTreatmentAdherence(): TreatmentAdherenceDataEntity =
        wrapBackendExceptions(userRepository) {
            treatmentAdherenceTestSource.getUserTreatmentAdherence()
        }

    override fun setUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity): Flow<ResultState<TreatmentAdherenceDataEntity>> =
        treatmentAdherenceSaveLazyFlowSubject.listen(treatmentAdherenceDataEntity)

    private suspend fun doSetUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity) =
        wrapBackendExceptions(userRepository) {
            treatmentAdherenceTestSource.setUserTreatmentAdherence(treatmentAdherenceDataEntity)
        }

    override fun reloadGetUserTreatmentAdherence() =
        treatmentAdherenceLazyFlowSubject.reloadAll()

    override fun reloadSetUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity) =
        treatmentAdherenceSaveLazyFlowSubject.reloadArgument(treatmentAdherenceDataEntity)
}