package pt.isel.pdm.chess4android.ui.screens.offline.puzzle

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.GenericBoard
import pt.isel.pdm.chess4android.domain.ScreenState

@Composable
fun BuildPuzzleScreen(
    navController: NavHostController,
    id: String,
    viewModel: PuzzleScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.initPuzzle(id)
    }
    val board = viewModel.board.observeAsState().value
    val paintResults = viewModel.paintResults.observeAsState().value!!
    val screenState = viewModel.screen.observeAsState().value!!
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (screenState == ScreenState.Loaded) {
            if (board != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column (
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                2.dp,
                                Color.Black,
                                RoundedCornerShape(10.dp)
                            )
                    ){
                        GenericBoard(
                            board = board,
                            paintResults = paintResults
                        ) { column, row ->
                            viewModel.movePiece(column, row)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            viewModel.restart()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.secondary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.restart),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        } else if (screenState == ScreenState.Loading) {
            Text(
                text = stringResource(id = R.string.loading),
                fontSize = 32.sp,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}