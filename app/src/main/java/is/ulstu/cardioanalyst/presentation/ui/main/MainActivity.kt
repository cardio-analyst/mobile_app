package `is`.ulstu.cardioanalyst.presentation.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.ActivityMainBinding
import `is`.ulstu.cardioanalyst.presentation.controllers.MainController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainController: MainController
    private val activityViewModel by viewModels<ActivityScopeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (activityViewModel.isUserAuthorized()) {
            // Tabs screen
            prepareRootNavController(isSignedIn = true)
        } else {
            // Auth nav graph
            prepareRootNavController(isSignedIn = false)
        }
    }

    override fun onResume() {
        super.onResume()
        mainController.mainNavController = findNavController(R.id.nav_host_activity_main)
    }

    override fun onPause() {
        super.onPause()
        mainController.mainNavController = null
    }

    private fun getRootNavController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_activity_main) as NavHostFragment
        return navHost.navController
    }

    private fun prepareRootNavController(isSignedIn: Boolean) {
        val navController = getRootNavController()
        val graph = navController.navInflater.inflate(R.navigation.main_graph)
        graph.setStartDestination(
            if (isSignedIn) {
                R.id.navigation_tabs
            } else {
                R.id.auth_graph
            }
        )
        navController.graph = graph
    }

}