package pt.isel.pdm.chess4android.ui.screens.online.challenges.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.navigation.Screen
import pt.isel.pdm.chess4android.ui.theme.White
import pt.isel.pdm.chess4android.utils.BuildMessage

@Composable
fun BuildCreateChallengeScreen(
    navController: NavController,
    viewModel: CreateChallengeViewModel = hiltViewModel()
) {
    /*
    viewModel.created.observe(this) {
            if (it == null) displayCreateChallenge()
            else
                it
                    .onSuccess { displayWaitingForChallenger() }
                    .onFailure { displayError() }
        }
     */
    /*
    viewModel.accepted.observe(this) {
            if (it == true) {
                Log.v(APP_TAG, "Someone accepted our challenge")
                viewModel.created.value?.onSuccess { challenge ->
                    val intent = OnlineActivity.buildIntent(
                        origin = this,
                        local = Team.firstToMove,
                        challengeInfo = challenge
                    )
                    startActivity(intent)
                }
            }
        }
     */
    val screen = viewModel.screen.value
    if (screen == ScreenState.Loaded) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            var name by remember { mutableStateOf("") }
            var message by remember { mutableStateOf("") }
            TextField(
                label = {
                    Text(text = stringResource(id = R.string.create_game_prompt_challenger_name))
                },
                value = name,
                onValueChange = { name = it}
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                label = {
                    Text(text = stringResource(id = R.string.create_game_prompt_message))
                },
                value = message,
                onValueChange = { message = it}
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                viewModel.createChallenge(name, message) {
                    navController.navigate(
                        Screen.Online.buildRoute(it.id, Team.WHITE)
                    ) {
                        popUpTo(Screen.Create.route) {
                            inclusive = true
                        }
                    }
                }
            }) {
                Text(
                    text = stringResource(id = R.string.create_game_button_label),
                    color = White
                )
            }
        }
    } else {
        BuildWaitingScreen {
            viewModel.removeChallenge()
        }
    }
}

@Composable
private fun BuildWaitingScreen(
    onCancel: () -> Unit
) {
    Box(
       modifier = Modifier.fillMaxSize(),
       contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BuildMessage(label = stringResource(id = R.string.loading))
            CircularProgressIndicator()
            Text(
                text = stringResource(id = R.string.accept_game_dialog_cancel),
                fontSize = 24.sp,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.clickable { onCancel() }
            )
        }
    }
}