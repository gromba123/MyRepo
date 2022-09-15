package pt.isel.pdm.chess4android.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.utils.CREDITS_ITEM
import pt.isel.pdm.chess4android.utils.OFFLINE_ITEM
import pt.isel.pdm.chess4android.utils.ONLINE_ITEM

const val TITTLE_FRACTION = 0.2F
const val SCREEN_FRACTION = 0.65F
const val NAVIGATION_BUTTONS_FRACTION = 0.15F
private val menuItems = listOf(
    NavigationItem(ONLINE_ITEM, R.string.online, R.drawable.ic_black_king),
    NavigationItem(OFFLINE_ITEM ,R.string.offline, R.drawable.ic_white_king),
    NavigationItem(CREDITS_ITEM, R.string.profile, R.drawable.ic_white_pawn)
)

@Composable
fun BuildMainMenu(
    appController: NavHostController
) {
    val menuController = rememberNavController()
    val configuration = LocalConfiguration.current
    //val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    Column(
        Modifier
            .background(Color.Black)
            .fillMaxSize()
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
                fontSize = (screenWidth * 0.1).sp,
                fontStyle = FontStyle.Italic
            )
        }
        NavHost(
            navController = menuController,
            startDestination = OFFLINE_ITEM,
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * SCREEN_FRACTION).dp)
        ) {
            composable(menuItems[0].path) {
                BuildOnlineScreen(appController)
            }
            composable(menuItems[1].path) {
                BuildOfflineScreen(appController)
            }
            composable(menuItems[2].path) {
                BuildProfileScreen(appController)
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        BuildNavigationSystem(
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            menuController,
            menuItems
        )
    }
}