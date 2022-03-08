package pt.isel.pdm.chess4android.activities

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
import pt.isel.pdm.chess4android.R

@Composable
fun BuildScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    //val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    val items by remember {
        mutableStateOf(listOf(
            NavigationItem(R.string.online, R.drawable.ic_black_king),
            NavigationItem(R.string.offline, R.drawable.ic_white_king),
            NavigationItem(R.string.profile, R.drawable.ic_white_pawn)
        ))
    }
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
            navController = navController,
            startDestination = items[1].labelId.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * SCREEN_FRACTION).dp)
        ) {
            composable(items[0].labelId.toString()) {
                BuildOnlineScreen()
            }
            composable(items[1].labelId.toString()) {
                BuildOfflineScreen()
            }
            composable(items[2].labelId.toString()) {
                BuildProfileScreen()
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        BuildNavigationSystem(
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            navController,
            items
        )
    }
}