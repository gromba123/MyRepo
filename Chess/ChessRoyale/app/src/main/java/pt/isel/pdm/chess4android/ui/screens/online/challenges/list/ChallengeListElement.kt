package pt.isel.pdm.chess4android.ui.screens.online.challenges.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.domain.online.ChallengeInfo

@Composable
fun BuildListItem(
    item: ChallengeInfo,
    onClick: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.fillMaxSize(),
            onClick = onClick
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.challengerName,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = item.challengerMessage,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}