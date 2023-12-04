package com.artemissoftware.mockauthentication.presentation.login

sealed class LoginEvent {
    data class OnNameChange(val name: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    object OnLoginClick : LoginEvent()
}
