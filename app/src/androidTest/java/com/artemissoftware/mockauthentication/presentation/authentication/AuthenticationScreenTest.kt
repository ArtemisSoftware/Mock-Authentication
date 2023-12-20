package com.artemissoftware.mockauthentication.presentation.authentication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artemissoftware.mockauthentication.MainActivity
import com.artemissoftware.mockauthentication.mockserver.MockServerDispatcher
import com.artemissoftware.mockauthentication.mockserver.MockServerDispatcher.Path.serviceMap
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AuthenticationScreenTest {

    @Inject
    lateinit var okHttp: OkHttpClient

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

//    @get:Rule(order = 2)
//    var rule = OkHttpIdlingResourceRule()

    private lateinit var idlingResource: OkHttp3IdlingResource

    private lateinit var mockServer: MockWebServer

    @Before
    fun setup() {
        hiltRule.inject()
        idlingResource = OkHttp3IdlingResource.create("okhttp", okHttp)
        mockServer = MockWebServer()
        mockServer.start(8080)

        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun stopService() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun test_authentication_login_success() {
        val email = "test@mail.com"
        val password = "password"

        mockServer.dispatcher = MockServerDispatcher().successDispatcher(serviceMap)

        composeTestRule.onNodeWithTag("enter email")
            .assertIsDisplayed()
            .performTextInput(email)

        composeTestRule.onNodeWithTag("enter password")
            .assertIsDisplayed()
            .performTextInput(password)

        composeTestRule.onNodeWithTag("login click")
            .assertIsDisplayed()
            .performClick()

        val request: RecordedRequest = mockServer.takeRequest()
        assertEquals(MockServerDispatcher.AUTH_LOGIN, request.path)

        composeTestRule.onNodeWithTag("logged in")
            .assertIsDisplayed()
    }
}
