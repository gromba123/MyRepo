package pt.isel.pdm.chess4android.ui.screens.offline.game

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.ui.theme.Board
import pt.isel.pdm.chess4android.utils.BuildPromotion
import pt.isel.pdm.chess4android.utils.getColor
import pt.isel.pdm.chess4android.utils.getPiece

val CELL_HEIGHT = 45.dp
const val SIDE = 8

@Composable
fun BuildOfflineBoard(
    viewModel: OfflineScreenViewModel = hiltViewModel()
) {
    val board = viewModel.offlineBoardData.observeAsState().value!!
    val paintResults = viewModel.paintResults.observeAsState().value!!
    val promotion = viewModel.promotion.observeAsState(initial = null).value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (
                modifier = Modifier
                    .border(
                        2.dp,
                        if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
            ){
                repeat(SIDE) { row ->
                    Row {
                        repeat(SIDE) { column ->
                            Box(
                                modifier = Modifier
                                    .height(CELL_HEIGHT)
                                    .width(CELL_HEIGHT)
                                    .background(if ((row + column) % 2 == 0) Board else Color.White)
                                    .clickable {
                                        viewModel.movePiece(column, row)
                                    },
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
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { viewModel.forfeit() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.forfeit),
                    color = MaterialTheme.colors.primary
                )
            }
        }
        if (promotion != null) {
            BuildPromotion(team = promotion) {
                viewModel.promote(it)
            }
        }
    }
}