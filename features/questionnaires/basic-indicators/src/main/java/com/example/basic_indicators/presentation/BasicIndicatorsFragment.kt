package com.example.basic_indicators.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.basic_indicators.R
import com.example.basic_indicators.databinding.FragmentBasicIndicatorsBinding
import com.example.basic_indicators.domain.entities.GetBasicIndicatorResponseEntity
import com.example.presentation.BaseFragment
import com.example.presentation.databinding.PairActionButtonsBinding
import com.example.presentation.observeResults
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BasicIndicatorsFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_basic_indicators) {

    override val viewModel by activityViewModels<BasicIndicatorsViewModel>()

    private val binding by viewBinding(FragmentBasicIndicatorsBinding::bind)
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
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_basic_indicators_load))
            resultView.setTryAgainAction { viewModel.getOrReloadBasicIndicators() }
        }


        observeBasicIndicators()
        observeCreateBasicIndicator()
        observeUpdateBasicIndicator()
        observeCurrentBasicIndicatorChanged()
        viewModel.getOrReloadBasicIndicators()
    }

    override fun onPause() {
        super.onPause()
        viewPagerOnPageChangeCallback?.let { binding.viewPager.unregisterOnPageChangeCallback(it) }
        viewPagerOnPageChangeCallback = null
        binding.viewPager.adapter = null
    }

    private fun observeCurrentBasicIndicatorChanged() {
        viewModel.currentBasicIndicatorChanged.observe(viewLifecycleOwner) { changed ->
            if (changed) {
                buttonVisibility(View.VISIBLE)
            } else {
                buttonVisibility(View.INVISIBLE)
            }
        }
    }

    private fun observeBasicIndicators() {
        viewModel.basicIndicators.observeResults(
            this,
            binding.root,
            binding.resultView,
            { basicIndicators ->
                val adapter = BasicIndicatorsAdapter(childFragmentManager, lifecycle)
                initAdapter(basicIndicators, adapter)
                initViewPager(adapter)
                initButtonsLogic(adapter)
            })
    }

    private fun observeCreateBasicIndicator() {
        viewModel.createBasicIndicators.observeResults(
            this,
            binding.root,
            binding.resultView, {
                with(binding) {
                    resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_basic_indicators_load))
                    resultView.setTryAgainAction { viewModel.getOrReloadBasicIndicators() }
                }
                viewModel.getOrReloadBasicIndicators()
                viewModel.onSuccessCreateToast()
            })
    }

    private fun observeUpdateBasicIndicator() {
        viewModel.updateBasicIndicators.observeResults(
            this,
            binding.root,
            binding.resultView, {
                with(binding) {
                    resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_basic_indicators_load))
                    resultView.setTryAgainAction { viewModel.getOrReloadBasicIndicators() }
                }
                viewModel.getOrReloadBasicIndicators()
                viewModel.onSuccessChangeToast()
            })
    }


    private fun initAdapter(
        basicIndicators: List<GetBasicIndicatorResponseEntity>,
        adapter: BasicIndicatorsAdapter
    ) {
        // add default
        adapter.addFragment(
            BasicIndicatorsRecordFragment()
        )
        if (basicIndicators.isNotEmpty()) {
            // add others records
            basicIndicators.forEach {
                adapter.addFragment(
                    BasicIndicatorsRecordFragment().apply {
                        arguments = bundleOf(
                            BasicIndicatorsRecordFragment.ARG_ID to it.id
                        )
                    }
                )
            }
        }
    }

    private fun initButtonsLogic(adapter: BasicIndicatorsAdapter) = with(binding) {
        with(actionButtonsBinding) {
            // reset
            negativeButton.setOnClickListener {
                adapter.getFragment(viewPager.currentItem).resetFields()
                buttonVisibility(View.INVISIBLE)
            }
            // save
            positiveButton.setOnClickListener {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_basic_indicators_save))
                val basicIndicatorId =
                    adapter.getFragment(viewPager.currentItem).basicIndicatorId
                val basicIndicator = viewModel.basicIndicatorsChangedMap[basicIndicatorId]
                if (basicIndicator != null) {
                    if (basicIndicatorId == null) {
                        // create
                        val createBasicIndicatorRequestEntity =
                            viewModel.getCreateBasicIndicatorEntity(basicIndicator)
                        resultView.setTryAgainAction {
                            viewModel.reloadCreateBasicIndicator(
                                createBasicIndicatorRequestEntity
                            )
                        }
                        viewModel.createUserBasicIndicators(
                            createBasicIndicatorRequestEntity
                        )
                    } else {
                        // update
                        val updateBasicIndicatorRequestEntity =
                            viewModel.getUpdateBasicIndicatorEntity(basicIndicator)
                        resultView.setTryAgainAction {
                            viewModel.reloadUpdateBasicIndicator(
                                updateBasicIndicatorRequestEntity
                            )
                        }
                        viewModel.updateUserBasicIndicator(
                            updateBasicIndicatorRequestEntity
                        )
                    }
                    viewModel.basicIndicatorsChangedMap.remove(basicIndicatorId)
                }
                buttonVisibility(View.INVISIBLE)
            }
        }
    }

    private fun initViewPager(adapter: BasicIndicatorsAdapter) = with(binding) {
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
                val basicIndicatorId =
                    adapter.getFragment(viewPager.currentItem).basicIndicatorId
                if (viewModel.basicIndicatorsChangedMap.containsKey(basicIndicatorId)) {
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