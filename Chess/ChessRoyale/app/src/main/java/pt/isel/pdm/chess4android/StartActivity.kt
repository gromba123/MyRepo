package pt.isel.pdm.chess4android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pt.isel.pdm.chess4android.menu.BuildMainMenu
import pt.isel.pdm.chess4android.offline.game.BuildOfflineBoard
import pt.isel.pdm.chess4android.offline.history.BuildPuzzleListScreen
import pt.isel.pdm.chess4android.profile.BuildCreditsScreen
import pt.isel.pdm.chess4android.theme.Chess4AndroidTheme
import pt.isel.pdm.chess4android.utils.CREDITS_SCREEN
import pt.isel.pdm.chess4android.utils.MENU_SCREEN
import pt.isel.pdm.chess4android.utils.OFFLINE_PUZZLE
import pt.isel.pdm.chess4android.utils.PUZZLE_LIST_SCREEN

@AndroidEntryPoint
class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chess4AndroidTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = MENU_SCREEN,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable(MENU_SCREEN) {
                            BuildMainMenu(navController)
                        }
                        composable(CREDITS_SCREEN) {
                            BuildCreditsScreen(navController = navController) {
                                startActivity(it)
                            }
                        }
                        composable(PUZZLE_LIST_SCREEN) {
                            BuildPuzzleListScreen(navController)
                        }
                        composable(OFFLINE_PUZZLE) {
                            BuildOfflineBoard()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Chess4AndroidTheme {
        //BuildOfflineScreen()
    }
}