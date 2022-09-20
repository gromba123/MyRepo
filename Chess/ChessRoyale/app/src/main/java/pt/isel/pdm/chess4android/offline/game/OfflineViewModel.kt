package pt.isel.pdm.chess4android.offline.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.PuzzleOfDayApplication
import pt.isel.pdm.chess4android.offline.pieces.King
import pt.isel.pdm.chess4android.offline.pieces.Location
import pt.isel.pdm.chess4android.offline.pieces.Piece
import pt.isel.pdm.chess4android.online.games.GameState
import pt.isel.pdm.chess4android.utils.EndgameResult
import pt.isel.pdm.chess4android.utils.PaintResults
import pt.isel.pdm.chess4android.views.DrawControllerImp
import javax.inject.Inject
import kotlin.collections.set

private const val ACTIVITY_STATE_BOARD = "BoardActivity.board"

/**
 * ViewModel to offline 1vs1 mode
 */
@HiltViewModel
class OfflineViewModel @Inject constructor(
    application: Application,
    private val state: SavedStateHandle
) : AndroidViewModel(application) {

    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private val drawController = DrawControllerImp()
    private var selectedPiece: Piece? = null
    private var gameBoard = OfflineBoard()

    private val _board = MutableLiveData<OfflineBoard>()
    val offlineBoardData: LiveData<OfflineBoard> = _board

    private val _promotion = MutableLiveData<Boolean>()
    val promotion = _promotion

    private val _paintResults = MutableLiveData<PaintResults>(PaintResults())
    val paintResults: LiveData<PaintResults> = _paintResults

    init {
        if (state.contains(ACTIVITY_STATE_BOARD)) {
            val b = state.get<OfflineBoard>(ACTIVITY_STATE_BOARD)
            if (b != null) {
                gameBoard = b
                _board.value = gameBoard
            }
        }
        else _board.value = OfflineBoard()
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
                gameBoard.gameState == GameState.Forfeit) return
        val locked = selectedPiece
        if (locked != null) {
            selectedPiece = null
            drawController.cleanSelectedPiece()

            if (!(locked.location.x == x && locked.location.y == y)) {
                val location = Location(x, y)
                if (acquiredMoves[locked]?.contains(location) == true) {
                    if (gameBoard.gameState == GameState.Xeque) {
                        drawController.cleanCheck()
                    }
                    gameBoard = gameBoard.movePiece(locked.location, location)
                    _board.value = gameBoard
                    acquiredMoves.clear()
                    if (gameBoard.specialMoveResult == null) {
                        applyNewState()
                    } else {
                        _promotion.value = true
                    }
                    return
                } else {
                    paintSpecialCheck(locked)
                }
            } else {
                paintSpecialCheck(locked)
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
     * Paints the special xeque occasions. It happens when the player
     * selects the king when it's in xeque and swap for another piece.
     * When the piece is selected then cleared the xeque visual mark disappears
     * ant the needs to be painted again
     */
    private fun paintSpecialCheck(locked: Piece) {
        if (locked is King && gameBoard.gameState == GameState.Xeque) {
            drawController.cleanCheck()
            _paintResults.value = drawController.drawXeque(
                gameBoard.getKing(gameBoard.playingTeam).location
            )
        } else {
            _paintResults.value = drawController.getActualResults()
        }
    }

    /**
     * Function that promotes a piece and publishes the result
     */
    fun promote(piece: String) {
        val resources = getApplication<PuzzleOfDayApplication>().resources
        gameBoard = gameBoard.promotion(piece, resources)
        _board.value = gameBoard
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