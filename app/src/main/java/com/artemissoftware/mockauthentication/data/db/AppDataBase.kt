package com.artemissoftware.mockauthentication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao
}
