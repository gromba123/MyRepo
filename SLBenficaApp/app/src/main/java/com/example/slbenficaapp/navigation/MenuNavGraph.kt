package com.example.slbenficaapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.slbenficaapp.ui.menu.BuildAssociateScreen
import com.example.slbenficaapp.ui.menu.home.BuildHomeScreen

fun NavGraphBuilder.buildMenuGraph(
    navController: NavController
) {
    navigation(
        startDestination = Screen.Home.route,
        route = Screen.Menu.route
    ) {
        composable(Screen.Home.route) {
            BuildHomeScreen(navController = navController)
        }
        composable(Screen.Associate.route) {
            BuildAssociateScreen(navController = navController)
        }
    }
}