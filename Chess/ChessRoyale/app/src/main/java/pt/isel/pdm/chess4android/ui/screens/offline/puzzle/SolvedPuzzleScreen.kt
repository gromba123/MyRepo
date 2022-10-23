package pt.isel.pdm.chess4android.ui.screens.offline.puzzle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleDTO
import pt.isel.pdm.chess4android.navigation.UNSOLVED_PUZZLE
import pt.isel.pdm.chess4android.ui.screens.offline.game.CELL_HEIGHT
import pt.isel.pdm.chess4android.ui.screens.offline.game.SIDE
import pt.isel.pdm.chess4android.ui.screens.offline.history.PUZZLE_EXTRA
import pt.isel.pdm.chess4android.ui.theme.Board
import pt.isel.pdm.chess4android.utils.getPiece

@Composable
fun BuildSolvedPuzzleScreen(
    navController: NavController,
    puzzleDTO: PuzzleDTO,
    viewModel: SolvedScreenViewModel = hiltViewModel()
) {
    val board = viewModel.board.observeAsState().value!!
    LaunchedEffect(key1 = puzzleDTO) {
        viewModel.buildPuzzle(puzzleDTO)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
                repeat(SIDE) { row ->
                    Row {
                        repeat(SIDE) { column ->
                            Box(
                                modifier = Modifier
                                    .height(CELL_HEIGHT)
                                    .width(CELL_HEIGHT)
                                    .background(if ((row + column) % 2 == 0) Board else Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                val id = getPiece(board.board[row][column])
                                if (id != null) {
                                    Image(painter = painterResource(id = id), contentDescription = "")
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround
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
                        text = stringResource(id = R.string.forfeit),
                        color = MaterialTheme.colors.primary
                    )
                }
                Button(
                    onClick = {
                        navController.currentBackStackEntry?.arguments?.putParcelable(
                            PUZZLE_EXTRA,
                            puzzleDTO
                        )
                        navController.navigate(UNSOLVED_PUZZLE)
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
    }
}