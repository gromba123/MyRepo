package com.example.myfootballcollection.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcollection.navigation.AppAuth
import com.example.myfootballcollection.navigation.AppGraph
import com.example.myfootballcollection.ui.screens.auth.login.BuildLoginScreen
import com.example.myfootballcollection.ui.screens.auth.register.BuildRegisterScreen

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
            BuildRegisterScreen(navController)
        }
        composable<AppAuth.Create> {
            
        }
    }
}