package com.artemissoftware.mockauthentication.domain.usecases

import com.artemissoftware.mockauthentication.data.remote.AuthApi
import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.ProgressBarState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authApi: AuthApi,
) {
    suspend fun execute(username: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

        if (username.isNotEmpty() && password.isNotEmpty()) {
            emit(DataState.Data(true))
        } else {
            emit(DataState.Data(false))
        }
    }

    suspend fun executeRemote(username: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

        if (username.isNotEmpty() && password.isNotEmpty()) {
            try {
                val network = authApi.login()

                val networkResponse = network.body()!!

                emit(DataState.Data(networkResponse.success))
            } catch (e: Exception) {
                emit(DataState.Error(Exception("Something went wrong!")))
            }
        } else {
            emit(DataState.Data(false))
        }
    }
}
