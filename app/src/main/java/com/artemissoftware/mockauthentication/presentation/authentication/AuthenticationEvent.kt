package com.artemissoftware.mockauthentication.presentation.authentication

sealed class AuthenticationEvent {
    object OnLoginClick : AuthenticationEvent()
    data class OnUsernameChange(val username: String) : AuthenticationEvent()
    data class OnPasswordChange(val password: String) : AuthenticationEvent()
}
