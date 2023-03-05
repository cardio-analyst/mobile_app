package `is`.ulstu.cardioanalyst

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import `is`.ulstu.foundation.ActivityScopeViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<ActivityScopeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (activityViewModel.isUserAuthorized()) {
            prepareRootNavController(isSignedIn = true)
        } else {
            prepareRootNavController(isSignedIn = false)
        }

    }

    private fun prepareRootNavController(isSignedIn: Boolean) {
        val navController = findNavController(R.id.nav_host_activity_main)
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