package pt.isel.pdm.chess4android.ui.screens.online.challenges.list

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.domain.online.OnlineGameState
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.navigation.Screen
import pt.isel.pdm.chess4android.utils.BuildArrowBack
import pt.isel.pdm.chess4android.utils.BuildMessage

@Composable
fun BuildChallengesListScreen(
    navController: NavController,
    viewModel: ChallengesListViewModel = hiltViewModel()
) {
    val onSelected: (Result<Pair<ChallengeInfo, OnlineGameState>>) -> Unit = {
        it.onSuccess { createdGameInfo ->
            navController.navigate(
                Screen.Online.buildRoute(createdGameInfo.first.id, Team.BLACK)
            ) {
                popUpTo(Screen.Create.route) {
                    inclusive = true
                }
            }
        }
    }

    val selectedChallengeInfo = viewModel.selectedChallenge.value
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
                BuildCreateChallengeButton {
                    navController.navigate(Screen.Create.route)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BuildChallengesList(
                    viewModel = viewModel
                ) { challengeInfo -> viewModel.onAcceptChallenge(challengeInfo) }
            }
        }
        if (selectedChallengeInfo != null) {
            BuildAcceptChallengeScreen(
                challengeInfo = selectedChallengeInfo,
                onAccept = { viewModel.tryAcceptChallenge(selectedChallengeInfo, onSelected) }
            ) {
                viewModel.cleanSelection()
            }
        }
    } else if (screenState == ScreenState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            BuildMessage(label = stringResource(id = R.string.loading))
        }
    }
}

@Composable
private fun BuildChallengesList(
    viewModel: ChallengesListViewModel,
    onClick: (ChallengeInfo) -> Unit
) {
    val list = viewModel.challenges.value
    LazyColumn (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(list) { _, item ->
            BuildListItem(item = item) {
                onClick(item)
            }
        }
    }
}

@Composable
private fun BuildCreateChallengeButton(
    onClick: () -> Unit
) {
    Icon (
        imageVector = Icons.Filled.Add,
        contentDescription = "Create challenge",
        tint = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.clickable { onClick() }
    )
}