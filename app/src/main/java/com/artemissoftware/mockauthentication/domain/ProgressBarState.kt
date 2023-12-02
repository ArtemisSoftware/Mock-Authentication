package com.artemissoftware.mockauthentication.domain

sealed class ProgressBarState {

    object Loading : ProgressBarState()

    object Gone : ProgressBarState()
}
