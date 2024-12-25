package com.example.myfootballcolection.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcolection.ui.screens.auth.login.BuildLoginScreen

fun NavGraphBuilder.buildAuthNavGraph(
    navController: NavController
) {
    navigation<AppGraph.Auth>(
        startDestination = AppAuth.Login,
    ) {
        composable<AppAuth.Login> {
            BuildLoginScreen(navController)
        }
        composable<AppAuth.Register> {
            //BuildRegisterScreen(navController)
        }
    }
}