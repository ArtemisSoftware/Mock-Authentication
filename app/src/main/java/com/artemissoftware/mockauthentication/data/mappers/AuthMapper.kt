package com.artemissoftware.mockauthentication.data.mappers

import com.artemissoftware.mockauthentication.data.remote.dto.AuthLoginDto
import com.artemissoftware.mockauthentication.data.remote.dto.PostDto
import com.artemissoftware.mockauthentication.data.remote.dto.UserDto
import com.artemissoftware.mockauthentication.domain.models.Login
import com.artemissoftware.mockauthentication.domain.models.Post
import com.artemissoftware.mockauthentication.domain.models.User

fun AuthLoginDto.toLogin(): Login {
    return Login(
        apiKey = "test",
    )
}

fun UserDto.toLogin(): User {
    return User(
        id = id,
        name = name,
        avatarUrl = avatar.thumbnail,
    )
}
fun PostDto.toLogin(): Post {
    return Post(
        id = id,
        title = title,
        body = body,
        userId = userId,
    )
}
