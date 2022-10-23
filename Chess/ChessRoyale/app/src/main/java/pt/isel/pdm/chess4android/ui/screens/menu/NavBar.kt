package pt.isel.pdm.chess4android.ui.screens.menu

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class NavigationItem(
    val path: String,
    @StringRes val labelId: Int,
    val iconId: ImageVector
)

@SuppressLint("ResourceType")
@Composable
fun BuildNavBar(
    screenHeight: Int,
    navController: NavHostController,
    items: List<NavigationItem>
) {
    Icons.Filled.Extension
    LazyRow (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .height((screenHeight * NAVIGATION_BUTTONS_FRACTION).dp)
    ) {
        itemsIndexed(items) { _, item ->
            BuildNavigationButton(item = item) {
                navController.navigate(item.path)
            }
        }
    }
}

@Composable
private fun BuildNavigationButton(
    item: NavigationItem,
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
            imageVector = item.iconId,
            contentDescription = stringResource(id = item.labelId),
            tint = Color.White,
            modifier = Modifier.height(30.dp).width(30.dp)
        )
        Text(
            text = stringResource(id = item.labelId),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}