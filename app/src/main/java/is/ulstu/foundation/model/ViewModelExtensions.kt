package `is`.ulstu.foundation.model

import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.ResultView
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ScrollView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import `is`.ulstu.cardioanalyst.app.RefreshTokenExpired
import `is`.ulstu.cardioanalyst.ui.TabsFragmentDirections
import `is`.ulstu.foundation.views.findMainNavController

fun <T> LiveData<Result<T>>.observeResults(
    fragment: BaseFragment,
    root: View,
    resultView: ResultView,
    onSuccess: (T) -> Unit,
    ignoreError: Boolean = false,
    uiActions: UiActions? = null,
    ) {
    observe(fragment.viewLifecycleOwner) { result ->
        if (result is Error && result.error is RefreshTokenExpired) {
            // to Auth
            val direction = TabsFragmentDirections.actionNavigationTabsToAuthGraph()
            fragment.findMainNavController().navigate(direction)
        }

        if (ignoreError && uiActions != null)
            resultView.setResult(fragment, result, uiActions)
        else
            resultView.setResult(fragment, result)
        val rootView: View = if (root is ScrollView)
            root.getChildAt(0)
        else
            root

        if (rootView is ViewGroup && rootView !is RecyclerView && root !is AbsListView) {
            rootView.children
                .filter { it != resultView }
                .forEach {
                    it.isVisible = result is Success<*> || (ignoreError && result is Error<*>)
                }
        }

        if (result is Success) {
            onSuccess.invoke(result.value)
        }
    }
}