package `is`.ulstu.cardioanalyst

import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.foundation.ActivityScopeViewModel
import `is`.ulstu.foundation.navigator.FragmentNavigator
import `is`.ulstu.foundation.navigator.IntermediateNavigator
import `is`.ulstu.foundation.uiactions.AndroidUiActions
import `is`.ulstu.foundation.uiactions.AndroidUiHelper
import `is`.ulstu.foundation.utils.viewModelCreator
import `is`.ulstu.foundation.views.FragmentsHolder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), FragmentsHolder {

    private lateinit var navigator: FragmentNavigator

    private val activityViewModel by viewModelCreator<ActivityScopeViewModel> {
        ActivityScopeViewModel(
            uiActions = AndroidUiActions(applicationContext),
            navigator = IntermediateNavigator(),
            uiHelper = AndroidUiHelper(applicationContext, windowManager)
        )
    }

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
            initialScreenCreator = { NavigationFragment.Screen() }
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

    override fun notifyScreenUpdates() {
        navigator.notifyScreenUpdates()
    }

    override fun getActivityScopeViewModel(): ActivityScopeViewModel {
        return activityViewModel
    }
}