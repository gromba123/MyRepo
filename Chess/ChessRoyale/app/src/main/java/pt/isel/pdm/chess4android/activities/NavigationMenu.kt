package pt.isel.pdm.chess4android.activities

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class NavigationItem(@StringRes val labelId: Int, @DrawableRes val iconId: Int)

@SuppressLint("ResourceType")
@Composable
fun BuildNavigationSystem(
    screenWidth: Int,
    screenHeight: Int,
    navController: NavHostController,
    items: List<NavigationItem>
) {
    LazyRow (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight * NAVIGATION_BUTTONS_FRACTION).dp)
    ) {
        itemsIndexed(items) { _, item ->
            BuildNavigationButton(item = item, screenWidth = screenWidth) {
                navController.navigate(item.labelId.toString())
            }
        }
    }
}

@Composable
fun BuildNavigationButton(
    item: NavigationItem,
    screenWidth: Int,
    onClick: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .clickable { onClick() }
    ) {
        Icon (
            painter = painterResource(id = item.iconId),
            contentDescription = stringResource(id = item.labelId),
            tint = Color.White
        )
        Text(
            text = stringResource(id = item.labelId),
            color = Color.White,
            fontSize = (screenWidth * 0.05).sp
        )
    }
}