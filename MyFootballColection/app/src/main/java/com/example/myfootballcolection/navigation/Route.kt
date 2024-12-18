package com.example.myfootballcolection.navigation

import kotlinx.serialization.Serializable

sealed class AppGraph {
    @Serializable data object Auth : AppGraph()
    @Serializable data object App : AppGraph()
}

@Serializable
sealed class Screen {
    @Serializable data object Social : Screen()
    @Serializable data object Games : Screen()
    @Serializable data object Collection : Screen()
    @Serializable data object Settings : Screen()
}