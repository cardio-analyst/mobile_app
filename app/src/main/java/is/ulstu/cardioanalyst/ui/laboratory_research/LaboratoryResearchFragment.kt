package `is`.ulstu.cardioanalyst.ui.laboratory_research

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentLaboratoryResearchBinding
import `is`.ulstu.cardioanalyst.databinding.PairActionButtonsBinding
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.GetLaboratoryResearchResponseEntity
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding

class LaboratoryResearchFragment : BaseFragment(R.layout.fragment_laboratory_research),
    LaboratoryResearchRecordFragment.LaboratoryResearchRecordListener {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<LaboratoryResearchViewModel>()

    private val binding by viewBinding(FragmentLaboratoryResearchBinding::bind)
    private val actionButtonsBinding by viewBinding(PairActionButtonsBinding::bind)
    private lateinit var buttonVisibility: (visibility: Int) -> Unit

    private lateinit var viewPagerOnPageChangeCallback: ViewPager2.OnPageChangeCallback
    private var viewPagerCurrentPagePosition: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(actionButtonsBinding) {
            buttonVisibility = {
                negativeButton.visibility = it
                positiveButton.visibility = it
                binding.indicator.visibility = if (it == View.VISIBLE) {
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }

            }
            buttonVisibility(View.INVISIBLE)
            negativeButton.text = resources.getString(R.string.button_text_reset)
            positiveButton.text = resources.getString(R.string.button_text_add)
        }

        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_laboratory_research_load))
            resultView.setTryAgainAction { viewModel.reloadLaboratoryResearches() }
        }


        observeLaboratoryResearches()
        observeCreateLaboratoryResearch()
        observeUpdateLaboratoryResearch()
    }


    private fun observeLaboratoryResearches() {
        viewModel.laboratoryResearches.observeResults(
            this,
            binding.root,
            binding.resultView,
            { laboratoryResearches ->
                val adapter = LaboratoryResearchAdapter(childFragmentManager, lifecycle)
                initAdapter(laboratoryResearches, adapter)
                initViewPager(adapter)
                initButtonsLogic(adapter)
            })
    }

    private fun observeCreateLaboratoryResearch() {
        viewModel.createLaboratoryResearch.observeResults(
            this,
            binding.root,
            binding.resultView, {
                with(binding) {
                    resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_laboratory_research_load))
                    resultView.setTryAgainAction { viewModel.reloadLaboratoryResearches() }
                    viewPagerCurrentPagePosition = viewPager.currentItem
                }
                viewModel.reloadLaboratoryResearches()
                viewModel.onSuccessCreateToast()
            })
    }

    private fun observeUpdateLaboratoryResearch() {
        viewModel.updateLaboratoryResearch.observeResults(
            this,
            binding.root,
            binding.resultView, {
                with(binding) {
                    resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_laboratory_research_load))
                    resultView.setTryAgainAction { viewModel.reloadLaboratoryResearches() }
                    viewPagerCurrentPagePosition = viewPager.currentItem
                }
                viewModel.reloadLaboratoryResearches()
                viewModel.onSuccessChangeToast()
            })
    }


    private fun initAdapter(
        laboratoryResearches: List<GetLaboratoryResearchResponseEntity>,
        adapter: LaboratoryResearchAdapter
    ) {
        // add default
        adapter.addFragment(
            LaboratoryResearchRecordFragment(
                viewModel.getDefaultLaboratoryResearchRecord(),
                this@LaboratoryResearchFragment
            )
        )
        if (laboratoryResearches.isNotEmpty()) {
            // add others records
            laboratoryResearches.forEach {
                adapter.addFragment(
                    LaboratoryResearchRecordFragment(
                        it,
                        this@LaboratoryResearchFragment
                    )
                )
            }
        }
    }

    private fun initButtonsLogic(adapter: LaboratoryResearchAdapter) = with(binding) {
        with(actionButtonsBinding) {
            // reset
            negativeButton.setOnClickListener {
                adapter.getFragment(viewPager.currentItem).resetFields()
                buttonVisibility(View.INVISIBLE)
            }
            // save
            positiveButton.setOnClickListener {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_laboratory_research_save))
                val laboratoryResearch =
                    adapter.getFragment(viewPager.currentItem).currentLaboratoryResearch
                if (laboratoryResearch.id == null) {
                    // create
                    val createLaboratoryResearchRequestEntity =
                        viewModel.getCreateLaboratoryResearchEntity(laboratoryResearch)
                    resultView.setTryAgainAction {
                        viewModel.reloadCreateLaboratoryResearch(
                            createLaboratoryResearchRequestEntity
                        )
                    }
                    viewModel.createUserLaboratoryResearch(
                        createLaboratoryResearchRequestEntity
                    )
                } else {
                    // update
                    val updateLaboratoryResearchRequestEntity =
                        viewModel.getUpdateLaboratoryResearchEntity(laboratoryResearch)
                    resultView.setTryAgainAction {
                        viewModel.reloadUpdateLaboratoryResearch(
                            updateLaboratoryResearchRequestEntity
                        )
                    }
                    viewModel.updateUserLaboratoryResearch(
                        updateLaboratoryResearchRequestEntity
                    )
                }
                buttonVisibility(View.INVISIBLE)
            }
        }
    }

    private fun initViewPager(adapter: LaboratoryResearchAdapter) = with(binding) {
        viewPager.adapter = adapter
        // unregister previous Page Change Callback
        if (this@LaboratoryResearchFragment::viewPagerOnPageChangeCallback.isInitialized)
            viewPager.unregisterOnPageChangeCallback(viewPagerOnPageChangeCallback)

        viewPagerOnPageChangeCallback = object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                indicator.animatePageSelected(getIndicatePosition(adapter.itemCount, position))
                actionButtonsBinding.positiveButton.text = if (position == 0)
                    resources.getString(R.string.button_text_add)
                else
                    resources.getString(R.string.button_text_save)
                // hide or show buttons
                adapter.getFragment(viewPager.currentItem).checkDifference()
            }
        }
        viewPager.registerOnPageChangeCallback(viewPagerOnPageChangeCallback)
        viewPager.currentItem = getActualPagePosition(adapter.itemCount)
        indicator.createIndicators(
            if (adapter.itemCount > 5) 5 else adapter.itemCount,
            getIndicatePosition(adapter.itemCount, viewPager.currentItem)
        )
    }

    private fun getActualPagePosition(itemCount: Int): Int {
        return viewPagerCurrentPagePosition?.let {
            if (viewPagerCurrentPagePosition == 0 && itemCount >= 2) 1 else it
        } ?: if (itemCount >= 2) 1 else 0
    }

    private fun getIndicatePosition(itemCount: Int, position: Int): Int = if (itemCount > 5)
        when {
            position < 2 -> position
            position == itemCount - 2 -> 3
            position == itemCount - 1 -> 4
            else -> 2
        }
    else position

    override fun <T : Comparable<T>> makeToast(name: String, range: ClosedFloatingPointRange<T>) =
        viewModel.onOutOfRangeToast(name, range)

    override fun sengMessageChanges(isChanged: Boolean) {
        if (isChanged) {
            buttonVisibility(View.VISIBLE)
        } else {
            buttonVisibility(View.INVISIBLE)
        }
    }
}