package com.artemissoftware.mockauthentication.domain.usecases

import app.cash.turbine.test
import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.models.Post
import com.artemissoftware.mockauthentication.domain.models.Resource
import com.artemissoftware.mockauthentication.domain.models.User
import com.artemissoftware.mockauthentication.domain.repository.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserPostsUseCaseTest {

    @MockK
    private lateinit var authRepository: AuthRepository

    private lateinit var useCase: GetUserPostsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetUserPostsUseCase(authRepository = authRepository)
    }

    @Test
    fun `execute users and return error`() = runTest {
        val fakeException = Exception()
        coEvery { authRepository.users() } returns Resource.Error(fakeException)

        useCase().test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Error)
            assertEquals(fakeException, (secondResult as DataState.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `execute users success and posts error`() = runTest {
        val fakeException = Exception()
        val fakeUsersData: List<User> = listOf()

        coEvery { authRepository.users() } returns Resource.Success(fakeUsersData)
        coEvery { authRepository.posts() } returns Resource.Error(fakeException)

        useCase().test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Error)
            assertEquals(fakeException, (secondResult as DataState.Error).error)
            awaitComplete()
        }
    }

    @Test
    fun `execute users success and posts success`() = runTest {
        val fakeUsersData: List<User> = listOf()
        val fakePostsData: List<Post> = listOf()

        coEvery { authRepository.users() } returns Resource.Success(data = fakeUsersData)
        coEvery { authRepository.posts() } returns Resource.Success(data = fakePostsData)

        useCase().test {
            val firstResult = awaitItem()
            assertTrue(firstResult is DataState.Loading)

            val secondResult = awaitItem()
            assertTrue(secondResult is DataState.Data)
            awaitComplete()
        }
    }
}
