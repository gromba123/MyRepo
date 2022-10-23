package pt.isel.pdm.chess4android.ui.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.CREDITS_SCREEN
import pt.isel.pdm.chess4android.navigation.OFFLINE_GAME
import pt.isel.pdm.chess4android.navigation.ONLINE_GAME

@Composable
fun BuildPlayScreen(
    navController: NavController
) {
    val items = listOf(
        MenuItem(
            title = R.string.onlineGame,
            description = R.string.onlineGameDescription,
            icon = Icons.Filled.Language
        ) {
            navController.navigate(ONLINE_GAME)
        },
        MenuItem(
            title = R.string.offlineGame,
            description = R.string.offlineGameDescription,
            icon = Icons.Filled.Person
        ) {
            navController.navigate(OFFLINE_GAME)
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