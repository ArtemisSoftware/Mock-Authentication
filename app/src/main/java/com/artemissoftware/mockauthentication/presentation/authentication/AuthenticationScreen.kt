package com.artemissoftware.mockauthentication.presentation.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artemissoftware.mockauthentication.navigation.Route
import com.artemissoftware.mockauthentication.presentation.NavigationEvent

@Composable
fun AuthenticationScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEventFlow.collect {
            when (it) {
                is NavigationEvent.NavigateToProfile -> {
                    navController.navigate(Route.UserProfile.link)
                }
                else -> Unit
            }
        }
    }

    AuthenticationContent(
        state = viewModel.state.collectAsState().value,
        events = viewModel::onTriggerEvent,
    )
}

@Composable
private fun AuthenticationContent(
    state: AuthenticationState,
    events: (AuthenticationEvent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .testTag(tag = "enter email"),
                value = state.username,
                placeholder = {
                    Text("Username")
                },
                onValueChange = {
                    events(AuthenticationEvent.OnUsernameChange(it))
                },
            )
            OutlinedTextField(
                modifier = Modifier
                    .testTag(tag = "enter password"),
                value = state.password,
                placeholder = {
                    Text("Password")
                },
                onValueChange = {
                    events(AuthenticationEvent.OnPasswordChange(it))
                },
            )
            Button(
                modifier = Modifier
                    .testTag(tag = "login click"),
                onClick = {
                    events(AuthenticationEvent.OnLoginClick)
                },
                content = {
                    Text("Login")
                },
            )
        }
    }
}
