package `is`.ulstu.foundation.views

import `is`.ulstu.cardioanalyst.app.AlreadyRegisteredWithLogin
import `is`.ulstu.cardioanalyst.app.BackendException
import `is`.ulstu.cardioanalyst.app.ConnectionException
import `is`.ulstu.cardioanalyst.app.InputExceptions
import `is`.ulstu.foundation.uiactions.UiActions
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import `is`.ulstu.foundation.utils.Event
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

/**
 * Base class for all view-models.
 */
open class BaseViewModel(private val uiAction: UiActions? = null) : ViewModel() {

    /**
     * Override this method in child classes if you want to listen for results
     * from other screens
     */
    open fun onResult(result: Any) {

    }

    /**
     * Safe launch database sources
     */
    fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                block.invoke(this)
            } catch (e: ConnectionException) {
                e.message?.let { uiAction?.toast(it) }
            } catch (e: BackendException) {
                uiAction?.toast(e.description)
            } catch (e: AlreadyRegisteredWithLogin) {
                uiAction?.toast(e.description)
            } catch (e: InputExceptions) {
                uiAction?.toast(e.description)
            }
            catch (e: Exception) {
                e.message?.let { uiAction?.toast(it) }
            }
        }
    }

    /**
     * Safe launch database sources methods returns data
     */
    fun <T> CoroutineScope.safeLaunchData(block: suspend CoroutineScope.() -> T): T? {
        return runBlocking {
            try {
                block()
            } catch (e: ConnectionException) {
                e.message?.let { uiAction?.toast(it) }
                null
            } catch (e: BackendException) {
                uiAction?.toast(e.description)
                null
            } catch (e: AlreadyRegisteredWithLogin) {
                uiAction?.toast(e.description)
                null
            } catch (e: InputExceptions) {
                uiAction?.toast(e.description)
                null
            }
            catch (e: Exception) {
                e.message?.let { uiAction?.toast(it) }
                null
            }
        }
    }
}