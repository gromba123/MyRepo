package pt.isel.pdm.chess4android.ui.screens.offline.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.utils.BuildPromotion

val CELL_HEIGHT = 45.dp
const val SIDE = 8

@Composable
fun BuildOfflineScreen(
    viewModel: OfflineScreenViewModel = hiltViewModel()
) {
    val board = viewModel.offlineBoardData.observeAsState().value!!
    val paintResults = viewModel.paintResults.observeAsState().value!!
    val promotion = viewModel.promotion.observeAsState(initial = null).value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OfflinePuzzleBoard(
                board = board,
                paintResults = paintResults,
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { viewModel.forfeit() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.forfeit),
                    color = MaterialTheme.colors.primary
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