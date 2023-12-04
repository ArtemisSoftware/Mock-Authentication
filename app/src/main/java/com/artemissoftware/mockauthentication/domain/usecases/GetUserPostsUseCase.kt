package com.artemissoftware.mockauthentication.domain.usecases

import com.artemissoftware.mockauthentication.domain.DataState
import com.artemissoftware.mockauthentication.domain.ProgressBarState
import com.artemissoftware.mockauthentication.domain.models.Resource
import com.artemissoftware.mockauthentication.domain.models.UserPost
import com.artemissoftware.mockauthentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserPostsUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<DataState<out List<UserPost>>> = flow {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        val userPostList: MutableList<UserPost> = mutableListOf()

        val users = authRepository.users()

        when (users) {
            is Resource.Success -> {
                when (val posts = authRepository.posts()) {
                    is Resource.Success -> {
                        emit(DataState.Data(data = userPostList))
                    }
                    is Resource.Error -> {
                        emit(DataState.Error(error = posts.exception))
                    }
                }
            }
            is Resource.Error -> {
                emit(DataState.Error(error = users.exception))
            }
        }
    }
}
