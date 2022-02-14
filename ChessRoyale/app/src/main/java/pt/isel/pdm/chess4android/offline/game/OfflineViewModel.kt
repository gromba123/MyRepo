package pt.isel.pdm.chess4android.offline.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import pt.isel.pdm.chess4android.DrawResults
import pt.isel.pdm.chess4android.EndgameResult
import pt.isel.pdm.chess4android.PuzzleOfDayApplication
import pt.isel.pdm.chess4android.offline.pieces.King
import pt.isel.pdm.chess4android.offline.pieces.Location
import pt.isel.pdm.chess4android.offline.pieces.Piece
import pt.isel.pdm.chess4android.online.games.GameState
import pt.isel.pdm.chess4android.views.DrawController
import kotlin.collections.set

private const val ACTIVITY_STATE_BOARD = "BoardActivity.board"

/**
 * ViewModel to offline 1vs1 mode
 */
class OfflineViewModel(
    application: Application,
    private val state: SavedStateHandle
    ) : AndroidViewModel(application) {

    private val _paint = MutableLiveData<DrawResults?>()
    private val _board = MutableLiveData<OfflineBoard>()
    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private val _xequeMate = MutableLiveData<EndgameResult>()
    private val drawController = DrawController()
    private var selectedPiece: Piece? = null
    private var gameBoard = OfflineBoard()
    private val _promotion = MutableLiveData<Boolean>()

    val offlineBoardData: LiveData<OfflineBoard> = _board
    val xequeMate = _xequeMate
    val paint = _paint
    val promotion = _promotion

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
            _paint.value = drawController.drawSelectedPiece(null)
            _paint.value = drawController.drawHighlight(null)

            if (!(locked.location.x == x && locked.location.y == y)) {
                val location = Location(x, y)
                if (acquiredMoves[locked]?.contains(location) == true) {
                    if (gameBoard.gameState == GameState.Xeque) {
                        if (locked !is King) {
                            _paint.value = drawController.drawXeque(null)
                        } else {
                            drawController.cleanCheck()
                        }
                    }
                    gameBoard = gameBoard.movePiece(locked.location, location)
                    _board.value = gameBoard
                    acquiredMoves.clear()
                    if (gameBoard.specialMoveResult == null) {
                        applyNewState()
                    }
                    else {
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
        _paint.value = drawController.drawSelectedPiece(clicked.location)
        if (!acquiredMoves.containsKey(clicked)) {
            val moves = clicked.getMoves(
                gameBoard.board,
                gameBoard.getKing(gameBoard.playingTeam)
            )
            acquiredMoves[clicked] = moves
        }
        _paint.value = drawController.drawHighlight(acquiredMoves[clicked])
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
            _paint.value =
                drawController.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
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
                _paint.value =
                    drawController.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
            }
            GameState.XequeMate -> {
                _paint.value = drawController.drawHighlight(null)
                val result = EndgameResult(
                    gameBoard.getWinningPieces().toList(),
                    gameBoard.playingTeam.other
                )
                _xequeMate.value = result
            }
            else -> {}
        }
    }

    /**
     * Sets the game state as Forfeit and warns the other player.
     * The forfeit is only possible during the player turn
     */
    fun forfeit() {
        gameBoard = gameBoard.forfeit()

        _paint.value = drawController.drawHighlight(null)
        _paint.value = drawController.drawSelectedPiece(null)
        _paint.value = drawController.drawXeque(null)

        val result = EndgameResult(
            gameBoard.getWinningPieces().toList(),
            gameBoard.playingTeam.other
        )
        _xequeMate.value = result
    }

    /**
     * Saves the game state when the ViewModel is about to be destroyed. It's saved when the
     * ViewModel is finished by normal termination even not being necessary but avoids
     * the game state to be saved in every move.
     */
    override fun onCleared() {
        super.onCleared()
        state.set(ACTIVITY_STATE_BOARD, gameBoard)
    }
}