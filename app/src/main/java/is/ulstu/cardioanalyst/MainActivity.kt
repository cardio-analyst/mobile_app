package `is`.ulstu.cardioanalyst

import `is`.ulstu.cardioanalyst.ui.authorization.AuthorizationFragment
import `is`.ulstu.foundation.ActivityScopeViewModel
import `is`.ulstu.foundation.navigator.FragmentNavigator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.model.Error
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityViewModel by viewModels<ActivityScopeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeAuthStatus()
    }

    private fun observeAuthStatus() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityViewModel.authorizationStatus.collect { authStatus ->
                    when (authStatus) {
                        is Success -> {
                            prepareRootNavController(isSignedIn = true)
                        }
                        is Error -> {
                            prepareRootNavController(isSignedIn = false)
                        }
                        else -> {
                            // nothing to do
                        }
                    }
                }
            }
        }

    }

    private fun prepareRootNavController(isSignedIn: Boolean) {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val graph = navController.navInflater.inflate(R.navigation.main_graph)
        graph.setStartDestination(
            if (isSignedIn) {
                R.id.navigation_tabs
            } else {
                R.id.navigation_authorization
            }
        )
        navController.graph = graph
    }

}