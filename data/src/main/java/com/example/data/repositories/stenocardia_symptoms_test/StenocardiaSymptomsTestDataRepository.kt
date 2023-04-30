package com.example.data.repositories.stenocardia_symptoms_test

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.R
import com.example.data.base.ContextResources
import com.example.data.repositories.stenocardia_symptoms_test.sources.StenocardiaSymptomsTestSource
import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StenocardiaSymptomsTestDataRepository @Inject constructor(
    private val stenocardiaSymptomsTestSource: StenocardiaSymptomsTestSource,
    private val userRepository: IUserDataRepository,
    uiAction: ContextResources,
) : IStenocardiaSymptomsTestDataRepository {

    /**
     * Stenocardia Symptoms Test questions
     * List<
     *      "Question title",
     *      Pair<"List of available answers", True = has risk or False = has not risk>,
     *      Multiple = 2 or Single Mode = 1 for choosing questions
     * >
     */
    override val questions: List<IStenocardiaSymptomsTestDataRepository.Question> = listOf(
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_1),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_no), false),
                Pair(uiAction.getString(R.string.stenocardia_answer_yes), true),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_2),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_no), false),
                Pair(uiAction.getString(R.string.stenocardia_answer_yes), true),
                Pair(
                    uiAction.getString(R.string.stenocardia_answer_never_walk_fast_and_climb_a_mountain),
                    false
                ),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_3),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_no), false),
                Pair(uiAction.getString(R.string.stenocardia_answer_yes), true),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_4),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_stop_or_go_slower), true),
                Pair(uiAction.getString(R.string.stenocardia_answer_keep_going), false),
                Pair(
                    uiAction.getString(R.string.stenocardia_answer_take_nitroglycerin_or_medications),
                    true
                ),
            ),
            2
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_5),
            listOf(
                Pair(
                    uiAction.getString(R.string.stenocardia_answer_pain_disappears_or_decreases),
                    true
                ),
                Pair(
                    uiAction.getString(R.string.stenocardia_answer_pain_does_not_disappear),
                    false
                ),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_6),
            listOf(
                Pair(
                    uiAction.getString(R.string.stenocardia_answer_after_10_15_minutes_or_faster),
                    true
                ),
                Pair(
                    uiAction.getString(R.string.stenocardia_answer_more_10_minutes_later),
                    false
                ),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_7),
            listOf(
                Pair(
                    uiAction.getString(R.string.stenocardia_answer_sternum_upper_or_middle_third),
                    false
                ),
                Pair(uiAction.getString(R.string.stenocardia_answer_sternum_lower_third), true),
                Pair(uiAction.getString(R.string.stenocardia_answer_left_side_chest_front), true),
                Pair(uiAction.getString(R.string.stenocardia_answer_left_hand), true),
                Pair(uiAction.getString(R.string.stenocardia_answer_other_areas), false),
            ),
            2
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_8),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_no), true),
                Pair(uiAction.getString(R.string.stenocardia_answer_yes), false),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_9),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_less_4_week), true),
                Pair(uiAction.getString(R.string.stenocardia_answer_less_1_month), false),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_10),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_less_2_week), false),
                Pair(uiAction.getString(R.string.stenocardia_answer_almost_every_day), true),
            ),
            1
        ),
        IStenocardiaSymptomsTestDataRepository.Question(
            uiAction.getString(R.string.stenocardia_question_11),
            listOf(
                Pair(uiAction.getString(R.string.stenocardia_answer_no), false),
                Pair(uiAction.getString(R.string.stenocardia_answer_yes), true),
            ),
            1
        ),
    )

    private val stenocardiaSymptomsLazyFlowSubject =
        LazyFlowSubject<Unit, StenocardiaSymptomsDataEntity> {
            doGetUserStenocardiaSymptoms()
        }

    private val stenocardiaSymptomsSaveLazyFlowSubject =
        LazyFlowSubject<StenocardiaSymptomsDataEntity, StenocardiaSymptomsDataEntity> { stenocardiaSymptomsDataEntity ->
            doSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity)
        }


    override fun getUserStenocardiaSymptoms(): Flow<ResultState<StenocardiaSymptomsDataEntity>> =
        stenocardiaSymptomsLazyFlowSubject.listen(Unit)

    override fun removeAllListenersGet() = stenocardiaSymptomsLazyFlowSubject.removeAllListeners()

    private suspend fun doGetUserStenocardiaSymptoms(): StenocardiaSymptomsDataEntity =
        wrapBackendExceptions(userRepository) {
            stenocardiaSymptomsTestSource.getUserStenocardiaSymptoms()
        }

    override fun setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity): Flow<ResultState<StenocardiaSymptomsDataEntity>> =
        stenocardiaSymptomsSaveLazyFlowSubject.listen(stenocardiaSymptomsDataEntity)

    private suspend fun doSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity) =
        wrapBackendExceptions(userRepository) {
            stenocardiaSymptomsTestSource.setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity)
        }

    override fun reloadGetUserStenocardiaSymptoms() =
        stenocardiaSymptomsLazyFlowSubject.reloadAll()

    override fun reloadSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity) =
        stenocardiaSymptomsSaveLazyFlowSubject.reloadArgument(stenocardiaSymptomsDataEntity)
}