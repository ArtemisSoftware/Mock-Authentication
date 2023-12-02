package com.artemissoftware.mockauthentication.domain.repository

import com.artemissoftware.mockauthentication.domain.models.Login
import com.artemissoftware.mockauthentication.domain.models.Post
import com.artemissoftware.mockauthentication.domain.models.Resource
import com.artemissoftware.mockauthentication.domain.models.User

interface AuthRepository {

    suspend fun login(credentials: String?): Resource<Login>

    suspend fun users(): Resource<List<User>>

    suspend fun posts(): Resource<List<Post>>
}