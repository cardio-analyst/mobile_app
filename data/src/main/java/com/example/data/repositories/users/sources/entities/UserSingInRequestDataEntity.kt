package com.example.data.repositories.users.sources.entities

data class UserSingInRequestDataEntity(
    val loginOrEmail: String,
    val password: String
)
