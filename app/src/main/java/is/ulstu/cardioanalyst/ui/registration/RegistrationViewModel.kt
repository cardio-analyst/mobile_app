package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel

class RegistrationViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val userRepository: IUserRepository,
) : BaseViewModel() {

    fun getAllAvailableRegions() = userRepository.getAllAvailableRegions()

    fun onRegisterNewUser(
        email: String,
        login: String,
        password: String,
        name: String,
        birthDate: String,
        region: String
    ) {
        val regex: Regex =
            Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
        val fio = name.split(' ').toList()
        try {
            if (!regex.matches(email)) {
                throw Exception("Некорректно введена электронная почта")
            }
            if (fio.size != 3) {
                throw Exception("Некорректное ФИО")
            }
            if (password.length < 7) {
                throw Exception("Пароль должен содержать как минимум 7 символов")
            }
            userRepository.registerNewUser(
                login,
                email,
                password,
                fio[1],
                fio[0],
                fio[2],
                birthDate,
                region
            )
        } catch (e: Exception) {
            uiActions.toast(e.message ?: "Something wrong")
            return
        }
        uiActions.toast("Регистрация прошла успешно!")
        navigator.goBack()
    }
}