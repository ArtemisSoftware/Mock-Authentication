package com.artemissoftware.mockauthentication.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.usecases.LoginUseCase
import com.artemissoftware.mockauthentication.presentation.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthenticationState())
    val state = _state.asStateFlow()

    private val _navigationEventFlow: MutableSharedFlow<NavigationEvent> = MutableSharedFlow(replay = 0)
    val navigationEventFlow: SharedFlow<NavigationEvent> = _navigationEventFlow

    fun onTriggerEvent(event: AuthenticationEvent) {
        when (event) {
            is AuthenticationEvent.OnLoginClick -> {
                login()
            }
            is AuthenticationEvent.OnUsernameChange -> {
                updateUsername(username = event.username)
            }
            is AuthenticationEvent.OnPasswordChange -> {
                updatePassword(password = event.password)
            }
        }
    }

    private fun login() = with(_state.value) {
        viewModelScope.launch {
            loginUseCase.executeRemote(username = username, password = password)
                .collect { data ->
                    when (data) {
                        is DataState.Data -> {
                            if (data.data!!) {
                                _navigationEventFlow.emit(NavigationEvent.NavigateToProfile)
                            }
                        }
                        is DataState.Error -> {}
                        is DataState.Loading -> {}
                    }
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
}
