package `is`.ulstu.cardioanalyst.models.lifestyle.tests

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons

class TreatmentAdherenceTestRepository {

    /**
     * Answers Kits for questions
     */
    private val answersKit: List<List<String>> = listOf(
        listOf(
            Singletons.getString(R.string.treatment_adherence_answer_not_important_high),
            Singletons.getString(R.string.treatment_adherence_answer_not_important_medium),
            Singletons.getString(R.string.treatment_adherence_answer_not_important_low),
            Singletons.getString(R.string.treatment_adherence_answer_important_low),
            Singletons.getString(R.string.treatment_adherence_answer_important_medium),
            Singletons.getString(R.string.treatment_adherence_answer_important_high),
        ),
        listOf(
            Singletons.getString(R.string.treatment_adherence_answer_not_important_not),
            Singletons.getString(R.string.treatment_adherence_answer_not_important_medium),
            Singletons.getString(R.string.treatment_adherence_answer_not_important_low),
            Singletons.getString(R.string.treatment_adherence_answer_important_low),
            Singletons.getString(R.string.treatment_adherence_answer_important_medium),
            Singletons.getString(R.string.treatment_adherence_answer_important_high),
        ),
        listOf(
            Singletons.getString(R.string.treatment_adherence_answer_difficult_high),
            Singletons.getString(R.string.treatment_adherence_answer_difficult_medium),
            Singletons.getString(R.string.treatment_adherence_answer_difficult_low),
            Singletons.getString(R.string.treatment_adherence_answer_not_difficult_high),
            Singletons.getString(R.string.treatment_adherence_answer_not_difficult_medium),
            Singletons.getString(R.string.treatment_adherence_answer_not_difficult_low),
        ),
        listOf(
            Singletons.getString(R.string.treatment_adherence_answer_not_will_high),
            Singletons.getString(R.string.treatment_adherence_answer_not_will_medium),
            Singletons.getString(R.string.treatment_adherence_answer_not_will_low),
            Singletons.getString(R.string.treatment_adherence_answer_will_high),
            Singletons.getString(R.string.treatment_adherence_answer_will_medium),
            Singletons.getString(R.string.treatment_adherence_answer_will_low),
        )
    )

    /**
     * Treatment Adherence Test questions
     * Pair<"Question title", "List of available answers">
     */
    val questions: List<Question> = listOf(
        Question(
            0,
            Singletons.getString(R.string.treatment_adherence_question_1),
            answersKit[0]
        ),
        Question(
            1,
            Singletons.getString(R.string.treatment_adherence_question_2),
            answersKit[2]
        ),
        Question(
            2,
            Singletons.getString(R.string.treatment_adherence_question_3),
            answersKit[2]
        ),
        Question(
            3,
            Singletons.getString(R.string.treatment_adherence_question_4),
            answersKit[2]
        ),
        Question(
            4,
            Singletons.getString(R.string.treatment_adherence_question_5),
            answersKit[2]
        ),
        Question(
            5,
            Singletons.getString(R.string.treatment_adherence_question_6),
            answersKit[0]
        ),
        Question(
            6,
            Singletons.getString(R.string.treatment_adherence_question_7),
            answersKit[1]
        ),
        Question(
            7,
            Singletons.getString(R.string.treatment_adherence_question_8),
            answersKit[2]
        ),
        Question(
            8,
            Singletons.getString(R.string.treatment_adherence_question_9),
            answersKit[2]
        ),
        Question(
            9,
            Singletons.getString(R.string.treatment_adherence_question_10),
            answersKit[0]
        ),
        Question(
            10,
            Singletons.getString(R.string.treatment_adherence_question_11),
            answersKit[0]
        ),
        Question(
            11,
            Singletons.getString(R.string.treatment_adherence_question_12),
            answersKit[0]
        ),
        Question(
            12,
            Singletons.getString(R.string.treatment_adherence_question_13),
            answersKit[2]
        ),
        Question(
            13,
            Singletons.getString(R.string.treatment_adherence_question_14),
            answersKit[2]
        ),
        Question(
            14,
            Singletons.getString(R.string.treatment_adherence_question_15),
            answersKit[2]
        ),
        Question(
            15,
            Singletons.getString(R.string.treatment_adherence_question_16),
            answersKit[3]
        ),
        Question(
            16,
            Singletons.getString(R.string.treatment_adherence_question_17),
            answersKit[3]
        ),
        Question(
            17,
            Singletons.getString(R.string.treatment_adherence_question_18),
            answersKit[3]
        ),
        Question(
            18,
            Singletons.getString(R.string.treatment_adherence_question_19),
            answersKit[3]
        ),
        Question(
            19,
            Singletons.getString(R.string.treatment_adherence_question_20),
            answersKit[3]
        ),
        Question(
            20,
            Singletons.getString(R.string.treatment_adherence_question_21),
            answersKit[3]
        ),
        Question(
            21,
            Singletons.getString(R.string.treatment_adherence_question_22),
            answersKit[3]
        ),
        Question(
            22,
            Singletons.getString(R.string.treatment_adherence_question_23),
            answersKit[3]
        ),
        Question(
            23,
            Singletons.getString(R.string.treatment_adherence_question_24),
            answersKit[3]
        ),
        Question(
            24,
            Singletons.getString(R.string.treatment_adherence_question_25),
            answersKit[3]
        ),
    )

    data class Question(
        val questionNumber: Int,
        val questionName: String,
        val questionsAnswers: List<String>,
    )
}