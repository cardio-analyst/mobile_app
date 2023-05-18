package com.example.data.repositories.users.sources.entities

data class UserSingUpRequestDataEntity(
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var birthDate: String,
    var region: String,
    var email: String,
    var login: String,
    var password: String,
)
