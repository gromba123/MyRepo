package pt.isel.pdm.chess4android.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import pt.isel.pdm.chess4android.ui.screens.auth.BuildLoginScreen
import pt.isel.pdm.chess4android.ui.screens.auth.LoginScreenViewModel

fun NavGraphBuilder.buildAuthNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = Screen.Login.route,
        route = AUTHENTICATION_ROUTE
    ) {
        composable(Screen.Login.route) {
            val viewModel = hiltViewModel<LoginScreenViewModel>()
            val state = viewModel.screenState.value

            BuildLoginScreen(
                navController = navController,
                screenState = state,
                onLogin = viewModel::login,
                onError = {}
            )
        }
        /*
        composable(Screen.SignUp.route) {
            val viewModel = hiltViewModel<SignUpScreenViewModel>()
            val state = viewModel.screenState.value
            val error = viewModel.error.value
            BuildSignUpScreen(
                navController = navController,
                state = state,
                error = error,
                verifyImageSize = viewModel::verifyImageSize,
                onErrorDismiss = viewModel::cleanErrorState,
                onSignUp = viewModel::signUp
            )
        }

        composable(Screen.Recover.route) {
            val viewModel = hiltViewModel<RecoverScreenViewModel>()
            val state = viewModel.screenState.value
            val error = viewModel.error.value
            BuildPasswordRecoverScreen(
                navController = navController,
                state = state,
                error = error,
                onErrorDismiss = viewModel::cleanErrorState,
                onRecover = viewModel::recover
            )
        }
        composable(Screen.MailSent.route) {
            BuildMailSentScreen(navController = navController)
        }*/
    }
}