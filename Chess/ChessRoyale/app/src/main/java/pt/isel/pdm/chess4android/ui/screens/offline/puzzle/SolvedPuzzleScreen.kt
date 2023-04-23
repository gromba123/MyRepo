package pt.isel.pdm.chess4android.ui.screens.offline.puzzle

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.GenericBoard
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.navigation.Screen

@Composable
fun BuildSolvedPuzzleScreen(
    navController: NavController,
    id: String,
    viewModel: SolvedScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.buildPuzzle(id)
    }
    val b = viewModel.board.observeAsState().value
    val screenState = viewModel.screen.observeAsState().value!!
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        b?.let { board ->
            if (screenState == ScreenState.Loaded) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column (
                        modifier = Modifier
                            .border(
                                2.dp,
                                if (isSystemInDarkTheme()) Color.White else Color.Black
                            )
                    ){
                        GenericBoard(board = board.board, null) {_,_ -> }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        var text by remember {
                            mutableStateOf(R.string.solution)
                        }
                        Button(
                            onClick = {
                                text = if (text == R.string.puzzle) {
                                    viewModel.loadPuzzle()
                                    R.string.solution
                                } else {
                                    viewModel.loadSolution()
                                    R.string.puzzle
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.solution),
                                color = MaterialTheme.colors.primary
                            )
                        }
                        Button(
                            onClick = {
                                navController.navigate(Screen.UnsolvedPuzzle.setRoute(id))
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.retry),
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
}