package com.artemissoftware.mockauthentication.domain.usecases

import android.util.Base64
import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.ProgressBarState
import com.artemissoftware.mockauthentication.domain.models.Resource
import com.artemissoftware.mockauthentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginBase64UseCase @Inject constructor(private val authRepository: AuthRepository) {

    operator fun invoke(
        username: String?,
        password: String?,
    ): Flow<DataState<out Any>> = flow {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

        val credentials = getCredentials(username, password)

        val result = authRepository.login(credentials)

        when (result) {
            is Resource.Success -> {
                emit(DataState.Data(data = result.data))
            }
            is Resource.Error -> {
                emit(DataState.Error(error = result.exception))
            }
        }
    }

    fun getCredentials(
        username: String?,
        password: String?,
    ): String? {
        val credentials = if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            "Basic " + Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
        } else {
            null
        }

        return credentials
    }
}
