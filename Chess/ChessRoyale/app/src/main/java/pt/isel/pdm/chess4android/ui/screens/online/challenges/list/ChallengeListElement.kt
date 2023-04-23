package pt.isel.pdm.chess4android.ui.screens.online.challenges.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            onClick = onClick
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = item.challengerName,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(2.dp))
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