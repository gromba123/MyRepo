package com.example.myfootballcolection.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun BuildNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppGraph.Auth,
        modifier = Modifier.fillMaxSize()
    ) {
        buildMenuNavGraph(navController)
        buildAuthNavGraph(navController)
    }
}