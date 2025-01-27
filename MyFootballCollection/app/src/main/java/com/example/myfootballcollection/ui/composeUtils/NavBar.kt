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
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myfootballcollection.R
import com.example.myfootballcollection.navigation.AppScreen
import kotlinx.serialization.Serializable

@Serializable
data class NavigationItem(
    val screen: AppScreen,
    @StringRes val labelId: Int,
    @DrawableRes val iconId: Int
)

private val menuItems = listOf(
    NavigationItem(AppScreen.Social, R.string.menu_social, R.drawable.ic_scarf),
    NavigationItem(AppScreen.Games, R.string.menu_games, R.drawable.ic_ball),
    NavigationItem(AppScreen.Collection, R.string.menu_collection, R.drawable.ic_scarf),
    NavigationItem(AppScreen.Settings, R.string.menu_settings, R.drawable.ic_settings),
)

@SuppressLint("ResourceType")
@Composable
fun BuildNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    val index = doWeShowBottomBar(destination)
    if (index != -1) {
        val currentRoute = destination?.route ?: AppScreen.Social::class.qualifiedName
        TabRow(
            selectedTabIndex = index,
            containerColor = Red,
            indicator = {
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(it[index]),
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

private fun doWeShowBottomBar(navDestination: NavDestination?) =
    if (navDestination == null) -1
    else {
        val currentRoute = navDestination.route
        if (currentRoute == null) {
            -1
        } else {
            if(currentRoute.startsWith(AppScreen.Social::class.qualifiedName!!)) {
                1
            } else if(currentRoute.startsWith(AppScreen.Games::class.qualifiedName!!)) {
                2
            } else if(currentRoute.startsWith(AppScreen.Collection::class.qualifiedName!!)) {
                3
            } else if(currentRoute.startsWith(AppScreen.Settings::class.qualifiedName!!)) {
                4
            }
            else {
                -1
            }
        }
    }