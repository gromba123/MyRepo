package pt.isel.pdm.chess4android.ui.screens.online.games

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.dataAccess.GamesRepository
import pt.isel.pdm.chess4android.domain.online.GameState
import pt.isel.pdm.chess4android.domain.online.OnlineBoard
import pt.isel.pdm.chess4android.domain.online.OnlineGameState
import pt.isel.pdm.chess4android.domain.online.toGameState
import pt.isel.pdm.chess4android.domain.pieces.King
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.utils.DrawResults
import pt.isel.pdm.chess4android.utils.EndgameResult
import pt.isel.pdm.chess4android.views.DrawControllerTemp
import javax.inject.Inject

/**
 * ViewModel for the [OnlineActivity]
 */
@HiltViewModel
class OnlineViewModel @Inject constructor(
    private val gamesRepository: GamesRepository
) : ViewModel() {

    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private val drawControllerTemp = DrawControllerTemp()
    private lateinit var gameSubscription : ListenerRegistration
    private lateinit var gameBoard: OnlineBoard
    private lateinit var initialGameState: OnlineGameState
    private var selectedPiece: Piece? = null

    private val _game: MutableLiveData<Result<OnlineBoard>> = MutableLiveData()
    val game: LiveData<Result<OnlineBoard>> = _game

    private val _xequeMate = MutableLiveData<EndgameResult>()
    val xequeMate = _xequeMate

    private val _draw = MutableLiveData<GameState>()
    val draw = _draw

    private val _acceptDraw = MutableLiveData<Boolean>()
    val acceptDraw = _acceptDraw

    private val _paint = MutableLiveData<DrawResults?>()
    val paint = _paint

    private val _promotion = MutableLiveData<Boolean>()
    val promotion = _promotion

    fun initGame(initialState: OnlineGameState, localPlayer: Team) {
        initialGameState = initialState
        gameSubscription = gamesRepository
            .subscribeToGameStateChanges (
                challengeId = initialState.id,
                onSubscriptionError = { _game.value = Result.failure(it) },
                onGameStateChange = { onStateUpdate(it) }
            )
        gameBoard = OnlineBoard(playerTeam = localPlayer)
        _game.value = Result.success(gameBoard)
    }

    /**
     * Updates the GameBoard based on the new OnlineGameState.
     * Contains a mechanism that prevents from update when the player
     * that played updates the document on FireStore. The subscription
     * doesn't differ the origin of the update.
     * @param state - Most recent game state
     */
    private fun onStateUpdate(state: OnlineGameState) {
        if (state.player != gameBoard.playerTeam) {
            if (state.state != GameState.Forfeit && state.state != GameState.Draw) {
                //Clean the previous xeque
                if (gameBoard.gameState == GameState.Xeque) {
                    _paint.value = drawControllerTemp.drawXeque(null)
                }
                gameBoard = gameBoard.moveOpponentPiece(state.moves)
                _game.value = Result.success(gameBoard)
                if (gameBoard.gameState == GameState.Xeque) {
                    _paint.value =
                        drawControllerTemp.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
                }
                else if (gameBoard.gameState == GameState.XequeMate)
                    checkMate(gameBoard.playerTeam.other)
            }
            else if (state.state == GameState.Draw) {
                gameBoard = gameBoard.swapPlayingTeam()
                when {
                    state.moves.isEmpty() -> _draw.value = GameState.Draw
                    state.moves == "accepted" -> _acceptDraw.value = true
                    else -> _acceptDraw.value = false
                }
            } else {
                gameBoard = gameBoard.forfeit()
                checkMate(gameBoard.playerTeam)
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
        if (gameBoard.isPlaying()) {
            val locked = selectedPiece
            if (locked != null) {
                selectedPiece = null
                _paint.value = drawControllerTemp.drawSelectedPiece(null)
                _paint.value = drawControllerTemp.drawHighlight(null)

                if (!(locked.location.x == x && locked.location.y == y)) {
                    val location = Location(x, y)
                    if (acquiredMoves[locked]?.contains(location) == true) {
                        if (gameBoard.gameState == GameState.Xeque) {
                            if (locked !is King) {
                                _paint.value = drawControllerTemp.drawXeque(null)
                            } else {
                                drawControllerTemp.cleanCheck()
                            }
                        }
                        gameBoard = gameBoard.movePiece(locked.location, location)
                        _game.value = Result.success(gameBoard)
                        acquiredMoves.clear()

                        if (gameBoard.specialMoveResult == null) {
                            applyNewState()
                            updateOnlineBoard()
                        }
                        else {
                            _promotion.value = true
                        }
                        return
                    } else {
                        paintSpecialXeque(locked)
                    }
                } else {
                    paintSpecialXeque(locked)
                    return
                }
            }

            val clicked: Piece = gameBoard.board[y][x]
            if (clicked.team != gameBoard.playingTeam) return
            selectedPiece = clicked
            _paint.value = drawControllerTemp.drawSelectedPiece(clicked.location)
            if (!acquiredMoves.containsKey(clicked)) {
                val moves = clicked.getMoves(
                    gameBoard.board,
                    gameBoard.getKing(gameBoard.playingTeam)
                )
                acquiredMoves[clicked] = moves
            }
            _paint.value = drawControllerTemp.drawHighlight(acquiredMoves[clicked])
        }
    }

    /**
     * Paints the special xeque occasions. It happens when the player
     * selects the king when it's in xeque and swap for another piece.
     * When the piece is selected then cleared the xeque visual mark disappears
     * ant the needs to be painted again
     */
    private fun paintSpecialXeque(locked: Piece) {
        if (locked is King && gameBoard.gameState == GameState.Xeque) {
            drawControllerTemp.cleanCheck()
            _paint.value =
                drawControllerTemp.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
        }
    }

    /**
     * Publishes the new board
     */
    private fun updateOnlineBoard() {
        gamesRepository.updateGameState(
            gameState = gameBoard.toGameState(initialGameState.id),
            onComplete = { result ->
                result.onFailure { _game.value = Result.failure(it) }
            }
        )
    }

    /**
     * Applies the new game state
     */
    private fun applyNewState() {
        when (gameBoard.gameState) {
            GameState.Xeque -> {
                _paint.value =
                    drawControllerTemp.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
            }
            GameState.XequeMate -> {
                _paint.value = drawControllerTemp.drawHighlight(null)
                checkMate(gameBoard.playerTeam)
            }
            else -> {}
        }
    }

    /**
     * Function that promotes a piece and publishes the result
     */
    /*
    fun promote(piece: String) {
        val resources = getApplication<ChessRoyaleApplication>().resources
        gameBoard = gameBoard.promotionByResources(piece, resources)
        _game.value = Result.success(gameBoard)
        applyNewState()
        updateOnlineBoard()
    }

     */

    /**
     * Sets the game state as Forfeit and warns the other player.
     * The forfeit is only possible during the player turn
     */
    fun forfeit() {
        if (gameBoard.isPlaying()) {
            gameBoard = gameBoard.forfeit()

            _paint.value = drawControllerTemp.drawHighlight(null)
            _paint.value = drawControllerTemp.drawSelectedPiece(null)
            _paint.value = drawControllerTemp.drawXeque(null)

            gamesRepository.updateGameState(
                gameState = gameBoard.toGameState(initialGameState.id, ""),
                onComplete = { result ->
                    result.onFailure { _game.value = Result.failure(it) }
                }
            )

            checkMate(gameBoard.playerTeam.other)
        }
    }

    /**
     * Accepts or rejects a draw and notifies the other player and updates the screen
     * based on the decision
     * @param accepted - if true then the draw was accepted
     */
    fun acceptDraw(accepted: Boolean) {
        gameBoard = gameBoard.swapPlayingTeam()
        val move = if (accepted) "accepted" else "rejected"
        gamesRepository.updateGameState(
            gameState = gameBoard.toGameState(initialGameState.id, move, GameState.Draw),
            onComplete = { result ->
                result.onFailure { _game.value = Result.failure(it) }
            }
        )
        _acceptDraw.value = accepted
    }

    /**
     * Purpose a draw to the other player
     */
    fun purposeDraw() {
        if (gameBoard.isPlaying()) {
            gameBoard = gameBoard.swapPlayingTeam()
            gamesRepository.updateGameState(
                gameState = gameBoard.toGameState(initialGameState.id, "", GameState.Draw),
                onComplete = { result ->
                    result.onFailure { /*TODO*/ }
                }
            )
        }
    }

    /**
     * Updates the screen based on the xeque-mate information
     * @param team - Player that won the game
     */
    private fun checkMate(team: Team) {
        val result = EndgameResult(
            gameBoard.getWinningPieces().toList(),
            team
        )
        _xequeMate.value = result
    }

    /**
     * This method will remove the game from the games collection before the ViewModel
     * is destroyed.
     * It's an idempotent action.
     */
    override fun onCleared() {
        super.onCleared()
        gamesRepository.deleteGame(
            challengeId = initialGameState.id,
            onComplete = { }
        )
        gameSubscription.remove()
    }
}