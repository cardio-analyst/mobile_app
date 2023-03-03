package `is`.ulstu.cardioanalyst.ui.navigation

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentNavigationBinding
import `is`.ulstu.cardioanalyst.ui.enums.TabItem
import `is`.ulstu.foundation.views.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavigationFragment @Inject constructor() : BaseFragment(R.layout.fragment_navigation) {

    override val viewModel by viewModels<NavigationViewModel>()

}