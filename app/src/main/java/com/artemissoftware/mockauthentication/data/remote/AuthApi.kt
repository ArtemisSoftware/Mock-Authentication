package com.artemissoftware.mockauthentication.data.remote

import com.artemissoftware.mockauthentication.data.remote.dto.AuthLoginDto
import com.artemissoftware.mockauthentication.data.remote.dto.PostDto
import com.artemissoftware.mockauthentication.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface AuthApi {

    @GET("/auth/login")
    suspend fun login(): Response<AuthLoginDto>

    @GET("posts")
    suspend fun posts(): List<PostDto>

    @GET("users")
    suspend fun users(): List<UserDto>
}
