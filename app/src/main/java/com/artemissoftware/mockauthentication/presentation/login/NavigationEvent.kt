package com.artemissoftware.mockauthentication.presentation.login

sealed class NavigationEvent() {
    object NavigateToHome : NavigationEvent()
}
