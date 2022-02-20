package pt.isel.pdm.chess4android.activities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import pt.isel.pdm.chess4android.R

data class NavigationItem(@StringRes val labelId: Int, @DrawableRes val iconId: Int)

@Composable
fun BuildNavigationSystem(screenWidth: Int, screenHeight: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight * NAVIGATION_BUTTONS_FRACTION).dp)
    ) {
        BuildNavigationButton(R.string.online, R.drawable.ic_black_king, screenWidth) {}
        BuildNavigationButton(R.string.offline, R.drawable.ic_white_king, screenWidth) {}
        BuildNavigationButton(R.string.profile, R.drawable.ic_white_pawn, screenWidth) {}
    }
}

@Composable
fun BuildNavigationButton(
    @StringRes labelId: Int,
    @DrawableRes iconId: Int,
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
            painter = painterResource(id = iconId),
            contentDescription = stringResource(id = labelId),
            tint = Color.White
        )
        Text(
            text = stringResource(id = labelId),
            color = Color.White,
            fontSize = (screenWidth * 0.05).sp
        )
    }
}