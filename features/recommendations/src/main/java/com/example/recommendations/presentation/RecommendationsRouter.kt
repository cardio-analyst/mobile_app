package com.example.recommendations.presentation

import com.example.presentation.BaseRouter

interface RecommendationsRouter : BaseRouter {
    fun navigateToQuestionnaires()

    fun launchFeedbackScreen()
}