package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.PLAY_ITEM
import pt.isel.pdm.chess4android.navigation.PROFILE_ITEM
import pt.isel.pdm.chess4android.navigation.PUZZLE_ITEM

const val TITTLE_FRACTION = 0.15F
const val NAVIGATION_BUTTONS_FRACTION = 0.075F
const val SCREEN_FRACTION = 1 - (NAVIGATION_BUTTONS_FRACTION + TITTLE_FRACTION)
private val menuItems = listOf(
    NavigationItem(PLAY_ITEM, R.string.play, Icons.Filled.SportsEsports),
    NavigationItem(PUZZLE_ITEM ,R.string.puzzles, Icons.Filled.Extension),
    NavigationItem(PROFILE_ITEM, R.string.profile, Icons.Filled.Person)
)

@Composable
fun BuildMainMenu(
    appController: NavHostController
) {
    val menuController = rememberNavController()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Column(
        Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * TITTLE_FRACTION).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ChessRoyale",
                color = Color.White,
                fontSize = 54.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }
        NavHost(
            navController = menuController,
            startDestination = PLAY_ITEM,
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * SCREEN_FRACTION).dp)
        ) {
            composable(menuItems[0].path) {
                BuildPlayScreen(appController)
            }
            composable(menuItems[1].path) {
                BuildOfflineScreen(appController)
            }
            composable(menuItems[2].path) {
                BuildProfileScreen(appController)
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BuildNavBar(
                screenHeight = screenHeight,
                navController = menuController,
                items = menuItems
            )
        }
    }
}