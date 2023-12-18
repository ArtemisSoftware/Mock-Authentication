package com.artemissoftware.mockauthentication.navigation

import androidx.navigation.NamedNavArgument

sealed class Route(val link: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object AuthLogin : Route(link = "auth_login")
    object UserProfile : Route(link = "user_profile")
}
