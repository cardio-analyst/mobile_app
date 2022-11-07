package `is`.ulstu.foundation.views

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Base class for all fragments
 */
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    /**
     * View-model that manages this fragment
     */
    abstract val viewModel: BaseViewModel

    /**
     * Call this method when activity controls (e.g. toolbar) should be re-rendered
     */
    fun notifyScreenUpdates() {
        // if you have more than 1 activity -> you should use a separate interface instead of direct
        // cast to MainActivity
        (requireActivity() as FragmentsHolder).notifyScreenUpdates()
    }
}