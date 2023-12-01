package com.artemissoftware.mockauthentication.data.remote.dto

import com.squareup.moshi.Json

data class UserDto(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "avatar")
    val avatar: AvatarDto,
)
