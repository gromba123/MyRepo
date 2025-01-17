package com.example.myfootballcollection.navigation

import kotlinx.serialization.Serializable

sealed class AppGraph {
    @Serializable data object Auth : AppGraph()
    @Serializable data object App : AppGraph()
}

sealed class AppAuth {
    @Serializable data object Login : AppGraph()
    @Serializable data object Register : AppGraph()
    @Serializable data object Create : AppGraph()
}

@Serializable
sealed class Screen {
    @Serializable data object Social : Screen()
    @Serializable data object Games : Screen()
    @Serializable data object Collection : Screen()
    @Serializable data object Settings : Screen()
}