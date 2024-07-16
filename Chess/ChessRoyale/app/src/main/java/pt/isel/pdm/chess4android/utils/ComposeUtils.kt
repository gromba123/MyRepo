package pt.isel.pdm.chess4android.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.domain.pieces.Team

@Composable
fun BuildArrowBack(
    navController: NavController
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Back",
        tint = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.clickable { navController.popBackStack() }
    )
}

@Composable
fun BuildMessage(
    label: String
) {
    Text(
        text = label,
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun BuildPromotion(
    team: Team,
    onClick: (Char) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface.copy(0.75F)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                promotionOptions.forEach {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onClick(it.piece) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            val id = if (team == Team.WHITE) it.idWhite else it.idBlack
                            val name = stringResource(id = it.name)
                            Image(
                                painter = painterResource(id = id),
                                modifier = Modifier.size(30.dp),
                                contentDescription = name
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = name,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}