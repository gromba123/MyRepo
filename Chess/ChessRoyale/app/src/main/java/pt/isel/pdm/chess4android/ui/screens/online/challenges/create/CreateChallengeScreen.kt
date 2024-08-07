package pt.isel.pdm.chess4android.ui.screens.online.challenges.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.compose.BuildDefaultOutlinedTextField
import pt.isel.pdm.chess4android.domain.ScreenState
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.navigation.Screen
import pt.isel.pdm.chess4android.ui.theme.White
import pt.isel.pdm.chess4android.utils.BuildMessage

@Composable
fun BuildCreateChallengeScreen(
    navController: NavController,
    screenState: ScreenState,
    onCreate: (name: String, message: String, onAccept: (info: ChallengeInfo) -> Unit) -> Unit,
    onCancel: () -> Unit,
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
    if (screenState == ScreenState.Loaded) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            var name by remember { mutableStateOf("") }
            var message by remember { mutableStateOf("") }
            BuildDefaultOutlinedTextField(
                placeholder = R.string.create_game_prompt_challenger_name,
                value = name,
                onChange = { name = it}
            )
            Spacer(modifier = Modifier.height(10.dp))
            BuildDefaultOutlinedTextField(
                placeholder = R.string.create_game_prompt_message,
                value = message,
                onChange = { message = it}
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                onCreate(name, message) {
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
            onCancel()
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
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable { onCancel() }
            )
        }
    }
}