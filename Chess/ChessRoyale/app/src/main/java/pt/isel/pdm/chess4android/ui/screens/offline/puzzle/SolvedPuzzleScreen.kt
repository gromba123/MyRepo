package pt.isel.pdm.chess4android.ui.screens.offline.puzzle

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val b = viewModel.board.value
    val screenState = viewModel.screen.value
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
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.solution),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Button(
                            onClick = {
                                navController.navigate(Screen.UnsolvedPuzzle.buildRoute(id))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.retry),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            } else if (screenState == ScreenState.Loading) {
                Text(
                    text = stringResource(id = R.string.loading),
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}