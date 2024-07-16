package pt.isel.pdm.chess4android.ui.screens.online.games

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.utils.BuildPromotion

@Composable
fun BuildOnlineGameScreen(
    gameId: String,
    team: Team,
    navController: NavController,
    viewModel: OnlineScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = gameId) {
        viewModel.initGame(gameId, team)
    }
    val boardResult = viewModel.board.value
    val paintResults = viewModel.paintResults.value
    val promotion = viewModel.promotion.value
    boardResult.onSuccess { board ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BuildOnlineGameBoard(
                board = board,
                paintResults = paintResults,
                viewModel = viewModel
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { viewModel.forfeit() }
                    ) {
                        Icon (
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Give up",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(id = R.string.forfeit),
                        fontSize = 18.sp
                    )
                }

            }
            if (promotion != null) {
                BuildPromotion(team = promotion) {
                    viewModel.promote(it)
                }
            }
        }
    }
}

/*
@SuppressLint("StringFormatMatches")
    private fun drawPurpose() {
        val text =
            if (localPlayer.other == Team.BLACK) resources.getString(R.string.draw_purpose_black)
            else resources.getString(R.string.draw_purpose_white)
        AlertDialog.Builder(this)
            .setTitle(text)
            .setPositiveButton(R.string.accept_game_dialog_ok) { _, _ ->
                viewModel.acceptDraw(true)
            }
            .setNegativeButton(R.string.accept_game_dialog_cancel) { _, _ ->
                viewModel.acceptDraw(false)
            }
            .create()
            .show()
    }
 */