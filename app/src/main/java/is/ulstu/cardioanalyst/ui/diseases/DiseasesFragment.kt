package `is`.ulstu.cardioanalyst.ui.diseases

import `is`.ulstu.cardioanalyst.databinding.FragmentDiseasesBinding
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DiseasesFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<DiseasesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDiseasesBinding.inflate(inflater, container, false)
        return binding.root
    }
}