package com.example.myfootballcollection.ui.screens.auth.create.steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myfootballcollectionkmp.domain.model.football.Team
import com.example.myfootballcollectionkmp.ui.composeUtils.BuildArrowBack
import com.example.myfootballcollectionkmp.ui.theme.Gray

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BuildSearchTeam(
    selectedTeams: List<Team>,
    searchText: String,
    teams: List<Team>,
    isSearching: Boolean,
    onSearchTextChange: (text: String) -> Unit,
    onTeamSelected: (Team) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BuildArrowBack { onBack() }
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                        fontSize = 28.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    append("Choose your team\n")
                }
                withStyle(style = SpanStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    append("Every collector should have a favorite team. You can pick up to 5. Choose wisely")
                }
            }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") },
            shape = RoundedCornerShape(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Spacer(modifier = Modifier.padding(5.dp))
            val columns = 3
            FlowRow(
                maxItemsInEachRow = columns
            ) {
                teams.forEach { team ->
                    Button(
                        modifier = Modifier
                            .height(125.dp)
                            .weight(1f),
                        shape = RoundedCornerShape(5.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 1.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedTeams.any { it.id == team.id }) {
                                Gray
                            } else {
                                Color.Transparent
                            }
                        ),
                        onClick = { onTeamSelected(team) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(team.logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(60.dp)
                                )
                            }
                            Text(
                                text = team.name,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}