package com.artemissoftware.mockauthentication.data.remote.dto

import com.squareup.moshi.Json

data class AvatarDto(
    @field:Json(name = "thumbnail")
    val thumbnail: String,
)
