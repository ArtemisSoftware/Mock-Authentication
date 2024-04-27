package com.artemissoftware.mockauthentication.di

import android.content.Context
import androidx.room.Room
import com.artemissoftware.mockauthentication.data.db.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
object TestDatabaseModule {

    @Singleton
    @Provides
    fun provideTestDB(@ApplicationContext context: Context): AppDataBase {
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
    }
}