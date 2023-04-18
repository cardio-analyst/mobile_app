package com.example.questionnaires_list.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.questionnaires_list.R
import com.example.questionnaires_list.databinding.ItemQuestionnaireBinding
import com.example.questionnaires_list.databinding.ItemStenocardiaSymptomsTestBinding
import com.example.questionnaires_list.databinding.ItemTreatmentAdherenceTestBinding
import com.example.questionnaires_list.presentation.entities.Questionnaire
import com.example.questionnaires_list.presentation.entities.QuestionnaireItem
import com.example.questionnaires_list.presentation.entities.StenocardiaSymptomsTest
import com.example.questionnaires_list.presentation.entities.TreatmentAdherenceTest

class QuestionnairesDiffUtilCallback(
    private val oldQuestionnaireList: List<QuestionnaireItem>,
    private val newQuestionnaireList: List<QuestionnaireItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldQuestionnaireList.size

    override fun getNewListSize(): Int = newQuestionnaireList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldQuestionnaireList[oldItemPosition]
        val new = newQuestionnaireList[newItemPosition]
        return old.viewType == new.viewType
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldQuestionnaireList[oldItemPosition]
        val new = newQuestionnaireList[newItemPosition]
        return old == new
    }

}

/**
 * Adapter for questionnaires recyclerView in Questionnaires screen
 * @see Questionnaire
 */
class QuestionnairesAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var questionnaireList: List<QuestionnaireItem> = emptyList()
        set(value) {
            val diffUtilCallback = QuestionnairesDiffUtilCallback(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    override fun getItemViewType(position: Int): Int = questionnaireList[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> {
                val binding = ItemStenocardiaSymptomsTestBinding.inflate(inflater, parent, false)
                StenocardiaSymptomsViewHolder(binding, parent.context)
            }
            2 -> {
                val binding = ItemTreatmentAdherenceTestBinding.inflate(inflater, parent, false)
                TreatmentAdherenceViewHolder(binding, parent.context)
            }
            else -> {
                val binding = ItemQuestionnaireBinding.inflate(inflater, parent, false)
                QuestionnairesViewHolder(binding, parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val questionnaire = questionnaireList[position]) {
            is StenocardiaSymptomsTest -> {
                with((holder as StenocardiaSymptomsViewHolder).binding) {
                    title.text = questionnaire.title
                    description.text = questionnaire.description
                    valueTextView.text = getResultStenocardiaSymptomsTest(
                        holder.context,
                        questionnaire.stenocardiaSymptoms.anginaScore,
                    )
                    root.setOnClickListener {
                        questionnaire.onClick()
                    }
                }
            }
            is TreatmentAdherenceTest -> {
                with((holder as TreatmentAdherenceViewHolder).binding) {
                    title.text = questionnaire.title
                    description.text = questionnaire.description
                    resultTextView.text = holder.context.getString(
                        R.string.treatment_adherence_test_result,
                        getResultTreatmentAdherenceTest(
                            holder.context,
                            questionnaire.generalResult
                        ),
                        getResultTreatmentAdherenceTest(
                            holder.context,
                            questionnaire.treatmentAdherence.adherenceDrugTherapy
                        ),
                        getResultTreatmentAdherenceTest(
                            holder.context,
                            questionnaire.treatmentAdherence.adherenceMedicalSupport
                        ),
                        getResultTreatmentAdherenceTest(
                            holder.context,
                            questionnaire.treatmentAdherence.adherenceLifestyleMod
                        ),
                    )
                    root.setOnClickListener {
                        questionnaire.onClick()
                    }
                }
            }
            is Questionnaire -> {
                with((holder as QuestionnairesViewHolder).binding) {
                    title.text = questionnaire.title
                    description.text = questionnaire.description
                    root.setOnClickListener {
                        questionnaire.onClick()
                    }
                }
            }
        }
    }

    override fun getItemCount() = questionnaireList.size

    private fun getResultStenocardiaSymptomsTest(context: Context, anginaScore: Int): String =
        if (anginaScore >= 2) {
            context.getString(R.string.angina_symptoms_symptoms)
        } else {
            context.getString(R.string.angina_symptoms_no_symptoms)
        }

    private fun getResultTreatmentAdherenceTest(context: Context, param: Double): String = when {
        param < 50 -> context.getString(R.string.treatment_adherence_low)
        param < 75 -> context.getString(R.string.treatment_adherence_medium)
        else -> context.getString(R.string.treatment_adherence_high)
    }

    class TreatmentAdherenceViewHolder(
        val binding: ItemTreatmentAdherenceTestBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root)

    class StenocardiaSymptomsViewHolder(
        val binding: ItemStenocardiaSymptomsTestBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root)

    class QuestionnairesViewHolder(val binding: ItemQuestionnaireBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root)
}