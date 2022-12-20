package `is`.ulstu.cardioanalyst.ui.navigation

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentNavigationBinding
import `is`.ulstu.cardioanalyst.ui.enums.TabItem
import `is`.ulstu.foundation.views.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavigationFragment @Inject constructor() : BaseFragment(R.layout.fragment_navigation) {

    override val viewModel by viewModels<NavigationViewModel>()

    private val binding by viewBinding(FragmentNavigationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val initTab = viewModel.currentTab
        with(binding) {
            profileButton.setOnClickListener {
                viewModel.onChooseSettingsMode(TabItem.PROFILE)
                tabNameTextView.text = TabItem.PROFILE.tabName
            }
            tabLayout.addOnTabSelectedListener(tabSelectedListener)
            if (initTab == TabItem.PROFILE)
                profileButton.performClick()
            else
                tabLayout.selectTab(tabLayout.getTabAt(initTab.position), true)
        }
    }

    private fun onTabSelected(position: Int) {
        when (position) {
            TabItem.DISEASES.position -> {
                viewModel.onChooseSettingsMode(TabItem.DISEASES)
                binding.tabNameTextView.text = TabItem.DISEASES.tabName
            }
            TabItem.HEART_INDICATORS.position -> {
                viewModel.onChooseSettingsMode(TabItem.HEART_INDICATORS)
                binding.tabNameTextView.text = TabItem.HEART_INDICATORS.tabName
            }
            TabItem.EXTRA.position -> {
                viewModel.onChooseSettingsMode(TabItem.EXTRA)
                binding.tabNameTextView.text = TabItem.EXTRA.tabName
            }
            TabItem.LIFESTYLE.position -> {
                viewModel.onChooseSettingsMode(TabItem.LIFESTYLE)
                binding.tabNameTextView.text = TabItem.LIFESTYLE.tabName
            }
            TabItem.RECOMMENDATION.position -> {
                viewModel.onChooseSettingsMode(TabItem.RECOMMENDATION)
                binding.tabNameTextView.text = TabItem.RECOMMENDATION.tabName
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            this@NavigationFragment.onTabSelected(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {
            this@NavigationFragment.onTabSelected(tab.position)
        }
    }
}