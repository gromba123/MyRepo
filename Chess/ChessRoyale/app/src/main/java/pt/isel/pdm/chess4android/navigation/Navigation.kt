package pt.isel.pdm.chess4android.navigation

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleDTO
import pt.isel.pdm.chess4android.ui.screens.menu.BuildMainMenu
import pt.isel.pdm.chess4android.ui.screens.offline.game.BuildOfflineBoard
import pt.isel.pdm.chess4android.ui.screens.offline.history.BuildPuzzleListScreen
import pt.isel.pdm.chess4android.ui.screens.offline.history.PUZZLE_EXTRA
import pt.isel.pdm.chess4android.ui.screens.offline.puzzle.BuildSolvedPuzzleScreen
import pt.isel.pdm.chess4android.ui.screens.profile.BuildCreditsScreen

@Composable
fun BuildNavHost(
    navController: NavHostController,
    onClick: (Intent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = MENU_SCREEN,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(MENU_SCREEN) {
            BuildMainMenu(navController)
        }
        composable(CREDITS_SCREEN) {
            BuildCreditsScreen(
                navController = navController,
                onClick = onClick
            )
        }
        composable(PUZZLE_LIST_SCREEN) {
            BuildPuzzleListScreen(navController)
        }
        composable(OFFLINE_GAME) {
            BuildOfflineBoard()
        }
        composable(SOLVED_PUZZLE) {
            val dto = it.arguments?.getParcelable(PUZZLE_EXTRA, PuzzleDTO::class.java) ?: throw IllegalArgumentException()
            BuildSolvedPuzzleScreen(
                navController = navController,
                puzzleDTO = dto
            )
        }
    }
}