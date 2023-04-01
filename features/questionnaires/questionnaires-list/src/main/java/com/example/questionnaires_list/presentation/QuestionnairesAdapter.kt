package com.example.questionnaires_list.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.questionnaires_list.databinding.ItemQuestionnaireBinding
import com.example.questionnaires_list.domain.entities.Questionnaire

class QuestionnairesDiffUtilCallback(
    private val oldQuestionnaireList: List<Questionnaire>,
    private val newQuestionnaireList: List<Questionnaire>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldQuestionnaireList.size

    override fun getNewListSize(): Int = newQuestionnaireList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldQuestionnaireList[oldItemPosition]
        val newEvent = newQuestionnaireList[newItemPosition]
        return oldEvent.title == newEvent.title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCategory = oldQuestionnaireList[oldItemPosition]
        val newCategory = newQuestionnaireList[newItemPosition]
        return oldCategory == newCategory
    }

}

/**
 * Adapter for questionnaires recyclerView in Questionnaires screen
 * @see Questionnaire
 */
class QuestionnairesAdapter :
    RecyclerView.Adapter<QuestionnairesAdapter.QuestionnairesViewHolder>() {
    var questionnaireList: List<Questionnaire> = emptyList()
        set(value) {
            val diffUtilCallback = QuestionnairesDiffUtilCallback(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionnairesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionnaireBinding.inflate(inflater, parent, false)
        return QuestionnairesViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: QuestionnairesViewHolder, position: Int) {
        val questionnaire = questionnaireList[position]
        with(holder.binding) {
            title.text = questionnaire.title
            description.text = questionnaire.description
            root.setOnClickListener {
                questionnaire.onClick()
            }
        }
    }

    override fun getItemCount() = questionnaireList.size

    class QuestionnairesViewHolder(val binding: ItemQuestionnaireBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root)
}