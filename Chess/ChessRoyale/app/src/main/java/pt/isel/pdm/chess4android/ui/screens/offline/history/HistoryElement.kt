package pt.isel.pdm.chess4android.ui.screens.offline.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
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
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = date,
                    color = Color.White,
                    fontSize = 20.sp
                )
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