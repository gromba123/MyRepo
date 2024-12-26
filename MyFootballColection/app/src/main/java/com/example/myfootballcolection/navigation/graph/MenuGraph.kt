package com.example.myfootballcolection.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcolection.navigation.AppGraph
import com.example.myfootballcolection.navigation.Screen
import com.example.myfootballcolection.ui.screens.games.BuildGamesScreen
import com.example.myfootballcolection.ui.screens.games.GamesScreenViewModel
import com.example.myfootballcolection.ui.screens.social.BuildSocialScreen
import org.koin.androidx.compose.koinViewModel

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
            val viewModel = koinViewModel<GamesScreenViewModel>()
            BuildGamesScreen(navController, viewModel)
        }
        composable<Screen.Collection> {

        }
        composable<Screen.Settings> {

        }
    }
}