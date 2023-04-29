package pt.isel.pdm.chess4android.ui.screens.offline.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.domain.offline.OfflineBoard
import pt.isel.pdm.chess4android.domain.online.GameState
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.utils.EndgameResult
import pt.isel.pdm.chess4android.utils.PaintResults
import pt.isel.pdm.chess4android.views.DrawController
import javax.inject.Inject
import kotlin.collections.set

private const val ACTIVITY_STATE_BOARD = "BoardActivity.board"

/**
 * ViewModel to offline 1vs1 mode
 */
@HiltViewModel
class OfflineScreenViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {

    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private val drawController = DrawController()
    private var selectedPiece: Piece? = null
    private var gameBoard = OfflineBoard()

    private val _board: MutableState<OfflineBoard> = mutableStateOf(gameBoard)
    val offlineBoardData: State<OfflineBoard> = _board

    private val _promotion: MutableState<Team?> = mutableStateOf(null)
    val promotion: State<Team?> = _promotion

    private val _paintResults: MutableState<PaintResults> = mutableStateOf(PaintResults())
    val paintResults: State<PaintResults> = _paintResults

    init {
        if (state.contains(ACTIVITY_STATE_BOARD)) {
            val b = state.get<OfflineBoard>(ACTIVITY_STATE_BOARD)
            if (b != null) {
                gameBoard = b
                _board.value = gameBoard
            }
        }
    }

    /**
     * Based in two main paths.
     * One selects the piece that the player clicked, if allowed, and draws the moves it can do on
     * the board. The other updates the board if the selected tile is a possible move between the
     * moves that were previously highlighted and clears highlights from the move and
     * updates the board based on the new gameState
     * @param x - The row of the selected tile
     * @param y - The column of the selected tile
     */
    fun movePiece(x: Int, y: Int) {
        if (gameBoard.gameState == GameState.XequeMate ||
            gameBoard.gameState == GameState.Forfeit
        ) return
        val locked = selectedPiece
        if (locked != null) {
            selectedPiece = null
            drawController.cleanSelectedPiece()

            if (!(locked.location.x == x && locked.location.y == y)) {
                val location = Location(x, y)
                if (acquiredMoves[locked]?.any { it.x == location.x && it.y == location.y } == true) {
                    if (gameBoard.gameState == GameState.Xeque) {
                        drawController.cleanCheck()
                    }
                    gameBoard = gameBoard.movePiece(locked.location, location)
                    _board.value = gameBoard
                    acquiredMoves.clear()
                    if (gameBoard.specialMoveResult == null) {
                        applyNewState()
                    } else {
                        _promotion.value = gameBoard.playingTeam
                    }
                    return
                } else {
                    _paintResults.value = drawController.getActualResults()
                }
            } else {
                _paintResults.value = drawController.getActualResults()
                return
            }
        }

        val clicked: Piece = gameBoard.board[y][x]
        if (clicked.team != gameBoard.playingTeam) return
        selectedPiece = clicked
        if (!acquiredMoves.containsKey(clicked)) {
            val moves = clicked.getMoves(
                gameBoard.board,
                gameBoard.getKing(gameBoard.playingTeam)
            )
            acquiredMoves[clicked] = moves
        }
        _paintResults.value = drawController.drawSelectedPiece(
            clicked.location,
            acquiredMoves[clicked]!!
        )
    }

    /**
     * Function that promotes a piece and publishes the result
     */
    fun promote(id: Char) {
        gameBoard = gameBoard.promotion(id)
        _board.value = gameBoard
        _promotion.value = null
        applyNewState()
    }

    /**
     * Applies the new game state
     */
    private fun applyNewState() {
        when (gameBoard.gameState) {
            GameState.Xeque -> {
                _paintResults.value = drawController.drawXeque(
                    gameBoard.getKing(gameBoard.playingTeam).location
                )
            }
            GameState.XequeMate -> {
                _paintResults.value = PaintResults(
                    selectedPiece = null,
                    highlightPieces = null,
                    xequePiece = null,
                    endgameResult = EndgameResult(
                        gameBoard.getWinningPieces().toList(),
                        gameBoard.playingTeam.other
                    )
                )
            }
            else -> {
                _paintResults.value = drawController.getActualResults()
            }
        }
    }

    /**
     * Sets the game state as Forfeit and warns the other player.
     * The forfeit is only possible during the player turn
     */
    fun forfeit() {
        gameBoard = gameBoard.forfeit()
        _paintResults.value = PaintResults(
            selectedPiece = null,
            highlightPieces = null,
            xequePiece = null,
            endgameResult = EndgameResult(
                gameBoard.getWinningPieces().toList(),
                gameBoard.playingTeam.other
            )
        )
    }

    /**
     * Saves the game state when the ViewModel is about to be destroyed. It's saved when the
     * ViewModel is finished by normal termination even not being necessary but avoids
     * the game state to be saved in every move.
     */
    override fun onCleared() {
        super.onCleared()
        state[ACTIVITY_STATE_BOARD] = gameBoard
    }
}