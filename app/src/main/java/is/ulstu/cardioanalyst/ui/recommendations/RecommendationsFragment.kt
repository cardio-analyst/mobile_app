package `is`.ulstu.cardioanalyst.ui.recommendations

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentDiseasesBinding
import `is`.ulstu.cardioanalyst.databinding.FragmentRecommendationsBinding
import `is`.ulstu.cardioanalyst.databinding.PairActionButtonsBinding
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding

class RecommendationsFragment: BaseFragment(R.layout.fragment_recommendations) {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<RecommendationsViewModel>()

    private val binding by viewBinding(FragmentRecommendationsBinding::bind)
    private lateinit var viewPagerOnPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_recommendations_load))
            resultView.setTryAgainAction { viewModel.reloadRecommendations() }
        }

        observeRecommendations()
    }

    private fun observeRecommendations() {
        viewModel.recommendations.observeResults(this, binding.root, binding.resultView, { recommendations ->
            val adapter = context?.let { RecommendationsAdapter(it, recommendations) }
            if (adapter != null) {
                initViewPager(adapter)
            }
        })
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