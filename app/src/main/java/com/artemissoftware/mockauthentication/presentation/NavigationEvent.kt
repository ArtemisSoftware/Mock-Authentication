package com.artemissoftware.mockauthentication.presentation

sealed class NavigationEvent() {
    object NavigateToHome : NavigationEvent()
    object NavigateToProfile : NavigationEvent()
}
