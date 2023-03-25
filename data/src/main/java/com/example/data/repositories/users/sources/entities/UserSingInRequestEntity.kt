package com.example.data.repositories.users.sources.entities

data class UserSingInRequestEntity(
    val loginOrEmail: String,
    val password: String
)
