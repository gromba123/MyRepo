package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.Screen

@Composable
fun BuildProfileScreen(
    navController: NavController
) {
    val items = listOf(
        MenuItem(
            title = R.string.info_item_title,
            description = R.string.info_item_description,
            icon = Icons.Filled.Info
        ) {
            navController.navigate(Screen.Credits.route)
        }
    )
    BuildMenuListItems(navController = navController, items = items)
}