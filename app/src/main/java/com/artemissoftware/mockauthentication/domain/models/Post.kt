package com.artemissoftware.mockauthentication.domain.models

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)
