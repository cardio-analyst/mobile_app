package com.example.data.repositories.recommendations.sources.entities

data class SendReportRequestDataEntity(
    val receiver: String,
    val sendMyself: Boolean,
)
