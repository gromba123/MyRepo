package com.example.myfootballcollectionkmp.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcollectionkmp.navigation.AppGraph
import com.example.myfootballcollectionkmp.navigation.AppScreen
import com.example.myfootballcollectionkmp.ui.screens.games.BuildGamesScreen
import com.example.myfootballcollectionkmp.ui.screens.games.GamesScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.social.BuildSocialScreen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.buildMenuNavGraph(
    navController: NavController
) {
    navigation<AppGraph.App>(
        startDestination = AppScreen.Social,
    ) {
        composable<AppScreen.Social> {
            BuildSocialScreen(navController)
        }
        composable<AppScreen.Games> {
            val viewModel = koinViewModel<GamesScreenViewModel>()
            BuildGamesScreen(navController, viewModel)
        }
        composable<AppScreen.Collection> {

        }
        composable<AppScreen.Settings> {

        }
    }
}