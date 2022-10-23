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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.CREDITS_SCREEN

@Composable
fun BuildProfileScreen(
    navController: NavController
) {
    BuildButton(stringResource(id = R.string.credits)) {
        navController.navigate(CREDITS_SCREEN)
    }
}

@Composable
private fun BuildButton(
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxSize(),
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