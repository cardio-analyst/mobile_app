package com.example.report.domain

import com.example.common.flows.ResultState
import com.example.report.domain.entities.SendReportRequestEntity
import com.example.report.domain.entities.SendReportResponseEntity
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    /**
     * Send report to email
     */
    fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): Flow<ResultState<SendReportResponseEntity>>

    /**
     * Reload sending report
     */
    fun reloadSendReportToEmail(sendReportRequestEntity: SendReportRequestEntity)
}