package pt.isel.pdm.chess4android.navigation

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pt.isel.pdm.chess4android.ui.screens.offline.game.BuildOfflineScreen
import pt.isel.pdm.chess4android.ui.screens.offline.history.BuildPuzzleListScreen
import pt.isel.pdm.chess4android.ui.screens.offline.puzzle.BuildPuzzleScreen
import pt.isel.pdm.chess4android.ui.screens.offline.puzzle.BuildSolvedPuzzleScreen
import pt.isel.pdm.chess4android.ui.screens.online.challenges.create.BuildCreateChallengeScreen
import pt.isel.pdm.chess4android.ui.screens.online.challenges.list.BuildChallengesListScreen
import pt.isel.pdm.chess4android.ui.screens.profile.BuildCreditsScreen

@Composable
fun BuildNavHost(
    navController: NavHostController,
    onClick: (Intent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route,
        modifier = Modifier.fillMaxSize()
    ) {
        buildMenuGraph(navController)
        composable(Screen.Credits.route) {
            BuildCreditsScreen(
                navController = navController,
                onClick = onClick
            )
        }
        composable(Screen.PuzzleList.route) {
            BuildPuzzleListScreen(navController)
        }
        composable(Screen.Offline.route) {
            BuildOfflineScreen()
        }
        composable(
            Screen.SolvedPuzzle.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString("id")
            id?.let { puzzleId -> BuildSolvedPuzzleScreen(
                navController = navController,
                puzzleId
            ) }
        }
        composable(
            Screen.UnsolvedPuzzle.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString("id")
            id?.let { puzzleId -> BuildPuzzleScreen(
                navController = navController,
                puzzleId
            ) }
        }

        composable(
            Screen.Challenges.route
        ) {
            BuildChallengesListScreen(navController = navController)
        }

        composable(
            Screen.Create.route
        ) {
            BuildCreateChallengeScreen(navController = navController)
        }
    }
}