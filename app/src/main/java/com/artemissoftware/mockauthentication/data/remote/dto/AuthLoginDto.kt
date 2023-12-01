package com.artemissoftware.mockauthentication.data.remote.dto

import com.squareup.moshi.Json

data class AuthLoginDto(
    @field:Json(name = "success")
    val success: Boolean,
)
