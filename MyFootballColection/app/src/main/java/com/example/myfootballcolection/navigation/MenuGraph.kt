package com.example.myfootballcolection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.buildMenuNavGraph(
    navController: NavController
) {
    navigation<AppGraph.App>(
        startDestination = Screen.Social,
    ) {
        composable<Screen.Social> {

        }
        composable<Screen.Games> {

        }
        composable<Screen.Collection> {

        }
        composable<Screen.Settings> {

        }
    }
}