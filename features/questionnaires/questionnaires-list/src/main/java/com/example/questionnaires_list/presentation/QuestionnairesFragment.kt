package com.example.questionnaires_list.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.BaseFragment
import com.example.questionnaires_list.R
import com.example.questionnaires_list.databinding.FragmentQuestionnairesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuestionnairesFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_questionnaires) {

    override val viewModel by viewModels<QuestionnairesViewModel>()

    private val binding by viewBinding(FragmentQuestionnairesBinding::bind)
    private val questionnairesAdapter by lazy {
        QuestionnairesAdapter().apply {
            questionnaireList = viewModel.getQuestionnairesList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = questionnairesAdapter
            reportButton.setOnClickListener {
                viewModel.launchReportScreen()
            }
        }
    }

}