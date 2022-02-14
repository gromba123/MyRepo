package pt.isel.pdm.chess4android.online.games

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pt.isel.pdm.chess4android.DrawResults
import pt.isel.pdm.chess4android.EndgameResult
import pt.isel.pdm.chess4android.PuzzleOfDayApplication
import pt.isel.pdm.chess4android.offline.pieces.King
import pt.isel.pdm.chess4android.offline.pieces.Location
import pt.isel.pdm.chess4android.offline.pieces.Piece
import pt.isel.pdm.chess4android.offline.pieces.Team
import pt.isel.pdm.chess4android.views.DrawController

/**
 * ViewModel for the [OnlineActivity]
 */
class OnlineViewModel (
    application: Application,
    private val initialGameState: OnlineGameState,
    private val localPlayer: Team
) : AndroidViewModel(application) {

    private val _paint = MutableLiveData<DrawResults?>()
    private val _xequeMate = MutableLiveData<EndgameResult>()
    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private val _draw = MutableLiveData<GameState>()
    private val _acceptDraw = MutableLiveData<Boolean>()
    private val _promotion = MutableLiveData<Boolean>()
    private val drawController = DrawController()

    private val _game: MutableLiveData<Result<OnlineBoard>> by lazy {
        MutableLiveData(Result.success(OnlineBoard(playerTeam = localPlayer)))
    }

    private val gameSubscription = getApplication<PuzzleOfDayApplication>()
        .gamesRepository.subscribeToGameStateChanges (
            challengeId = initialGameState.id,
            onSubscriptionError = { _game.value = Result.failure(it) },
            onGameStateChange = { onStateUpdate(it) }
        )

    private var selectedPiece: Piece? = null
    private var gameBoard = OnlineBoard(playerTeam = localPlayer)

    val game: LiveData<Result<OnlineBoard>> = _game
    val xequeMate = _xequeMate
    val draw = _draw
    val acceptDraw = _acceptDraw
    val paint = _paint
    val promotion = _promotion

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
                    _paint.value = drawController.drawXeque(null)
                }
                gameBoard = gameBoard.moveOpponentPiece(state.moves)
                _game.value = Result.success(gameBoard)
                if (gameBoard.gameState == GameState.Xeque) {
                    _paint.value =
                        drawController.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
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
    }

    /**
     * Paints the special xeque occasions. It happens when the player
     * selects the king when it's in xeque and swap for another piece.
     * When the piece is selected then cleared the xeque visual mark disappears
     * ant the needs to be painted again
     */
    private fun paintSpecialXeque(locked: Piece) {
        if (locked is King && gameBoard.gameState == GameState.Xeque) {
            drawController.cleanCheck()
            _paint.value =
                drawController.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
        }
    }

    /**
     * Publishes the new board
     */
    private fun updateOnlineBoard() {
        getApplication<PuzzleOfDayApplication>().gamesRepository.updateGameState(
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
                    drawController.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
            }
            GameState.XequeMate -> {
                _paint.value = drawController.drawHighlight(null)
                checkMate(gameBoard.playerTeam)
            }
            else -> {}
        }
    }

    /**
     * Function that promotes a piece and publishes the result
     */
    fun promote(piece: String) {
        val resources = getApplication<PuzzleOfDayApplication>().resources
        gameBoard = gameBoard.promotionByResources(piece, resources)
        _game.value = Result.success(gameBoard)
        applyNewState()
        updateOnlineBoard()
    }

    /**
     * Sets the game state as Forfeit and warns the other player.
     * The forfeit is only possible during the player turn
     */
    fun forfeit() {
        if (gameBoard.isPlaying()) {
            gameBoard = gameBoard.forfeit()

            _paint.value = drawController.drawHighlight(null)
            _paint.value = drawController.drawSelectedPiece(null)
            _paint.value = drawController.drawXeque(null)

            getApplication<PuzzleOfDayApplication>().gamesRepository.updateGameState(
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
        getApplication<PuzzleOfDayApplication>().gamesRepository.updateGameState(
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
            getApplication<PuzzleOfDayApplication>().gamesRepository.updateGameState(
                gameState = gameBoard.toGameState(initialGameState.id, "", GameState.Draw),
                onComplete = { result ->
                    result.onFailure { _game.value = Result.failure(it) }
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
        getApplication<PuzzleOfDayApplication>().gamesRepository.deleteGame(
            challengeId = initialGameState.id,
            onComplete = { }
        )
        gameSubscription.remove()
    }
}