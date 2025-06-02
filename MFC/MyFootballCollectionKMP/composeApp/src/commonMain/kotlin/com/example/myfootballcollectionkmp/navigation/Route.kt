package com.example.myfootballcollectionkmp.navigation

import kotlinx.serialization.Serializable

interface Screen

sealed class AppGraph {
    @Serializable data object Splash : AppGraph()
    @Serializable data object Auth : AppGraph()
    @Serializable data object App : AppGraph()
}

sealed class AppAuth {
    @Serializable data object Login : AppAuth(), Screen
    @Serializable data object Register : AppAuth(), Screen
    @Serializable data object Create : AppAuth(), Screen
}

@Serializable
sealed class AppScreen {
    @Serializable data object Social : AppScreen(), Screen
    @Serializable data object Games : AppScreen(), Screen
    @Serializable data object Collection : AppScreen(), Screen
    @Serializable data object Settings : AppScreen(), Screen
}