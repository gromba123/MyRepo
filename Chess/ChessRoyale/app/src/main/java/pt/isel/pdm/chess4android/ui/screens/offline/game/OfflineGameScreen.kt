package pt.isel.pdm.chess4android.ui.screens.offline.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.utils.BuildPromotion

val CELL_HEIGHT = 45.dp
const val SIDE = 8

@Composable
fun BuildOfflineScreen(
    viewModel: OfflineScreenViewModel = hiltViewModel()
) {
    val board = viewModel.offlineBoardData.value
    val paintResults = viewModel.paintResults.value
    val promotion = viewModel.promotion.value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BuildOfflineGameBoard(
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
                        imageVector = Icons.Default.Logout,
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