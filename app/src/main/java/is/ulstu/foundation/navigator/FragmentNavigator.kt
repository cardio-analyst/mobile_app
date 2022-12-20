package `is`.ulstu.foundation.navigator

import `is`.ulstu.foundation.utils.Event
import `is`.ulstu.foundation.views.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentNavigator(
    private val activity: AppCompatActivity,
    @IdRes private val mainContainerId: Int,
    private val animations: Animations,
    private val initialScreenFragment: Fragment
) : Navigator {

    private var result: Event<Any>? = null

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            // define the initial screen that should be launched when app starts.
            launchFragment(
                fragment = initialScreenFragment,
                addToBackStack = false
            )
        }
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    fun onDestroy() {
        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    override fun launch(fragment: Fragment) {
        launchFragment(fragment)
    }

    override fun addFragmentToScreen(containerId: Int, fragment: Fragment) {
        addFragment(containerId, fragment)
    }

    override fun goBack(result: Any?) {
        if (result != null) {
            this.result = Event(result)
        }
        activity.onBackPressed()
    }

    override fun getBackstackFragmentCount(): Int =
        activity.supportFragmentManager.backStackEntryCount

    fun launchFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        //if (fragment is ChangeModeParamsListener) changeModeParamsListener = fragment

        val transaction = activity.supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .setCustomAnimations(
                animations.enterAnim,
                animations.exitAnim,
                animations.popEnterAnim,
                animations.popExitAnim
            )
            .replace(mainContainerId, fragment)
            .commit()
    }

    private fun addFragment(containerId: Int, fragment: Fragment) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction
            .setCustomAnimations(
                animations.enterAnim,
                animations.exitAnim,
                animations.popEnterAnim,
                animations.popExitAnim
            )
            .replace(containerId, fragment)
            .commit()
    }

    override fun removeFragment(fragment: Fragment) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction
            .remove(fragment)
            .commit()
    }

    fun notifyScreenUpdates() {
        //val f = activity.supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (activity.supportFragmentManager.backStackEntryCount > 0) {
            // more than 1 screen -> show back button in the toolbar
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun publishResults(fragment: Fragment) {
        val result = result?.getValue() ?: return
        if (fragment is BaseFragment) {
            // has result that can be delivered to the screen's view-model
            fragment.viewModel.onResult(result)
        }
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
            publishResults(f)
        }
    }

    class Animations(
        @AnimRes val enterAnim: Int,
        @AnimRes val exitAnim: Int,
        @AnimRes val popEnterAnim: Int,
        @AnimRes val popExitAnim: Int,
    )
}