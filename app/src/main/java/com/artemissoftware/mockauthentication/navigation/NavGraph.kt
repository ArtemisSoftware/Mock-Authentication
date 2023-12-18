package com.artemissoftware.mockauthentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.mockauthentication.presentation.authentication.AuthenticationScreen
import com.artemissoftware.mockauthentication.presentation.userprofile.UserProfileScreen

fun NavGraphBuilder.navGraph(
    navController: NavHostController,
) {
    composable(route = Route.AuthLogin.link) {
        AuthenticationScreen(navController)
    }
    composable(route = Route.UserProfile.link) {
        UserProfileScreen()
    }
}


