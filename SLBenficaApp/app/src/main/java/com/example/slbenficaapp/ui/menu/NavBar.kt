package com.example.slbenficaapp.ui.menu

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.slbenficaapp.R
import com.example.slbenficaapp.navigation.Screen
import com.example.slbenficaapp.ui.theme.Red

data class NavigationItem(
    val path: String,
    @StringRes val labelId: Int,
    @DrawableRes val iconId: Int
)

private val menuItems = listOf(
    NavigationItem(Screen.Home.route, R.string.home, R.drawable.ic_benfica_foreground),
    NavigationItem(Screen.Associate.route, R.string.associates, R.drawable.ic_benfica_foreground)
)

@SuppressLint("ResourceType")
@Composable
fun BuildNavBar(
    navController: NavController
) {
    val actualRoute = Screen.Home.route//navController.currentDestination?.route!!
    val state = getIndex(actualRoute)
    TabRow(
        selectedTabIndex = state,
        containerColor = Red,
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(it[state]),
                color = MaterialTheme.colorScheme.surface,
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
                painter = painterResource(id = item.iconId),
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
        Screen.Home.route -> 0
        Screen.Associate.route -> 1
        else -> 2
    }