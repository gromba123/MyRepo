package pt.isel.pdm.chess4android.utils

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BuildArrowBack(
    navController: NavController
) {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = "Back",
        tint = MaterialTheme.colors.secondary,
        modifier = Modifier.clickable { navController.navigate(MENU_SCREEN) }
    )
}