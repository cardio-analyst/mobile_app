package `is`.ulstu.foundation.views

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import `is`.ulstu.cardioanalyst.R

/**
 * Base class for all fragments
 */
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    /**
     * View-model that manages this fragment
     */
    abstract val viewModel: BaseViewModel

}

fun Fragment.findMainNavController(): NavController {
    return requireActivity().findNavController(R.id.nav_host_activity_main)
}