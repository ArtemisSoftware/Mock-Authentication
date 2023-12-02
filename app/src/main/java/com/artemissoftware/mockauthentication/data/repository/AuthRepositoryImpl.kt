package com.artemissoftware.mockauthentication.data.repository

import com.artemissoftware.mockauthentication.data.mappers.toPost
import com.artemissoftware.mockauthentication.data.mappers.toUser
import com.artemissoftware.mockauthentication.data.remote.AuthApi
import com.artemissoftware.mockauthentication.domain.models.Login
import com.artemissoftware.mockauthentication.domain.models.Post
import com.artemissoftware.mockauthentication.domain.models.Resource
import com.artemissoftware.mockauthentication.domain.models.User
import com.artemissoftware.mockauthentication.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
) : AuthRepository {
    override suspend fun login(credentials: String?): Resource<Login> = try {
        val result = authApi.login()
        Resource.Success(Login(apiKey = "test"))
    } catch (e: Exception) {
        Resource.Error(exception = e)
    }

    override suspend fun users(): Resource<List<User>> = try {
        val request = authApi.users()
        Resource.Success(
            data = request.map { it.toUser() },
        )
    } catch (e: Exception) {
        Resource.Error(exception = e)
    }

    override suspend fun posts(): Resource<List<Post>> = try {
        val request = authApi.posts()
        Resource.Success(
            data = request.map { it.toPost() },
        )
    } catch (e: Exception) {
        Resource.Error(exception = e)
    }
}
