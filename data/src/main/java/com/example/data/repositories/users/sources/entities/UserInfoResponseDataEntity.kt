package com.example.data.repositories.users.sources.entities

data class UserInfoResponseDataEntity(
    val email: String,
    val login: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val birthDate: String,
    val region: String,
)