package com.example.myfootballcollectionkmp.ui.screens.auth.create

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myfootballcollection.ui.screens.auth.create.steps.BuildSearchTeam
import com.example.myfootballcollection.utils.IMAGE_DIAMETER
import com.example.myfootballcollection.utils.OFFSET
import com.example.myfootballcollection.utils.modifyOrientation
import com.example.myfootballcollectionkmp.domain.model.football.Team
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildDefaultOutlinedTextField
import com.example.myfootballcollectionkmp.ui.theme.White
import myfootballcollectionkmp.composeapp.generated.resources.Res
import myfootballcollectionkmp.composeapp.generated.resources.first_name
import myfootballcollectionkmp.composeapp.generated.resources.ic_edit
import myfootballcollectionkmp.composeapp.generated.resources.last_name
import myfootballcollectionkmp.composeapp.generated.resources.placeholder
import myfootballcollectionkmp.composeapp.generated.resources.username
import org.jetbrains.compose.resources.painterResource

private const val MAX_SIZE = 5

private enum class Step {
    UserInfo,
    SearchTeam
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BuildCreateScreen(
    navController: NavController,
    viewModel: CreateScreenViewModel
) {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf(Step.UserInfo) }
    var currentImage: Bitmap by remember {
        mutableStateOf(
            BitmapFactory.decodeResource(context.resources, Res.drawable.placeholder)
        )
    }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var selectedTeams by remember { mutableStateOf(emptyList<Team>()) }

    val searchText by viewModel.searchText.collectAsState()
    val teams by viewModel.teams.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    if (currentScreen == Step.UserInfo) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            val photoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia()
            ) { uri ->
                if (uri != null) {
                    val stream = context.contentResolver.openInputStream(uri)
                    val bytes = stream!!.readBytes()
                    val bitmap = modifyOrientation(
                        BitmapFactory.decodeByteArray(bytes, 0, bytes.size),
                        stream
                    )
                    currentImage = bitmap
                    stream.close()
                }
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    ) {
                        append("Complete your account\n")
                    }
                    withStyle(style = SpanStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    ) {
                        append("Your account is almost finished, just a few more steps before you can start")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Row(
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        bottom = 20.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        bitmap = currentImage.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(IMAGE_DIAMETER)
                            .clip(CircleShape)
                    )
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_edit),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(White),
                        modifier = Modifier
                            .size(25.dp)
                            .offset(x = OFFSET)
                            .clickable {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                    )
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        ) {
                            append("Profile Picture\n")
                        }
                        withStyle(style = SpanStyle(
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        ) {
                            append("Every collector is part of the family")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            BuildDefaultOutlinedTextField(
                placeholder = Res.string.first_name,
                value = firstName,
                onChange = { firstName = it }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            BuildDefaultOutlinedTextField(
                placeholder = Res.string.last_name,
                value = lastName,
                onChange = { lastName = it }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            BuildDefaultOutlinedTextField(
                placeholder = Res.string.username,
                value = username,
                onChange = { username = it }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    ) {
                        append("Choose your favorite team\n")
                    }
                    withStyle(style = SpanStyle(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    ) {
                        append("Every collector should have a favorite team. You can pick up to 5 teams, choose wisely. This can be changed later")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            FlowRow {
                selectedTeams.forEach {
                    AssistChip(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(5.dp),
                        label = {
                            Text(
                                text = it.name,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Image(
                                painter = rememberAsyncImagePainter(it.logo),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (selectedTeams.isEmpty()) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.NoteAdd,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable { currentScreen = Step.SearchTeam }
                    )
                    Text(
                        text = "Add team",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                } else {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_edit),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(White),
                        modifier = Modifier
                            .size(25.dp)
                            .clickable { currentScreen = Step.SearchTeam }
                    )
                    Text(
                        text = "Change team",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    } else {
        BuildSearchTeam(
            selectedTeams = selectedTeams,
            searchText = searchText,
            teams = teams,
            isSearching = isSearching,
            onSearchTextChange = viewModel::onSearchTextChange,
            onTeamSelected = { team ->
                selectedTeams = if (selectedTeams.any { it.id == team.id }) {
                    selectedTeams.filter { it.id != team.id }
                } else if (selectedTeams.size < MAX_SIZE) {
                    selectedTeams + team
                } else {
                    selectedTeams
                }
            },
            onBack = { currentScreen = Step.UserInfo }
        )
    }
}