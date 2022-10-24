package `is`.ulstu.cardioanalyst.ui.diseases

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentAuthorizationBinding
import `is`.ulstu.cardioanalyst.databinding.FragmentDiseasesBinding
import `is`.ulstu.cardioanalyst.databinding.PairActionButtonsBinding
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class DiseasesFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<DiseasesViewModel>()

    private lateinit var binding: FragmentDiseasesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiseasesBinding.inflate(inflater, container, false)
        val actionButtonsBinding = PairActionButtonsBinding.bind(binding.root)
        val diseaseNames = viewModel.getUserDiseases().keys.toList()
        var diseaseChecked = viewModel.getUserDiseases().values.toList()
        actionButtonsBinding.negativeButton.visibility = View.INVISIBLE
        actionButtonsBinding.positiveButton.visibility = View.INVISIBLE

        val adapter =
            this.context?.let {
                ArrayAdapter(
                    it,
                    R.layout.simple_list_item_multiple_choice,
                    diseaseNames
                )
            }
        with(binding) {
            val buttonVisibility: (visibility: Int) -> Unit = {
                actionButtonsBinding.negativeButton.visibility = it
                actionButtonsBinding.positiveButton.visibility = it
            }

            diseasesListView.adapter = adapter
            diseasesListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
            diseaseChecked.forEachIndexed { position, isSelected ->
                if (isSelected) diseasesListView.setItemChecked(
                    position,
                    isSelected
                )
            }
            diseasesListView.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, _, _ ->
                    for (position in 0 until diseasesListView.count) {
                        if (diseasesListView.isItemChecked(position) != diseaseChecked[position]) {
                            buttonVisibility(View.VISIBLE)
                            break
                        }
                        buttonVisibility(View.INVISIBLE)
                    }
                }

            actionButtonsBinding.negativeButton.setOnClickListener {
                diseaseChecked.forEachIndexed { position, isSelected ->
                    diseasesListView.setItemChecked(position, isSelected)
                }
                buttonVisibility(View.INVISIBLE)
            }
            actionButtonsBinding.positiveButton.setOnClickListener {
                val newDiseaseChecked = mutableListOf<Boolean>()
                for (position in 0 until diseasesListView.count) {
                    newDiseaseChecked.add(diseasesListView.isItemChecked(position))
                }
                val map = diseaseNames.zip(newDiseaseChecked).toMap()
                viewModel.setUserDiseases(map)
                diseaseChecked = viewModel.getUserDiseases().values.toList()
                buttonVisibility(View.INVISIBLE)
            }
        }
        return binding.root
    }
}