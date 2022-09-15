package pt.isel.pdm.chess4android.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.utils.OFFLINE_PUZZLE
import pt.isel.pdm.chess4android.utils.PUZZLE_LIST_SCREEN

@Composable
fun BuildOfflineScreen(
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Row(modifier = Modifier.fillMaxSize()) {
        BuildButtons(screenWidth, navController)
    }
}

@Composable
private fun BuildButtons(
    screenWidth: Int,
    navController: NavController
) {
    BuildButton(label = stringResource(id = R.string.offline), screenWidth) {
        navController.navigate(OFFLINE_PUZZLE)
    }
    Spacer(modifier = Modifier.width(2.dp))
    BuildButton(label = stringResource(id = R.string.fetch_button), screenWidth) {
        navController.navigate(PUZZLE_LIST_SCREEN)
    }
}

@Composable
private fun BuildButton(
    label: String,
    screenWidth: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxHeight()
            .width((screenWidth * 0.5).dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(10),
        onClick = onClick
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}