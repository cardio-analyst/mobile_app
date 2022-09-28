package `is`.ulstu.cardioanalyst.ui.profile

import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel

class ProfileViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val userRepository: IUserRepository,
) : BaseViewModel() {
    fun getCurrentUser() = userRepository.getCurrentUserInfo()
}