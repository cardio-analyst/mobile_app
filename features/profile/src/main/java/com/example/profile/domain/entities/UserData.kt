package com.example.profile.domain.entities

data class UserData(
    val email: String,
    val login: String,
    val password: String,
    val name: String,
    val birthDate: String,
    val region: String
)
