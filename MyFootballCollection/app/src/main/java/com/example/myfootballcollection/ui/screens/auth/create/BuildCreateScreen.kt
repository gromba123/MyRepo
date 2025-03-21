package com.example.myfootballcollection.ui.screens.auth.create

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfootballcollection.R
import com.example.myfootballcollection.ui.screens.auth.create.steps.BuildSearchTeamStep
import com.example.myfootballcollection.ui.screens.auth.create.steps.BuildUserInfoStep

private enum class Step(@StringRes val title: Int) {
    UserInfo(R.string.step_user_info_title),
    SearchTeam(R.string.step_search_team_title)
}

private enum class Direction {
    Backward,
    Forward
}

private fun calculateProgressIndicator(step: Step) =
    when(step) {
        Step.UserInfo -> 0.5f
        else -> 1f
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildCreateScreen(
    navController: NavController,
    viewModel: CreateScreenViewModel
) {
    val context = LocalContext.current
    var currentStep by remember { mutableIntStateOf(Step.UserInfo.ordinal) }
    var currentImage: Bitmap by remember {
        mutableStateOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.placeholder)
        )
    }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var selectedTeams by remember { mutableStateOf(emptyList<Int>()) }

    val searchText by viewModel.searchText.collectAsState()
    val teams by viewModel.teams.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        when(Step.entries[currentStep]) {
            Step.UserInfo -> BuildUserInfoStep(
                firstName = firstName,
                lastName = lastName,
                username = username,
                currentImage = currentImage,
                onImageChange = { currentImage = it },
                onFirstNameChange = { firstName = it },
                onLastNameChange = { lastName = it },
                onUsernameChange = { username = it }
            )
            else -> BuildSearchTeamStep(
                selectedTeams = selectedTeams,
                searchText = searchText,
                teams = teams,
                isSearching = isSearching,
                onSearchTextChange = viewModel::onSearchTextChange,
                onTeamSelected = { team ->
                    selectedTeams = if (selectedTeams.contains(team.id)) {
                        selectedTeams.filter { it != team.id }
                    } else {
                        selectedTeams + team.id
                    }
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(onClick = {
                currentStep = calculateNextStep(Direction.Backward, currentStep)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(onClick = {
                currentStep = calculateNextStep(Direction.Forward, currentStep)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Front",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

private fun calculateNextStep(direction: Direction, currentStep: Int): Int {
    val steps = Step.entries.size
    val newStep = if (direction == Direction.Backward) currentStep - 1 else currentStep + 1
    return if (newStep in 0..<steps) newStep else currentStep
}