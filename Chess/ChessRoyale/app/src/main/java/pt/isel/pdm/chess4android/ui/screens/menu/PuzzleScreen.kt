package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.Screen

@Composable
fun BuildOfflineScreen(
    navController: NavController
) {
    val items = listOf(
        MenuItem(
            title = R.string.puzzles,
            description = R.string.puzzlesDescription,
            icon = Icons.Filled.Extension
        ) {
            navController.navigate(Screen.PuzzleList.route)
        }
    )
    BuildMenuLayout(navController = navController) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(items = items) { item ->
                BuildMenuButton(item)
            }
        }
    }
}