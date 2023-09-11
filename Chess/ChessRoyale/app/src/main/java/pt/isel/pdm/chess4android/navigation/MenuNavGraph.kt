package pt.isel.pdm.chess4android.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pt.isel.pdm.chess4android.ui.screens.menu.BuildOfflineScreen
import pt.isel.pdm.chess4android.ui.screens.menu.BuildPlayScreen
import pt.isel.pdm.chess4android.ui.screens.menu.BuildProfileScreen

fun NavGraphBuilder.buildMenuGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = Screen.Play.route,
        route = Screen.Menu.route
    ) {
        composable(Screen.Play.route) {
            BuildPlayScreen(navController = navHostController)
        }
        composable(Screen.Puzzle.route) {
            BuildOfflineScreen(navController = navHostController)
        }
        composable(Screen.Profile.route) {
            BuildProfileScreen(navController = navHostController)
        }
    }
}