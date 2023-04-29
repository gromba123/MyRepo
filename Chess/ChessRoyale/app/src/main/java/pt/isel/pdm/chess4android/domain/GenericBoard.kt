package pt.isel.pdm.chess4android.domain

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.ui.screens.offline.game.CELL_HEIGHT
import pt.isel.pdm.chess4android.ui.screens.offline.game.SIDE
import pt.isel.pdm.chess4android.ui.theme.Board
import pt.isel.pdm.chess4android.utils.PaintResults
import pt.isel.pdm.chess4android.utils.getColor
import pt.isel.pdm.chess4android.utils.getPiece

@Composable
fun GenericBoard(
    board: List<List<Piece>>,
    paintResults: PaintResults?,
    onClick: (Int, Int) -> Unit
) {
    repeat(SIDE) { row ->
        Row {
            repeat(SIDE) { column ->
                Box(
                    modifier = Modifier
                        .height(CELL_HEIGHT)
                        .width(CELL_HEIGHT)
                        .background(if ((row + column) % 2 == 0) Board else Color.White)
                        .clickable {
                            onClick(column, row)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val pieceId = getPiece(board[row][column])
                    val color = if (paintResults != null) getColor(board[row][column], paintResults) else null
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
                    if (pieceId != null) {
                        Image(painter = painterResource(id = pieceId), contentDescription = "")
                    }
                }
            }
        }
    }
    /*
    repeat(SIDE) { row ->
        Row {
            repeat(SIDE) { column ->
                Box(
                    modifier = Modifier
                        .height(CELL_HEIGHT)
                        .width(CELL_HEIGHT)
                        .background(if ((row + column) % 2 == 0) Board else Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    val pieceId = getPiece(board[row][column])
                    if (pieceId != null) {
                        Image(painter = painterResource(id = pieceId), contentDescription = "")
                    }
                }
            }
        }
    }

     */
}