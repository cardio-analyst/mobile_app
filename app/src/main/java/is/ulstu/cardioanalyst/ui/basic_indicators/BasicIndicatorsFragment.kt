package `is`.ulstu.cardioanalyst.ui.basic_indicators

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentBasicIndicatorsBinding
import `is`.ulstu.cardioanalyst.databinding.PairActionButtonsBinding
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.GetBasicIndicatorResponseEntity
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding

class BasicIndicatorsFragment : BaseFragment(R.layout.fragment_basic_indicators),
    BasicIndicatorsRecordFragment.BasicIndicatorRecordListener {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<BasicIndicatorsViewModel>()

    private val binding by viewBinding(FragmentBasicIndicatorsBinding::bind)
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
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_basic_indicators_load))
            resultView.setTryAgainAction { viewModel.reloadBasicIndicators() }
        }


        observeBasicIndicators()
        observeCreateBasicIndicator()
        observeUpdateBasicIndicator()
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
                    resultView.setTryAgainAction { viewModel.reloadBasicIndicators() }
                    viewPagerCurrentPagePosition = viewPager.currentItem
                }
                viewModel.reloadBasicIndicators()
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
                    resultView.setTryAgainAction { viewModel.reloadBasicIndicators() }
                    viewPagerCurrentPagePosition = viewPager.currentItem
                }
                viewModel.reloadBasicIndicators()
                viewModel.onSuccessChangeToast()
            })
    }


    private fun initAdapter(
        basicIndicators: List<GetBasicIndicatorResponseEntity>,
        adapter: BasicIndicatorsAdapter
    ) {
        // add default
        adapter.addFragment(
            BasicIndicatorsRecordFragment(
                viewModel.getDefaultLBasicIndicatorRecord(),
                this@BasicIndicatorsFragment
            )
        )
        if (basicIndicators.isNotEmpty()) {
            // add others records
            basicIndicators.forEach {
                adapter.addFragment(
                    BasicIndicatorsRecordFragment(
                        it,
                        this@BasicIndicatorsFragment
                    )
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
                val basicIndicator =
                    adapter.getFragment(viewPager.currentItem).currentBasicIndicator
                if (basicIndicator.id == null) {
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
                buttonVisibility(View.INVISIBLE)
            }
        }
    }

    private fun initViewPager(adapter: BasicIndicatorsAdapter) = with(binding) {
        viewPager.adapter = adapter
        // unregister previous Page Change Callback
        if (this@BasicIndicatorsFragment::viewPagerOnPageChangeCallback.isInitialized)
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