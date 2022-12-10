package `is`.ulstu.foundation.views

import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.cardioanalyst.ui.authorization.AuthorizationFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.LazyFlowSubject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*


/**
 * Base class for all view-models.
 */
open class BaseViewModel(
    private val navigator: Navigator,
    private val userSettings: UserSettings,
    private val uiAction: UiActions? = null,
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
     * Override this method in child classes if you want to listen for results
     * from other screens
     */
    open fun onResult(result: Any) {

    }

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
            } catch (e: RefreshTokenExpired) {
                userSettings.setCurrentRefreshToken(null)
                if (userSettings.getUserAccountAccessToken() != null)
                    navigator.launch(AuthorizationFragment())
                userSettings.setUserAccountAccessToken(null)
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
    override fun onCleared() {
        super.onCleared()
        firstLoadFlag = true
        job.cancel()
    }

}