package com.example.report.domain.entities

data class SendReportRequestEntity(
    val receiver: String,
    val sendMyself: Boolean,
)
