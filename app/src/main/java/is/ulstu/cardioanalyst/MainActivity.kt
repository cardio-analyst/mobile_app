package `is`.ulstu.cardioanalyst

import `is`.ulstu.cardioanalyst.ui.authorization.AuthorizationFragment
import `is`.ulstu.foundation.ActivityScopeViewModel
import `is`.ulstu.foundation.navigator.FragmentNavigator
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authorizationFragment: AuthorizationFragment

    private lateinit var navigator: FragmentNavigator

    private val activityViewModel by viewModels<ActivityScopeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navigator = FragmentNavigator(
            activity = this,
            mainContainerId = R.id.fragmentContainer,
            animations = FragmentNavigator.Animations(
                R.anim.enter,
                R.anim.exit,
                R.anim.pop_enter,
                R.anim.pop_exit
            ),
            initialScreenFragment = authorizationFragment
        )
        navigator.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        navigator.onDestroy()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        // execute navigation actions only when activity is active
        activityViewModel.navigator.setTarget(navigator)
    }

    override fun onPause() {
        super.onPause()
        // postpone navigation actions if activity is not active
        activityViewModel.navigator.setTarget(null)
    }

}