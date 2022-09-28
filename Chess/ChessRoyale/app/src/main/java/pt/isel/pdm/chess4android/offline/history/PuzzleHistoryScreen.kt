package pt.isel.pdm.chess4android.offline.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleDTO
import pt.isel.pdm.chess4android.offline.puzzle.PuzzleHistoryDTO
import pt.isel.pdm.chess4android.utils.BuildArrowBack
import pt.isel.pdm.chess4android.utils.UNSOLVED_PUZZLE

private const val ITEMS_LIST_FRACTION = 0.9F
private const val FETCH_FRACTION = 0.1F
const val PUZZLE_EXTRA = "HistoryActivity.Extra.Puzzle"
const val SOLVED_PUZZLE = 2

@Composable
fun BuildPuzzleListScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<HistoryActivityViewModel>()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        BuildArrowBack(navController = navController)
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            BuildFetchButton(
                viewModel = viewModel
            ) {
                navController.currentBackStackEntry?.arguments?.putParcelable(
                    PUZZLE_EXTRA,
                    it
                )
                navController.navigate(SOLVED_PUZZLE)
            }
            Column (
                modifier = Modifier.fillMaxSize().padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BuildPuzzleList(
                    viewModel = viewModel,
                    screenHeight = screenHeight
                ) { historyDto, dto ->
                    navController.currentBackStackEntry?.arguments?.putParcelable(
                        PUZZLE_EXTRA,
                        dto
                    )
                    if (historyDto.state != SOLVED_PUZZLE) {
                        navController.navigate(UNSOLVED_PUZZLE)
                    } else {
                        navController.navigate(SOLVED_PUZZLE)
                    }
                }
            }
        }
    }
}

@Composable
private fun BuildPuzzleList(
    viewModel: HistoryActivityViewModel,
    screenHeight: Int,
    onClick: (PuzzleHistoryDTO, PuzzleDTO) -> Unit
) {
    val list = viewModel.history.observeAsState().value
    if (list != null) {
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight * ITEMS_LIST_FRACTION).dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            itemsIndexed(list) { _, item ->
                BuildListItem(dto = item) {
                    val dto = viewModel.loadPuzzle(item).value
                    if (dto != null) {
                        onClick(item, dto)
                    }
                }
            }
        }
    }
}

//TODO("Verify the puzzle state before switching activities")
@Composable
private fun BuildFetchButton(
    viewModel: HistoryActivityViewModel,
    onClick: (PuzzleDTO) -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(50),
        onClick = {
            viewModel.fetchDailyPuzzle(
                onClick = onClick
            )
        }
    ) {
        Text(
            text = "Fetch puzzle",
            color = MaterialTheme.colors.secondary,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )
    }
}