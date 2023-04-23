package pt.isel.pdm.chess4android.ui.screens.menu

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.navigation.Screen

data class NavigationItem(
    val path: String,
    @StringRes val labelId: Int,
    val iconId: ImageVector
)

private val menuItems = listOf(
    NavigationItem(Screen.Play.route, R.string.play, Icons.Filled.SportsEsports),
    NavigationItem(Screen.Puzzle.route , R.string.puzzles, Icons.Filled.Extension),
    NavigationItem(Screen.Profile.route, R.string.profile, Icons.Filled.Person)
)

@SuppressLint("ResourceType")
@Composable
fun BuildNavBar(
    navController: NavController
) {
    val actualRoute = navController.currentDestination?.route!!
    val state = getIndex(actualRoute)
    TabRow(
        selectedTabIndex = state,
        backgroundColor = MaterialTheme.colors.primary,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(it[state]),
                color = MaterialTheme.colors.surface,
                height = 2.dp
            )
        }
    ) {
        menuItems.forEachIndexed { index, item ->
            BuildNavigationButton(
                item = item,
                selected = index == state
            ) {
                navController.navigate(item.path)
            }
        }
    }
}

@Composable
private fun BuildNavigationButton(
    item: NavigationItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    Tab(
        selected = selected,
        text = {
            Text(
                text = stringResource(id = item.labelId),
                color = Color.White,
                fontSize = 12.sp
            )
        },
        icon = {
            Icon (
                imageVector = item.iconId,
                contentDescription = stringResource(id = item.labelId),
                tint = Color.White,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
        },
        onClick = onClick
    )
}

private fun getIndex(route: String) =
    when(route) {
        Screen.Play.route -> 0
        Screen.Puzzle.route -> 1
        else -> 2
    }