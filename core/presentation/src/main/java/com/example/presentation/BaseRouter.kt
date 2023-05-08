package com.example.presentation

interface BaseRouter {
    val onSessionExpired: () -> Unit
}