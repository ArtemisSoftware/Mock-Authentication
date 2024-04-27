package com.artemissoftware.mockauthentication.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String,
    val price: Double,
    val title: String,
)
