package com.artemissoftware.mockauthentication.presentation.login

import com.artemissoftware.mockauthentication.presentation.ButtonState

data class LoginState(
    val username: String = "",
    val password: String = "",
    val continueButtonState: ButtonState = ButtonState.Active,
)
