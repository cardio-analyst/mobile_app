package `is`.ulstu.cardioanalyst.models.lifestyle.tests

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons

class StenocardiaSymptomsTestRepository {
    /**
     * Stenocardia Symptoms Test questions
     * List<
     *      "Question title",
     *      Pair<"List of available answers", True = has risk or False = has not risk>,
     *      Multiple = 2 or Single Mode = 1 for choosing questions
     * >
     */
    val questions: List<Question> = listOf(
        Question(
            Singletons.getString(R.string.stenocardia_question_1),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_no), false),
                Pair(Singletons.getString(R.string.stenocardia_answer_yes), true),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_2),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_no), false),
                Pair(Singletons.getString(R.string.stenocardia_answer_yes), true),
                Pair(
                    Singletons.getString(R.string.stenocardia_answer_never_walk_fast_and_climb_a_mountain),
                    false
                ),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_3),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_no), false),
                Pair(Singletons.getString(R.string.stenocardia_answer_yes), true),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_4),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_stop_or_go_slower), true),
                Pair(Singletons.getString(R.string.stenocardia_answer_keep_going), false),
                Pair(
                    Singletons.getString(R.string.stenocardia_answer_take_nitroglycerin_or_medications),
                    true
                ),
            ),
            2
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_5),
            listOf(
                Pair(
                    Singletons.getString(R.string.stenocardia_answer_pain_disappears_or_decreases),
                    true
                ),
                Pair(
                    Singletons.getString(R.string.stenocardia_answer_pain_does_not_disappear),
                    false
                ),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_6),
            listOf(
                Pair(
                    Singletons.getString(R.string.stenocardia_answer_after_10_15_minutes_or_faster),
                    true
                ),
                Pair(
                    Singletons.getString(R.string.stenocardia_answer_more_10_minutes_later),
                    false
                ),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_7),
            listOf(
                Pair(
                    Singletons.getString(R.string.stenocardia_answer_sternum_upper_or_middle_third),
                    false
                ),
                Pair(Singletons.getString(R.string.stenocardia_answer_sternum_lower_third), true),
                Pair(Singletons.getString(R.string.stenocardia_answer_left_side_chest_front), true),
                Pair(Singletons.getString(R.string.stenocardia_answer_left_hand), true),
                Pair(Singletons.getString(R.string.stenocardia_answer_other_areas), false),
            ),
            2
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_8),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_no), true),
                Pair(Singletons.getString(R.string.stenocardia_answer_yes), false),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_9),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_less_4_week), true),
                Pair(Singletons.getString(R.string.stenocardia_answer_less_1_month), false),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_10),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_less_2_week), false),
                Pair(Singletons.getString(R.string.stenocardia_answer_almost_every_day), true),
            ),
            1
        ),
        Question(
            Singletons.getString(R.string.stenocardia_question_11),
            listOf(
                Pair(Singletons.getString(R.string.stenocardia_answer_no), false),
                Pair(Singletons.getString(R.string.stenocardia_answer_yes), true),
            ),
            1
        ),
    )

    data class Question(
        val questionName: String,
        val questionsAnswers: List<Pair<String, Boolean>>,
        val choiceMode: Int
    )
}