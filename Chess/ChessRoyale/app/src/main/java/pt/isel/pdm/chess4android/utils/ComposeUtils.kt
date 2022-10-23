package pt.isel.pdm.chess4android.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.domain.pieces.Team

@Composable
fun BuildArrowBack(
    navController: NavController
) {
    Icon(
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = "Back",
        tint = MaterialTheme.colors.secondary,
        modifier = Modifier.clickable { navController.popBackStack() }
    )
}

@Composable
fun BuildPromotion(
    team: Team,
    onClick: (Char) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface.copy(0.75F)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            promotionOptions.forEach {
                Button(
                    onClick = { onClick(it.piece) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val id = if (team == Team.WHITE) it.idWhite else it.idBlack
                        val name = stringResource(id = it.name)
                        Image(
                            painter = painterResource(id = id),
                            contentDescription = name
                        )
                        Text(
                            text = name,
                            color = MaterialTheme.colors.secondary
                        )
                    }
                }
            }
        }
    }
}