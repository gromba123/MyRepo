package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.Screen

@Composable
fun BuildOfflineScreen(
    navController: NavController
) {
    val items = listOf(
        IconListItem(
            title = R.string.puzzles,
            description = R.string.puzzles_list_item_description,
            icon = Icons.Filled.Extension
        ) {
            navController.navigate(Screen.PuzzleList.route)
        }
    )
    BuildMenuListItems(navController = navController, items = items)
}