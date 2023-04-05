package com.example.laboratory_research.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.laboratory_research.R
import com.example.laboratory_research.databinding.FragmentLaboratoryResearchBinding
import com.example.laboratory_research.domain.entities.GetLaboratoryResearchResponseEntity
import com.example.presentation.BaseFragment
import com.example.presentation.databinding.PairActionButtonsBinding
import com.example.presentation.observeResults
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaboratoryResearchFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_laboratory_research) {

    override val viewModel by activityViewModels<LaboratoryResearchViewModel>()

    private val binding by viewBinding(FragmentLaboratoryResearchBinding::bind)
    private val actionButtonsBinding by viewBinding(PairActionButtonsBinding::bind)
    private lateinit var buttonVisibility: (visibility: Int) -> Unit

    private var viewPagerOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

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
            resultView.setTryAgainAction { viewModel.getOrReloadLaboratoryResearches() }
        }


        observeLaboratoryResearches()
        observeCreateLaboratoryResearch()
        observeUpdateLaboratoryResearch()
        observeCurrentLaboratoryResearchChanged()
        viewModel.getOrReloadLaboratoryResearches()
    }

    override fun onPause() {
        super.onPause()
        viewPagerOnPageChangeCallback?.let { binding.viewPager.unregisterOnPageChangeCallback(it) }
        viewPagerOnPageChangeCallback = null
        binding.viewPager.adapter = null
    }

    private fun observeCurrentLaboratoryResearchChanged() {
        viewModel.currentLaboratoryResearchChanged.observe(viewLifecycleOwner) { changed ->
            if (changed) {
                buttonVisibility(View.VISIBLE)
            } else {
                buttonVisibility(View.INVISIBLE)
            }
        }
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
                    resultView.setTryAgainAction { viewModel.getOrReloadLaboratoryResearches() }
                }
                viewModel.getOrReloadLaboratoryResearches()
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
                    resultView.setTryAgainAction { viewModel.getOrReloadLaboratoryResearches() }
                }
                viewModel.getOrReloadLaboratoryResearches()
                viewModel.onSuccessChangeToast()
            })
    }


    private fun initAdapter(
        laboratoryResearches: List<GetLaboratoryResearchResponseEntity>,
        adapter: LaboratoryResearchAdapter
    ) {
        // add default
        adapter.addFragment(
            LaboratoryResearchRecordFragment()
        )
        if (laboratoryResearches.isNotEmpty()) {
            // add others records
            laboratoryResearches.forEach {
                adapter.addFragment(
                    LaboratoryResearchRecordFragment().apply {
                        arguments = bundleOf(
                            LaboratoryResearchRecordFragment.ARG_ID to it.id
                        )
                    }
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
                val laboratoryResearchId =
                    adapter.getFragment(viewPager.currentItem).laboratoryResearchId
                val laboratoryResearch =
                    viewModel.laboratoryResearchChangedMap[laboratoryResearchId]
                if (laboratoryResearch != null) {
                    if (laboratoryResearchId == null) {
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
                    viewModel.laboratoryResearchChangedMap.remove(laboratoryResearchId)
                }
                buttonVisibility(View.INVISIBLE)
            }
        }
    }

    private fun initViewPager(adapter: LaboratoryResearchAdapter) = with(binding) {
        viewPager.adapter = adapter
        viewPagerOnPageChangeCallback?.let { viewPager.unregisterOnPageChangeCallback(it) }

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
                val laboratoryResearchId =
                    adapter.getFragment(viewPager.currentItem).laboratoryResearchId
                if (viewModel.laboratoryResearchChangedMap.containsKey(laboratoryResearchId)) {
                    buttonVisibility(View.VISIBLE)
                } else {
                    buttonVisibility(View.INVISIBLE)
                }
                // new current page
                viewModel.viewPagerCurrentPagePosition = viewPager.currentItem
            }
        }
        viewPager.registerOnPageChangeCallback(viewPagerOnPageChangeCallback!!)
        viewPager.currentItem = getActualPagePosition(adapter.itemCount)
        indicator.createIndicators(
            if (adapter.itemCount > 5) 5 else adapter.itemCount,
            getIndicatePosition(adapter.itemCount, viewPager.currentItem)
        )
    }

    private fun getActualPagePosition(itemCount: Int): Int {
        return viewModel.viewPagerCurrentPagePosition?.let {
            if (viewModel.viewPagerCurrentPagePosition == 0 && itemCount >= 2) 1 else it
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
}