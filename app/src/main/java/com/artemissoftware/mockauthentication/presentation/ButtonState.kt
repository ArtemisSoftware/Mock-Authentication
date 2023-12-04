package com.artemissoftware.mockauthentication.presentation

sealed class ButtonState {

    object Active : ButtonState()

    object Disabled : ButtonState()

    object Loading : ButtonState()
}
