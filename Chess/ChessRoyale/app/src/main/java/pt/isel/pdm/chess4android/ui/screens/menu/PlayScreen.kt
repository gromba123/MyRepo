package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.Screen

@Composable
fun BuildPlayScreen(
    navController: NavController
) {
    val items = listOf(
        IconListItem(
            title = R.string.online_game_item_title,
            description = R.string.online_game_item_description,
            icon = Icons.Filled.Language
        ) {
            navController.navigate(Screen.Challenges.route)
        },
        IconListItem(
            title = R.string.offline_game_item_title,
            description = R.string.offline_game_item_description,
            icon = Icons.Filled.Person
        ) {
            navController.navigate(Screen.Offline.route)
        }
    )
    BuildMenuListItems(navController = navController, items = items)
}