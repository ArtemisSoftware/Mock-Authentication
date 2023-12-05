package com.artemissoftware.mockauthentication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.usecases.LoginBase64UseCase
import com.artemissoftware.mockauthentication.presentation.ButtonState
import com.artemissoftware.mockauthentication.presentation.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginBase64UseCase: LoginBase64UseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<NavigationEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLoginClick -> {
                login()
            }
            is LoginEvent.OnNameChange -> {
                updateUsername(username = event.name)
            }
            is LoginEvent.OnPasswordChange -> {
                updatePassword(password = event.password)
            }
        }
    }

    private fun updateUsername(username: String) = with(_state) {
        update {
            it.copy(username = username)
        }
    }

    private fun updatePassword(password: String) = with(_state) {
        update {
            it.copy(password = password)
        }
    }

    private fun updateLoading(buttonState: ButtonState) = with(_state) {
        update {
            it.copy(continueButtonState = buttonState)
        }
    }

    private fun login() = with(_state.value) {
        viewModelScope.launch {
            loginBase64UseCase(username = username, password = password).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        updateLoading(ButtonState.Loading)
                    }
                    is DataState.Data -> {
                        _uiEvent.send(NavigationEvent.NavigateToHome)
                    }
                    else -> {}
                }
            }
        }
    }
}
