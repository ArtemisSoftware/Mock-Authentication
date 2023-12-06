package com.artemissoftware.mockauthentication.di

import com.artemissoftware.mockauthentication.data.remote.AuthApi
import com.artemissoftware.mockauthentication.data.repository.AuthRepositoryImpl
import com.artemissoftware.mockauthentication.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi = authApi)
    }
}
