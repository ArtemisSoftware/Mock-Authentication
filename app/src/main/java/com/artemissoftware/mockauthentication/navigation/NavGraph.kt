package com.artemissoftware.mockauthentication.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.artemissoftware.mockauthentication.presentation.authentication.AuthenticationScreen

fun NavGraphBuilder.navGraph(
    route: String,
    navController: NavController,
) {
    navigation(
        startDestination = Route.AuthLogin.link,
        route = route,
    ) {
        login(navController)
        profile(navController)
    }
}

fun NavGraphBuilder.login(navController: NavController) {
    composable(route = Route.AuthLogin.link) {
        AuthenticationScreen(navController)
    }
}

fun NavGraphBuilder.profile(navController: NavController) {
    composable(
        route = Route.AuthProfile.link,
    ) {
        AuthProfileScreen(navController)
    }
}

@Composable
fun AuthProfileScreen(navController: NavController) {
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.semantics {
                    contentDescription = "logged in"
                },
                text = "Logged Success",
            )
        }
    }
}
