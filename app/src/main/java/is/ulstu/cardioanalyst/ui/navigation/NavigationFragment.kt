package `is`.ulstu.cardioanalyst.ui.navigation

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentLaboratoryResearchBinding
import `is`.ulstu.cardioanalyst.databinding.FragmentNavigationBinding
import `is`.ulstu.cardioanalyst.ui.enums.TabItem
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout

class NavigationFragment : BaseFragment(R.layout.fragment_navigation) {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<NavigationViewModel>()

    private val binding by viewBinding(FragmentNavigationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onChooseSettingsMode(TabItem.GENERAL_INFO)
        with(binding) {
            tabNameTextView.text = TabItem.GENERAL_INFO.tabName
            profileButton.setOnClickListener {
                viewModel.onChooseSettingsMode(TabItem.PROFILE)
                tabNameTextView.text = TabItem.PROFILE.tabName
            }
            tabLayout.addOnTabSelectedListener(tabSelectedListener)
        }
    }

    private fun onTabSelected(position: Int) {
        when (position) {
            TabItem.GENERAL_INFO.position -> {
                viewModel.onChooseSettingsMode(TabItem.GENERAL_INFO)
                binding.tabNameTextView.text = TabItem.GENERAL_INFO.tabName
            }
            TabItem.EXTRA.position -> {
                viewModel.onChooseSettingsMode(TabItem.EXTRA)
                binding.tabNameTextView.text = TabItem.EXTRA.tabName
            }
        }
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