package pt.isel.pdm.chess4android.ui.screens.offline.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.isel.pdm.chess4android.domain.offline.OfflineBoard
import pt.isel.pdm.chess4android.ui.theme.Board
import pt.isel.pdm.chess4android.utils.PaintResults
import pt.isel.pdm.chess4android.utils.getColor
import pt.isel.pdm.chess4android.utils.getPiece

@Composable
fun OfflinePuzzleBoard(
    board: OfflineBoard,
    paintResults: PaintResults,
    viewModel: OfflineScreenViewModel
) {
    Column (
        modifier = Modifier
            .border(
                2.dp,
                if (isSystemInDarkTheme()) Color.White else Color.Black
            )
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.End)
        ) {
            board.getRemovedBlacks().forEach {
                val id = getPiece(it)!!
                Image(
                    painter = painterResource(id = id),
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        repeat(SIDE) { row ->
            Row {
                repeat(SIDE) { column ->
                    Box(
                        modifier = Modifier
                            .height(CELL_HEIGHT)
                            .width(CELL_HEIGHT)
                            .background(if ((row + column) % 2 == 0) Board else Color.White)
                            .clickable { viewModel.movePiece(column, row) },
                        contentAlignment = Alignment.Center
                    ) {
                        val id = getPiece(board.board[row][column])
                        val color = getColor(board.board[row][column], paintResults)
                        if (color != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(0.75F)
                                    .clip(RoundedCornerShape(50))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                ) {}
                            }
                        }
                        if (id != null) {
                            Image(painter = painterResource(id = id), contentDescription = "")
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            board.getRemovedWhites().forEach {
                val id = getPiece(it)!!
                Image(
                    painter = painterResource(id = id),
                    modifier = Modifier.size(30.dp),
                    contentDescription = ""
                )
            }
        }
    }
}