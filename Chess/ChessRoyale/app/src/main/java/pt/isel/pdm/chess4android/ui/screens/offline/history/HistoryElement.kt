package pt.isel.pdm.chess4android.ui.screens.offline.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.puzzle.PuzzleHistoryDTO
import java.sql.Date

@Composable
fun BuildListItem(
    dto: PuzzleHistoryDTO,
    onClick: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        val date = Date(dto.timestamp.toLong()).toString()
        val text = convertState(dto.state)
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
                    text = date,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(2.dp))
                Text(
                    text = stringResource(id = text),
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Converts the received state to the respective string
 */
private fun convertState(state: Int): Int =
    when(state) {
        0 -> R.string.unsolved
        1 -> R.string.solving
        else -> R.string.solved
    }