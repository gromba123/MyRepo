package com.example.myfootballcollection.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcollection.navigation.AppGraph
import com.example.myfootballcollection.navigation.AppScreen
import com.example.myfootballcollection.ui.screens.games.BuildGamesScreen
import com.example.myfootballcollection.ui.screens.games.GamesScreenViewModel
import com.example.myfootballcollection.ui.screens.social.BuildSocialScreen
import org.koin.androidx.compose.koinViewModel

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