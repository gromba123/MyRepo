package com.example.myfootballcollectionkmp.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myfootballcollection.ui.screens.auth.login.BuildLoginScreen
import com.example.myfootballcollectionkmp.navigation.AppAuth
import com.example.myfootballcollectionkmp.navigation.AppGraph
import com.example.myfootballcollectionkmp.navigation.Screen
import com.example.myfootballcollectionkmp.ui.screens.auth.entryInfo.BuildEntryInfoScreen
import com.example.myfootballcollectionkmp.ui.screens.auth.entryInfo.EntryInfoScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.auth.login.LoginScreenViewModel
import com.example.myfootballcollectionkmp.ui.screens.auth.register.BuildRegisterScreen
import com.example.myfootballcollectionkmp.ui.screens.auth.register.RegisterScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

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
            val viewModel = koinViewModel<EntryInfoScreenViewModel>()
            BuildEntryInfoScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}