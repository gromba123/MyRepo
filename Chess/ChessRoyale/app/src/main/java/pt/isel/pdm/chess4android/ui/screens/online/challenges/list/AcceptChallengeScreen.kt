package pt.isel.pdm.chess4android.ui.screens.online.challenges.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.ui.theme.Gray

@Composable
fun BuildAcceptChallengeScreen(
    challengeInfo: ChallengeInfo,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface.copy(0.90F)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp),
            contentAlignment = Alignment.Center,
        ) {
            BuildCard(challengeInfo, onAccept, onCancel)
        }
    }
}

@Composable
private fun BuildCard(
    challengeInfo: ChallengeInfo,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(20.dp),
        horizontalAlignment = Alignment.End
    ) {
        Image(
            imageVector = Icons.Filled.Close,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(20.dp)
                .clickable { onCancel() }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.accept_game_dialog_title, challengeInfo.challengerName),
                fontSize = 20.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onCancel() },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Gray),
                    border = BorderStroke(1.dp, Color.Black),
                    contentPadding = PaddingValues(
                        start = 25.dp,
                        end = 25.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.accept_game_dialog_cancel),
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = { onAccept() },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Gray),
                    contentPadding = PaddingValues(
                        start = 25.dp,
                        end = 25.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.accept_game_dialog_ok),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}