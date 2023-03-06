package `is`.ulstu.cardioanalyst.ui.recommendations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentRecommendationsBinding
import `is`.ulstu.foundation.model.ResultViewTools
import `is`.ulstu.foundation.model.observeResultsComponent
import `is`.ulstu.foundation.views.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class RecommendationsFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_recommendations) {

    override val viewModel by viewModels<RecommendationsViewModel>()

    private val binding by viewBinding(FragmentRecommendationsBinding::bind)
    private lateinit var viewPagerOnPageChangeCallback: ViewPager2.OnPageChangeCallback
    private val resultViewTools by lazy {
        ResultViewTools(this, binding.root, binding.resultView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_recommendations_load))
            resultView.setTryAgainAction { viewModel.getOrReloadRecommendations() }
        }

        observeRecommendations()
        viewModel.getOrReloadRecommendations()
    }

    private fun observeRecommendations() {
        viewModel.recommendations.observeResultsComponent(resultViewTools) { recommendations ->
            val adapter = context?.let { RecommendationsAdapter(it, recommendations) }
            if (adapter != null) {
                initViewPager(adapter)
            }
        }
    }

    private fun initViewPager(adapter: RecommendationsAdapter) = with(binding) {
        viewPager.adapter = adapter
        // unregister previous Page Change Callback
        if (this@RecommendationsFragment::viewPagerOnPageChangeCallback.isInitialized)
            viewPager.unregisterOnPageChangeCallback(viewPagerOnPageChangeCallback)

        viewPagerOnPageChangeCallback = object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                indicator.animatePageSelected(getIndicatePosition(adapter.itemCount, position))
            }
        }
        viewPager.registerOnPageChangeCallback(viewPagerOnPageChangeCallback)
        viewPager.currentItem = 0
        indicator.createIndicators(
            if (adapter.itemCount > 5) 5 else adapter.itemCount,
            getIndicatePosition(adapter.itemCount, viewPager.currentItem)
        )
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