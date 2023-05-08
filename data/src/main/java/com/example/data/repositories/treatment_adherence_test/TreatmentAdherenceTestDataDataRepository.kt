package com.example.data.repositories.treatment_adherence_test

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.R
import com.example.data.base.ContextResources
import com.example.data.repositories.treatment_adherence_test.sources.TreatmentAdherenceTestSource
import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import com.example.data.repositories.users.IUserDataDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreatmentAdherenceTestDataDataRepository @Inject constructor(
    private val treatmentAdherenceTestSource: TreatmentAdherenceTestSource,
    private val userRepository: IUserDataDataRepository,
    uiAction: ContextResources,
) : ITreatmentAdherenceTestDataDataRepository {

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
    override val questions: List<ITreatmentAdherenceTestDataDataRepository.Question> = listOf(
        ITreatmentAdherenceTestDataDataRepository.Question(
            0,
            uiAction.getString(R.string.treatment_adherence_question_1),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            1,
            uiAction.getString(R.string.treatment_adherence_question_2),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            2,
            uiAction.getString(R.string.treatment_adherence_question_3),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            3,
            uiAction.getString(R.string.treatment_adherence_question_4),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            4,
            uiAction.getString(R.string.treatment_adherence_question_5),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            5,
            uiAction.getString(R.string.treatment_adherence_question_6),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            6,
            uiAction.getString(R.string.treatment_adherence_question_7),
            answersKit[1]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            7,
            uiAction.getString(R.string.treatment_adherence_question_8),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            8,
            uiAction.getString(R.string.treatment_adherence_question_9),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            9,
            uiAction.getString(R.string.treatment_adherence_question_10),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            10,
            uiAction.getString(R.string.treatment_adherence_question_11),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            11,
            uiAction.getString(R.string.treatment_adherence_question_12),
            answersKit[0]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            12,
            uiAction.getString(R.string.treatment_adherence_question_13),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            13,
            uiAction.getString(R.string.treatment_adherence_question_14),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            14,
            uiAction.getString(R.string.treatment_adherence_question_15),
            answersKit[2]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            15,
            uiAction.getString(R.string.treatment_adherence_question_16),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            16,
            uiAction.getString(R.string.treatment_adherence_question_17),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            17,
            uiAction.getString(R.string.treatment_adherence_question_18),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            18,
            uiAction.getString(R.string.treatment_adherence_question_19),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            19,
            uiAction.getString(R.string.treatment_adherence_question_20),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            20,
            uiAction.getString(R.string.treatment_adherence_question_21),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            21,
            uiAction.getString(R.string.treatment_adherence_question_22),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            22,
            uiAction.getString(R.string.treatment_adherence_question_23),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
            23,
            uiAction.getString(R.string.treatment_adherence_question_24),
            answersKit[3]
        ),
        ITreatmentAdherenceTestDataDataRepository.Question(
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

    override fun removeAllListenersGet() = treatmentAdherenceLazyFlowSubject.removeAllListeners()

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