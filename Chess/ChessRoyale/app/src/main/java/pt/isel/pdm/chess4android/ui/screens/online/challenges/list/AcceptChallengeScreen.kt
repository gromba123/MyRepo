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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import pt.isel.pdm.chess4android.ui.theme.White

@Composable
fun BuildAcceptChallengeScreen(
    challengeInfo: ChallengeInfo,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface.copy(0.75F)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
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
            .background(White)
            .padding(20.dp),
        horizontalAlignment = Alignment.End
    ) {
        Image(
            imageVector = Icons.Filled.Close,
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(20.dp)
                .clickable { onCancel() }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.accept_game_dialog_title, challengeInfo.challengerName),
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onCancel() },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Gray),
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
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = { onAccept() },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Gray),
                    contentPadding = PaddingValues(
                        start = 25.dp,
                        end = 25.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.accept_game_dialog_ok),
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}