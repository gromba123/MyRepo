package pt.isel.pdm.chess4android.ui.screens.offline.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.FetchState
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleHistoryDTO
import pt.isel.pdm.chess4android.navigation.Screen
import pt.isel.pdm.chess4android.utils.BuildArrowBack

const val SOLVED_PUZZLE_STATE = 2

@Composable
fun BuildPuzzleListScreen(
    navController: NavController,
    viewModel: HistoryScreenViewModel = hiltViewModel()
) {
    val screenState = viewModel.screen.value
    if (screenState == ScreenState.Loaded) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BuildArrowBack(navController = navController)
                BuildFetchButton(viewModel = viewModel)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BuildPuzzleList(
                    viewModel = viewModel
                ) { historyDto ->
                    if (historyDto.state != SOLVED_PUZZLE_STATE) {
                        navController.navigate(Screen.UnsolvedPuzzle.buildRoute(historyDto.id))
                    } else {
                        navController.navigate(Screen.SolvedPuzzle.buildRoute(historyDto.id))
                    }
                }
            }
        }
    } else if (screenState == ScreenState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = stringResource(id = R.string.loading),
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun BuildPuzzleList(
    viewModel: HistoryScreenViewModel,
    onClick: (PuzzleHistoryDTO) -> Unit
) {
    val list = viewModel.history.value
    LazyColumn (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(list) { _, item ->
            BuildListItem(dto = item) {
                onClick(item)
            }
        }
    }
}

@Composable
private fun BuildFetchButton(
    viewModel: HistoryScreenViewModel
) {
    if (viewModel.fetch != FetchState.Loaded) {
        Icon (
            imageVector = Icons.Filled.Download,
            contentDescription = "Download",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.clickable { viewModel.fetchDailyPuzzle() }
        )
    }
}