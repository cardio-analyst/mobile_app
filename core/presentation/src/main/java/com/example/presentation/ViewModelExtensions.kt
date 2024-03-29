package com.example.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ScrollView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.common.RefreshTokenExpired
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.common.flows.Success
import com.example.presentation.uiactions.UiAction


data class ResultViewTools(
    val fragment: BaseFragment,
    val root: View,
    val resultView: ResultView,
    val onSessionExpired: (() -> Unit)? = null,
    val ignoreError: Boolean = false,
    val uiActions: UiAction? = null,
)

fun <T> LiveData<ResultState<T>>.observeResultsComponent(
    resultViewTools: ResultViewTools,
    onSuccess: (T) -> Unit,
) {
    with(resultViewTools) {
        observe(fragment.viewLifecycleOwner) { result ->
            if (result is Error && result.error is RefreshTokenExpired) {
                // to Auth
                onSessionExpired?.invoke()
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
}

/**
 * Convert mutable live-data into non-mutable live-data.
 */
fun <T> MutableLiveData<T>.share(): LiveData<T> = this