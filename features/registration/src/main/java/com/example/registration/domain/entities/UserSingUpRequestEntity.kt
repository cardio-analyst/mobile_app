package com.example.registration.domain.entities

data class UserSingUpRequestEntity(
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var birthDate: String,
    var region: String,
    var email: String,
    var login: String,
    var password: String,
)
