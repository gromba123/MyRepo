package com.example.myfootballcollection.ui.composeUtils

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myfootballcollection.navigation.Screen
import com.example.myfootballcollection.R
import kotlinx.serialization.Serializable

@Serializable
data class NavigationItem(
    val screen: Screen,
    @StringRes val labelId: Int,
    @DrawableRes val iconId: Int
)

private val menuItems = listOf(
    NavigationItem(Screen.Social, R.string.menu_social, R.drawable.ic_scarf),
    NavigationItem(Screen.Games, R.string.menu_games, R.drawable.ic_ball),
    NavigationItem(Screen.Collection, R.string.menu_collection, R.drawable.ic_scarf),
    NavigationItem(Screen.Settings, R.string.menu_settings, R.drawable.ic_settings),
)

@SuppressLint("ResourceType")
@Composable
fun BuildNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Social::class.qualifiedName
    val state = getIndex(currentRoute!!)
    TabRow(
        selectedTabIndex = state,
        containerColor = Red,
        indicator = {
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(it[state]),
                height = 2.dp,
                color = MaterialTheme.colorScheme.surface
            )
        }
    ) {
        menuItems.forEach { item ->
            BuildNavigationButton(
                item = item,
                selected = currentRoute == item::class.qualifiedName,
            ) {
                navController.navigate(item.screen)
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
                painter = painterResource(id = item.iconId),
                contentDescription = stringResource(id = item.labelId),
                tint = Color.White,
                modifier = Modifier
                    .height(25.dp)
                    .width(25.dp)
            )
        },
        onClick = onClick
    )
}

private fun getIndex(route: String) =
    when(route) {
        Screen.Social::class.qualifiedName -> 0
        Screen.Games::class.qualifiedName -> 1
        Screen.Collection::class.qualifiedName -> 2
        Screen.Settings::class.qualifiedName -> 3
        else -> 4
    }