package com.example.presentation

import androidx.fragment.app.Fragment

/**
 * Base class for all fragments
 */
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    /**
     * View-model that manages this fragment
     */
    abstract val viewModel: BaseViewModel

}
