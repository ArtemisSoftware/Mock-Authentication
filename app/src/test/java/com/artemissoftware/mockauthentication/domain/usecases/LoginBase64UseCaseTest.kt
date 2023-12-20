package com.artemissoftware.mockauthentication.domain.usecases

import android.util.Base64
import app.cash.turbine.test
import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.models.Login
import com.artemissoftware.mockauthentication.domain.models.Resource
import com.artemissoftware.mockauthentication.domain.repository.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginBase64UseCaseTest {

    @MockK
    private lateinit var authRepository: AuthRepository

    private lateinit var useCase: LoginBase64UseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = LoginBase64UseCase(authRepository = authRepository)
    }

    @Before
    fun `Bypass android_util_Base64 to java_util_Base64`() {
        mockkStatic(Base64::class)
        val arraySlot = slot<ByteArray>()

        every {
            Base64.encodeToString(capture(arraySlot), Base64.DEFAULT)
        } answers {
            java.util.Base64.getEncoder().encodeToString(arraySlot.captured)
        }

        val stringSlot = slot<String>()
        every {
            Base64.decode(capture(stringSlot), Base64.DEFAULT)
        } answers {
            java.util.Base64.getDecoder().decode(stringSlot.captured)
        }
    }

    @Test
    fun `execute login check if credentials was null`() = runTest {
        turbineScope {
            val email = null
            val password = null
            val loginSuccessResult = Login("apikey")
            coEvery { authRepository.login(null) } returns Resource.Success(loginSuccessResult)

            useCase(email, password).testIn(backgroundScope)

            coVerify { authRepository.login(isNull()) }
        }
    }

    @Test
    fun `execute login check if we have passed credentials`() = runTest {
        turbineScope {
            val email = "email"
            val password = "pass"
            val credentials = "credentials"
            val loginSuccessResult = Login("apikey")
            coEvery { authRepository.login(any()) } returns Resource.Success(loginSuccessResult)

            every { useCase.getCredentials(email, password) } returns credentials

            useCase(email, password).testIn(backgroundScope)

            coVerify { authRepository.login(isNull(inverse = true)) }
        }
    }

    @Test
    fun `execute login without credentials and return data`() = runTest {
        val email = null
        val password = null
        val loginSuccessResult = Login("apikey")
        coEvery { authRepository.login(null) } returns Resource.Success(loginSuccessResult)

        useCase(email, password).test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Data)

            awaitComplete()
        }
    }

    @Test
    fun `execute login without credentials and return error`() = runTest {
        val email = null
        val password = null
        val fakeException = Exception()
        coEvery { authRepository.login(null) } returns Resource.Error(fakeException)

        val flow = useCase(email, password)

        flow.test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Error)
            assertEquals(fakeException, (secondResult as DataState.Error).error)
            awaitComplete()
        }
    }
}
