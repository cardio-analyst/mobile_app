package com.example.data.repositories.recommendations.sources.entities

data class SendReportRequestEntity(
    val receiver: String,
    val sendMyself: Boolean,
)
