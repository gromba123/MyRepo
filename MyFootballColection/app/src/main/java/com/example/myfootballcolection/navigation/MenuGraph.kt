package com.example.myfootballcolection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcolection.ui.screens.games.BuildGamesScreen
import com.example.myfootballcolection.ui.screens.social.BuildSocialScreen

fun NavGraphBuilder.buildMenuNavGraph(
    navController: NavController
) {
    navigation<AppGraph.App>(
        startDestination = Screen.Social,
    ) {
        composable<Screen.Social> {
            BuildSocialScreen(navController)
        }
        composable<Screen.Games> {
            BuildGamesScreen(navController)
        }
        composable<Screen.Collection> {

        }
        composable<Screen.Settings> {

        }
    }
}