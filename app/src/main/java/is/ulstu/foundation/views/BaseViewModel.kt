package `is`.ulstu.foundation.views

import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.ui.authorization.AuthorizationFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

typealias LiveEvent<T> = LiveData<Event<T>>
typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

/**
 * Base class for all view-models.
 */
open class BaseViewModel(
    private val navigator: Navigator,
    private val uiAction: UiActions? = null
) : ViewModel() {

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
                uiAction?.toast(e.error + e.description)
            } catch (e: RefreshTokenExpired) {
                Singletons.appSettings.setCurrentRefreshToken(null)
                if (Singletons.appSettings.getUserAccountAccessToken() != null)
                    navigator.launch(AuthorizationFragment.Screen())
                Singletons.appSettings.setUserAccountAccessToken(null)
            } catch (e: AccessTokenExpired) {
                safeLaunch { Singletons.userRepository.refreshUserAccessToken() }
                if (Singletons.appSettings.getUserAccountAccessToken() != null)
                    safeLaunch(block)
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
                uiAction?.toast(e.error + e.description)
                null
            } catch (e: RefreshTokenExpired) {
                Singletons.appSettings.setCurrentRefreshToken(null)
                if (Singletons.appSettings.getUserAccountAccessToken() != null)
                    navigator.launch(AuthorizationFragment.Screen())
                Singletons.appSettings.setUserAccountAccessToken(null)
                null
            } catch (e: AccessTokenExpired) {
                safeLaunchData { Singletons.userRepository.refreshUserAccessToken() }
                if (Singletons.appSettings.getUserAccountAccessToken() != null)
                    safeLaunchData(block)
                else
                    null
            } catch (e: BackendExceptions) {
                uiAction?.toast(e.description)
                null
            } catch (e: InputExceptions) {
                uiAction?.toast(e.description)
                null
            } catch (e: AppLogicExceptions) {
                e.message?.let { uiAction?.toast(it) }
                null
            } catch (_: CancellationException) {
                // ignore cancel exception
                null
            }  catch (e: Exception) {
                e.message?.let { uiAction?.toast(it) }
                null
            }
        }
    }
}