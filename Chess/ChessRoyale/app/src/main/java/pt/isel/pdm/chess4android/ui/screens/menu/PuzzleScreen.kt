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
import pt.isel.pdm.chess4android.navigation.PUZZLE_LIST_SCREEN

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
            navController.navigate(PUZZLE_LIST_SCREEN)
        }
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(items = items) { item ->
            BuildMenuButton(item)
        }
    }
}