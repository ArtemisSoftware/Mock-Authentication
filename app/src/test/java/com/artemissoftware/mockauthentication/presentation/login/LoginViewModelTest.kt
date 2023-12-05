package com.artemissoftware.mockauthentication.presentation.login

import app.cash.turbine.test
import com.artemissoftware.mockauthentication.MainCoroutineRule
import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.usecases.LoginBase64UseCase
import com.artemissoftware.mockauthentication.presentation.ButtonState
import com.artemissoftware.mockauthentication.presentation.NavigationEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var loginBase64UseCase: LoginBase64UseCase

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(loginBase64UseCase = loginBase64UseCase)
    }

    @Test
    fun `when OnNameChange is triggered with non-empty value check if username has new value`() {
        val name = "Bruce Wayne"

        viewModel.onTriggerEvent(LoginEvent.OnNameChange(name = name))

        assertEquals(name, viewModel.state.value.username)
    }

    @Test
    fun `when OnPasswordChange is triggered with non-empty value check if password has new value`() {
        val password = "batman"

        viewModel.onTriggerEvent(LoginEvent.OnPasswordChange(password = password))

        assertEquals(password, viewModel.state.value.password)
    }

    @Test
    fun `when OnLoginClick is triggered and loginInteractor emits Loading`() = runTest {
        coEvery { loginBase64UseCase(any(), any()) } returns flowOf(DataState.Loading())

        viewModel.onTriggerEvent(LoginEvent.OnLoginClick)

        assertEquals(ButtonState.Loading, viewModel.state.value.continueButtonState)
    }

    @Test
    fun `when OnLoginClick is triggered with user and pass check if loginBase64UseCase is invoked with user and pass`() = runTest {
        val username = "Bruce Wayne"
        val password = "Batman"

        val emailSlot = slot<String>()
        val passwordSlot = slot<String>()

        coEvery { loginBase64UseCase(capture(emailSlot), capture(passwordSlot)) } returns flowOf()

        viewModel.onTriggerEvent(LoginEvent.OnNameChange(name = username))
        viewModel.onTriggerEvent(LoginEvent.OnPasswordChange(password = password))
        viewModel.onTriggerEvent(LoginEvent.OnLoginClick)

        coVerify { loginBase64UseCase(any(), any()) }

        assertEquals(username, emailSlot.captured)
        assertEquals(password, passwordSlot.captured)
    }

    @Test
    fun `when OnLoginClick is triggered and emits Data to navigate to home`() = runTest {
        coEvery { loginBase64UseCase(any(), any()) } returns flowOf(DataState.Data())

        backgroundScope.launch {
            viewModel.onTriggerEvent(LoginEvent.OnLoginClick)
        }

        viewModel.uiEvent.test {
            val navigationEvent = awaitItem()
            assertTrue(navigationEvent is NavigationEvent.NavigateToHome)
            cancelAndConsumeRemainingEvents()
        }
    }
}
