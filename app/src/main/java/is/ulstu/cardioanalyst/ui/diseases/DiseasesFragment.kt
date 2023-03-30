package `is`.ulstu.cardioanalyst.ui.diseases

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.data.repositories.diseases.sources.entities.DiseasesMainEntity
import com.example.presentation.BaseFragment
import com.example.presentation.databinding.PairActionButtonsBinding
import com.example.presentation.observeResults
import dagger.hilt.android.AndroidEntryPoint
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentDiseasesBinding
import javax.inject.Inject

@AndroidEntryPoint
class DiseasesFragment @Inject constructor() : BaseFragment(R.layout.fragment_diseases) {

    override val viewModel by viewModels<DiseasesViewModel>()

    private val binding by viewBinding(FragmentDiseasesBinding::bind)
    private val actionButtonsBinding by viewBinding(PairActionButtonsBinding::bind)
    private lateinit var buttonVisibility: (visibility: Int) -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(actionButtonsBinding) {
            buttonVisibility = {
                negativeButton.visibility = it
                positiveButton.visibility = it
            }
            buttonVisibility(View.INVISIBLE)
        }

        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_diseases_load))
            resultView.setTryAgainAction { viewModel.getOrReloadDiseases() }
        }

        observeDiseases()
        observeDiseasesSave()
        viewModel.getOrReloadDiseases()
    }

    private fun observeDiseases() {
        viewModel.diseases.observeResults(this, binding.root, binding.resultView, { data ->
            with(binding) {
                val diseases = mapOf(
                    resources.getString(R.string.cvdPredisposed) to data.cvdPredisposed,
                    resources.getString(R.string.takesStatins) to data.takesStatins,
                    resources.getString(R.string.hasChronicKidneyDisease) to data.hasChronicKidneyDisease,
                    resources.getString(R.string.hasArterialHypertension) to data.hasArterialHypertension,
                    resources.getString(R.string.hasIschemicHeartDisease) to data.hasIschemicHeartDisease,
                    resources.getString(R.string.hasTypeTwoDiabetes) to data.hasTypeTwoDiabetes,
                    resources.getString(R.string.hadInfarctionOrStroke) to data.hadInfarctionOrStroke,
                    resources.getString(R.string.hasAtherosclerosis) to data.hasAtherosclerosis,
                    resources.getString(R.string.hasOtherCVD) to data.hasOtherCVD,
                )
                val adapter =
                    this@DiseasesFragment.context?.let {
                        ArrayAdapter(
                            it,
                            R.layout.simple_list_item_multiple_choice,
                            diseases.keys.toList()
                        )
                    }

                diseasesListView.adapter = adapter
                diseasesListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
                val diseasesValues = diseases.values.toList()
                diseasesValues.forEachIndexed { position, isSelected ->
                    if (isSelected) diseasesListView.setItemChecked(
                        position,
                        isSelected
                    )
                }

                diseasesListView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, _, _ ->
                        for (position in 0 until diseasesListView.count) {
                            if (diseasesListView.isItemChecked(position) != diseasesValues[position]) {
                                buttonVisibility(View.VISIBLE)
                                break
                            }
                            buttonVisibility(View.INVISIBLE)
                        }
                    }

                actionButtonsBinding.negativeButton.setOnClickListener {
                    diseasesValues.forEachIndexed { position, isSelected ->
                        diseasesListView.setItemChecked(position, isSelected)
                    }
                    buttonVisibility(View.INVISIBLE)
                }

                actionButtonsBinding.positiveButton.setOnClickListener {
                    val newDiseasesMainEntity = DiseasesMainEntity(
                        cvdPredisposed = diseasesListView.isItemChecked(0),
                        takesStatins = diseasesListView.isItemChecked(1),
                        hasChronicKidneyDisease = diseasesListView.isItemChecked(2),
                        hasArterialHypertension = diseasesListView.isItemChecked(3),
                        hasIschemicHeartDisease = diseasesListView.isItemChecked(4),
                        hasTypeTwoDiabetes = diseasesListView.isItemChecked(5),
                        hadInfarctionOrStroke = diseasesListView.isItemChecked(6),
                        hasAtherosclerosis = diseasesListView.isItemChecked(7),
                        hasOtherCVD = diseasesListView.isItemChecked(8)
                    )
                    with(binding) {
                        resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_diseases_save))
                        resultView.setTryAgainAction {
                            viewModel.reloadDiseasesSave(
                                newDiseasesMainEntity
                            )
                        }
                    }
                    viewModel.setUserDiseases(newDiseasesMainEntity)
                    buttonVisibility(View.INVISIBLE)
                }
            }
        })
    }

    private fun observeDiseasesSave() {
        viewModel.diseasesSave.observeResults(this, binding.root, binding.resultView, { data ->
            viewModel.onSuccessSaveToast()
            with(binding) {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_diseases_load))
                resultView.setTryAgainAction { viewModel.getOrReloadDiseases() }
            }
        })
    }

}