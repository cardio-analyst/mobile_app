package com.example.questionnaires_list.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.BaseFragment
import com.example.presentation.ResultViewTools
import com.example.presentation.observeResultsComponent
import com.example.questionnaires_list.R
import com.example.questionnaires_list.databinding.FragmentQuestionnairesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuestionnairesFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_questionnaires) {

    override val viewModel by viewModels<QuestionnairesViewModel>()

    private val binding by viewBinding(FragmentQuestionnairesBinding::bind)
    private val resultViewTools by lazy {
        ResultViewTools(
            fragment = this,
            root = binding.root,
            resultView = binding.resultView,
            onSessionExpired = viewModel.onSessionExpired,
        )
    }
    private val adapter by lazy { QuestionnairesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.pending_load))
            resultView.setTryAgainAction { viewModel.getOrReloadQuestionnaires() }
            reportButton.setOnClickListener {
                viewModel.launchReportScreen()
            }
        }

        observeQuestionnaires()
        viewModel.getOrReloadQuestionnaires()
    }

    override fun onStop() {
        super.onStop()
        viewModel.removeListeners()
    }

    private fun observeQuestionnaires() {
        viewModel.questionnairesList.observeResultsComponent(resultViewTools) { questionnaires ->
            adapter.questionnaireList = questionnaires
            binding.recyclerView.adapter = adapter
        }
    }

}