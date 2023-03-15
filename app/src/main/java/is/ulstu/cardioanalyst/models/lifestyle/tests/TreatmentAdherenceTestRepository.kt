package `is`.ulstu.cardioanalyst.models.lifestyle.tests

import `is`.ulstu.cardioanalyst.R
import com.example.presentation.uiactions.UiAction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreatmentAdherenceTestRepository @Inject constructor(
    uiAction: UiAction,
) {

    var results: Triple<Double, Double, Double>? = null
        get() {
            if (field != null) {
                val value = field
                field = null
                return value
            }
            return field
        }

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
    val questions: List<Question> = listOf(
        Question(
            0,
            uiAction.getString(R.string.treatment_adherence_question_1),
            answersKit[0]
        ),
        Question(
            1,
            uiAction.getString(R.string.treatment_adherence_question_2),
            answersKit[2]
        ),
        Question(
            2,
            uiAction.getString(R.string.treatment_adherence_question_3),
            answersKit[2]
        ),
        Question(
            3,
            uiAction.getString(R.string.treatment_adherence_question_4),
            answersKit[2]
        ),
        Question(
            4,
            uiAction.getString(R.string.treatment_adherence_question_5),
            answersKit[2]
        ),
        Question(
            5,
            uiAction.getString(R.string.treatment_adherence_question_6),
            answersKit[0]
        ),
        Question(
            6,
            uiAction.getString(R.string.treatment_adherence_question_7),
            answersKit[1]
        ),
        Question(
            7,
            uiAction.getString(R.string.treatment_adherence_question_8),
            answersKit[2]
        ),
        Question(
            8,
            uiAction.getString(R.string.treatment_adherence_question_9),
            answersKit[2]
        ),
        Question(
            9,
            uiAction.getString(R.string.treatment_adherence_question_10),
            answersKit[0]
        ),
        Question(
            10,
            uiAction.getString(R.string.treatment_adherence_question_11),
            answersKit[0]
        ),
        Question(
            11,
            uiAction.getString(R.string.treatment_adherence_question_12),
            answersKit[0]
        ),
        Question(
            12,
            uiAction.getString(R.string.treatment_adherence_question_13),
            answersKit[2]
        ),
        Question(
            13,
            uiAction.getString(R.string.treatment_adherence_question_14),
            answersKit[2]
        ),
        Question(
            14,
            uiAction.getString(R.string.treatment_adherence_question_15),
            answersKit[2]
        ),
        Question(
            15,
            uiAction.getString(R.string.treatment_adherence_question_16),
            answersKit[3]
        ),
        Question(
            16,
            uiAction.getString(R.string.treatment_adherence_question_17),
            answersKit[3]
        ),
        Question(
            17,
            uiAction.getString(R.string.treatment_adherence_question_18),
            answersKit[3]
        ),
        Question(
            18,
            uiAction.getString(R.string.treatment_adherence_question_19),
            answersKit[3]
        ),
        Question(
            19,
            uiAction.getString(R.string.treatment_adherence_question_20),
            answersKit[3]
        ),
        Question(
            20,
            uiAction.getString(R.string.treatment_adherence_question_21),
            answersKit[3]
        ),
        Question(
            21,
            uiAction.getString(R.string.treatment_adherence_question_22),
            answersKit[3]
        ),
        Question(
            22,
            uiAction.getString(R.string.treatment_adherence_question_23),
            answersKit[3]
        ),
        Question(
            23,
            uiAction.getString(R.string.treatment_adherence_question_24),
            answersKit[3]
        ),
        Question(
            24,
            uiAction.getString(R.string.treatment_adherence_question_25),
            answersKit[3]
        ),
    )

    data class Question(
        val questionNumber: Int,
        val questionName: String,
        val questionsAnswers: List<String>,
    )
}