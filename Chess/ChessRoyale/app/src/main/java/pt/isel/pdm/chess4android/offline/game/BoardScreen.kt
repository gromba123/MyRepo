package pt.isel.pdm.chess4android.offline.game

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pt.isel.pdm.chess4android.utils.getPiece

private val CELL_HEIGHT = 45.dp
private const val SIDE = 8

@Composable
fun BuildOfflineBoard() {
    val viewModel = hiltViewModel<OfflineViewModel>()
    val board = viewModel.offlineBoardData.observeAsState().value!!
    val results = viewModel.paint.observeAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier
                .border(2.dp, if (isSystemInDarkTheme()) Color.White else Color.Black)
        ){
            repeat(SIDE) { row ->
                Row {
                    repeat(SIDE) { column ->
                        Box(
                            modifier = Modifier
                                .height(CELL_HEIGHT)
                                .width(CELL_HEIGHT)
                                .background(if ((row + column) % 2 == 0) Color.Black else Color.White)
                                .clickable {
                                    viewModel.movePiece(column, row)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            val id = getPiece(board.board[row][column])
                            if (results != null) {
                                val filtered = results.list.filter { it.x == column && it.y == row }
                                if (filtered.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .border(BorderStroke(0.dp, Color.Black), RoundedCornerShape(50))
                                            .background(Color.Blue)
                                    ) {

                                    }
                                }
                            }
                            if (id != null) {
                                Image(painter = painterResource(id = id), contentDescription = "")
                            }
                        }
                    }
                }
            }
        }
    }
}