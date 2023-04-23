package pt.isel.pdm.chess4android.ui.screens.online.challenges.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.FetchState
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.ui.screens.offline.history.HistoryScreenViewModel
import pt.isel.pdm.chess4android.utils.BuildArrowBack

private const val ITEMS_LIST_FRACTION = 0.9F
const val PUZZLE_EXTRA = "HistoryActivity.Extra.Puzzle"
const val SOLVED_PUZZLE_STATE = 2

@Composable
fun BuildChallengesListScreen(
    navController: NavController,
    viewModel: ChallengesListViewModel = hiltViewModel()
) {
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
            //BuildFetchButton(viewModel = viewModel)
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BuildChallengesList(
                    viewModel = viewModel,
                    screenHeight = screenHeight
                ) { historyDto ->

                }
            }
        }
    }
}

@Composable
private fun BuildChallengesList(
    viewModel: ChallengesListViewModel,
    screenHeight: Int,
    onClick: (ChallengeInfo) -> Unit
) {
    val screenState = viewModel.screen.observeAsState().value
    if (screenState == ScreenState.Loaded) {
        val list = viewModel.challenges.observeAsState().value
        if (list != null) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * ITEMS_LIST_FRACTION).dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(list) { _, item ->
                    BuildListItem(item = item) {
                        onClick(item)
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
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun BuildFetchButton(
    viewModel: HistoryScreenViewModel
) {
    val fetchState = viewModel.fetch.observeAsState().value
    if (fetchState != null && fetchState != FetchState.Loaded) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            border = BorderStroke(2.dp, Color.White),
            shape = RoundedCornerShape(50),
            onClick = {
                if (fetchState == FetchState.NotLoaded) {
                    viewModel.fetchDailyPuzzle()
                }
            }
        ) {
            if (fetchState == FetchState.NotLoaded) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon (
                        imageVector = Icons.Filled.Download,
                        contentDescription = "Download",
                        tint = MaterialTheme.colors.secondary
                    )
                    Text(
                        text = stringResource(id = R.string.fetchPuzzle),
                        color = MaterialTheme.colors.secondary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Text(
                    text = "...",
                    color = MaterialTheme.colors.secondary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}