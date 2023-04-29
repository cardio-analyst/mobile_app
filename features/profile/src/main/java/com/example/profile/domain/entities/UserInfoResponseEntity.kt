package com.example.profile.domain.entities

data class UserInfoResponseEntity(
    val email: String,
    val login: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val birthDate: String,
    val region: String,
)