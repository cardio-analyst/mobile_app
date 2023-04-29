package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.*
import com.example.presentation.uiactions.UiAction
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * Base class for all view-models.
 */
open class BaseViewModel(
    private val uiAction: UiAction? = null,
) : ViewModel() {

    /**
     * Flag of first loading main get request of viewModel
     */
    protected var firstLoadFlag: Boolean = true
        get() {
            if (field) {
                field = false
                return true
            }
            return field
        }

    /**
     * Main Job() of viewModel for correct [safeLaunch] method work
     */
    private var job = Job()

    /**
     * Safe launch database sources
     */
    fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        if (job.isCancelled) job = Job()
        viewModelScope.launch(job) {
            try {
                block.invoke(this)
            } catch (e: ConnectionException) {
                e.message?.let { uiAction?.toast(it) }
            } catch (e: BackendException) {
                uiAction?.toast(e.error + e.description)
            } catch (e: BackendExceptions) {
                uiAction?.toast(e.description)
            } catch (e: InputExceptions) {
                uiAction?.toast(e.description)
            } catch (e: AppLogicExceptions) {
                e.message?.let { uiAction?.toast(it) }
            } catch (_: CancellationException) {
                // ignore cancel exception
            } catch (e: Exception) {
                e.message?.let { uiAction?.toast(it) }
            }
        }
    }

    /**
     * Cancel [Job] of viewModel for removing observers
     * @see LazyFlowSubject.listen job.cancel() will trigger awaitCLose()
     */
    public override fun onCleared() {
        super.onCleared()
        firstLoadFlag = true
        job.cancel()
    }

}