package com.example.myfootballcollection.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcollection.navigation.AppAuth
import com.example.myfootballcollection.navigation.AppGraph
import com.example.myfootballcollection.navigation.Screen
import com.example.myfootballcollection.ui.screens.auth.create.BuildCreateScreen
import com.example.myfootballcollection.ui.screens.auth.login.BuildLoginScreen
import com.example.myfootballcollection.ui.screens.auth.login.LoginScreenViewModel
import com.example.myfootballcollection.ui.screens.auth.register.BuildRegisterScreen
import com.example.myfootballcollection.ui.screens.auth.register.RegisterScreenViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.buildAuthNavGraph(
    navController: NavController,
    startDestination: Screen
) {
    navigation<AppGraph.Auth>(
        startDestination = startDestination//AppAuth.Login,
    ) {
        composable<AppAuth.Login> {
            val viewModel = koinViewModel<LoginScreenViewModel>()
            BuildLoginScreen(
                navController = navController,
                loginUser = viewModel::registerUser
            )
        }
        composable<AppAuth.Register> {
            val viewModel = koinViewModel<RegisterScreenViewModel>()
            BuildRegisterScreen(
                navController = navController,
                registerUser = viewModel::registerUser
            )
        }
        composable<AppAuth.Create> {
            BuildCreateScreen(
                navController = navController
            )
        }
    }
}