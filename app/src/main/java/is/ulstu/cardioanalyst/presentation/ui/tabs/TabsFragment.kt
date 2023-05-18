package `is`.ulstu.cardioanalyst.presentation.ui.tabs

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentTabsBinding
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject

@AndroidEntryPoint
class TabsFragment @Inject constructor() : Fragment(R.layout.fragment_tabs) {

    @Inject
    lateinit var tabsController: TabsController
    private val viewModel by viewModels<TabsViewModel>()
    private lateinit var binding: FragmentTabsBinding
    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (getTabsStackEntryCount() > 0) {
                    tabsController.tabsNavController?.popBackStack()
                } else {
                    this.remove()
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback)
    }

    override fun onPause() {
        super.onPause()
        onBackPressedCallback.remove()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabsBinding.bind(view)

        val navController = getTabsNavController()
        val bottomNavigationView = binding.bottomNavigationView

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        tabsController.run {
            tabsNavController = navController
            tabsBottomNavigationView = bottomNavigationView
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabsController.run {
            tabsBottomNavigationView = null
            tabsNavController = null
        }
    }

    private fun getTabsNavController(): NavController {
        val navHost =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_tabs) as NavHostFragment
        return navHost.navController
    }

    private fun getTabsStackEntryCount(): Int {
        val navHost =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_tabs) as NavHostFragment
        return navHost.childFragmentManager.backStackEntryCount
    }

}