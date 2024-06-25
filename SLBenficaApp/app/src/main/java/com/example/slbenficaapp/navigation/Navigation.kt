package com.example.slbenficaapp.navigation

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
        startDestination = MENU_ROUTE,
        modifier = Modifier.fillMaxSize()
    ) {
        buildMenuGraph(navController)
    }
}