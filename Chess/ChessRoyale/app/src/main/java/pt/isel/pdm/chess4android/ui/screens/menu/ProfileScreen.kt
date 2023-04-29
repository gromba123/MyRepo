package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.navigation.Screen

@Composable
fun BuildProfileScreen(
    navController: NavController
) {
    BuildMenuLayout(navController = navController) {
        BuildScreen {
            navController.navigate(it.route)
        }
    }
}

@Composable
private fun BuildScreen(
    onClick: (Screen) -> Unit
) {
    Button(
        modifier = Modifier.fillMaxSize(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(10),
        onClick = {}
    ) {
        Text(
            text = "",
            color = Color.White,
            fontSize = 20.sp
        )
    }
}