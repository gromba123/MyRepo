package pt.isel.pdm.chess4android.ui.screens.online.games

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.isel.pdm.chess4android.dataAccess.GamesRepository
import pt.isel.pdm.chess4android.domain.DrawController
import pt.isel.pdm.chess4android.domain.online.GameState
import pt.isel.pdm.chess4android.domain.online.OnlineBoard
import pt.isel.pdm.chess4android.domain.online.OnlineGameState
import pt.isel.pdm.chess4android.domain.online.toGameState
import pt.isel.pdm.chess4android.domain.pieces.King
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.utils.EndgameResult
import pt.isel.pdm.chess4android.utils.PaintResults
import javax.inject.Inject

/**
 * ViewModel for the [OnlineActivity]
 */
@HiltViewModel
class OnlineScreenViewModel @Inject constructor(
    private val gamesRepository: GamesRepository
) : ViewModel() {

    private val acquiredMoves: HashMap<Piece, List<Location>> = HashMap()
    private val drawController = DrawController()
    private lateinit var gameSubscription : ListenerRegistration
    private lateinit var gameBoard: OnlineBoard
    private lateinit var gameId: String
    private var selectedPiece: Piece? = null

    private val _board: MutableState<Result<OnlineBoard>> = mutableStateOf(Result.success(OnlineBoard(playerTeam = Team.SPACE)))
    val board: State<Result<OnlineBoard>> = _board

    private val _gameState: MutableState<GameState> = mutableStateOf(GameState.Free)
    val gameState = _gameState

    private val _paintResults: MutableState<PaintResults> = mutableStateOf(PaintResults())
    val paintResults: State<PaintResults> = _paintResults

    private val _promotion: MutableState<Team?> = mutableStateOf(null)
    val promotion: State<Team?> = _promotion

    fun initGame(gameId: String, localPlayer: Team) {
        this.gameId = gameId
        gameSubscription = gamesRepository
            .subscribeToGameStateChanges (
                challengeId = gameId,
                onSubscriptionError = { _board.value = Result.failure(it) },
                onGameStateChange = { onStateUpdate(it) }
            )
        gameBoard = OnlineBoard(playerTeam = localPlayer)
        _board.value = Result.success(gameBoard)
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
            when(state.state) {
                GameState.Draw -> {
                    gameBoard = gameBoard.swapPlayingTeam()
                    _gameState.value = GameState.Draw
                }
                GameState.AcceptedDraw -> {
                    _gameState.value = GameState.AcceptedDraw
                }
                GameState.Forfeit -> {
                    gameBoard = gameBoard.forfeit()
                    checkMate(gameBoard.playerTeam)
                }
                else -> {
                    //Clean the previous xeque
                    if (gameBoard.gameState == GameState.Xeque) {
                        _paintResults.value = drawController.drawXeque(null)
                    }
                    gameBoard = gameBoard.moveOpponentPiece(state.moves)
                    _board.value = Result.success(gameBoard)
                    if (gameBoard.gameState == GameState.Xeque) {
                        _paintResults.value = drawController.drawXeque(
                            gameBoard.getKing(gameBoard.playingTeam).location
                        )
                    } else if (gameBoard.gameState == GameState.XequeMate) {
                        checkMate(gameBoard.playerTeam.other)
                    }
                }
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
                drawController.cleanSelectedPiece()

                if (!(locked.location.x == x && locked.location.y == y)) {
                    val location = Location(x, y)
                    if (acquiredMoves[locked]?.any { it.x == location.x && it.y == location.y } == true) {
                        if (gameBoard.gameState == GameState.Xeque) {
                            drawController.cleanCheck()
                        }
                        gameBoard = gameBoard.movePiece(locked.location, location)
                        _board.value = Result.success(gameBoard)
                        acquiredMoves.clear()
                        if (gameBoard.specialMoveResult == null) {
                            applyNewState()
                            updateOnlineBoard()
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
    }

    /**
     * Paints the special xeque occasions. It happens when the player
     * selects the king when it's in xeque and swap for another piece.
     * When the piece is selected then cleared the xeque visual mark disappears
     * ant the needs to be painted again
     */
    private fun paintSpecialXeque(locked: Piece) {
        //TODO(""Verify use of this fun)
        if (locked is King && gameBoard.gameState == GameState.Xeque) {
            drawController.cleanCheck()
            _paintResults.value =
                drawController.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
        }
    }

    /**
     * Publishes the new board
     */
    private fun updateOnlineBoard() {
        gamesRepository.updateGameState(
            gameState = gameBoard.toGameState(gameId),
            onComplete = { result ->
                result.onFailure { _board.value = Result.failure(it) }
            }
        )
    }

    /**
     * Applies the new game state
     */
    private fun applyNewState() {
        when (gameBoard.gameState) {
            GameState.Xeque -> {
                _paintResults.value =
                    drawController.drawXeque(gameBoard.getKing(gameBoard.playingTeam).location)
            }
            GameState.XequeMate -> {
                checkMate(gameBoard.playerTeam)
            }
            else -> {
                _paintResults.value = drawController.getActualResults()
            }
        }
    }

    /**
     * Function that promotes a piece and publishes the result
     */
    fun promote(id: Char) {
        gameBoard = gameBoard.promotion(id)
        _board.value = Result.success(gameBoard)
        _promotion.value = null
        applyNewState()
    }

    /**
     * Sets the game state as Forfeit and warns the other player.
     * The forfeit is only possible during the player turn
     */
    fun forfeit() {
        if (gameBoard.isPlaying()) {
            gameBoard = gameBoard.forfeit()
            gamesRepository.updateGameState(
                gameState = gameBoard.toGameState(gameId, ""),
                onComplete = { result ->
                    result.onFailure { _board.value = Result.failure(it) }
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
        val move = if (accepted) GameState.AcceptedDraw else GameState.Free
        gamesRepository.updateGameState(
            gameState = gameBoard.toGameState(gameId, "", move),
            onComplete = { result ->
                result.onFailure { _board.value = Result.failure(it) }
            }
        )
        _gameState.value = GameState.AcceptedDraw
        //TODO("On draw accept change")
    }

    /**
     * Purpose a draw to the other player
     */
    fun purposeDraw() {
        if (gameBoard.isPlaying()) {
            gameBoard = gameBoard.swapPlayingTeam()
            gamesRepository.updateGameState(
                gameState = gameBoard.toGameState(gameId, "", GameState.Draw),
                onComplete = { result ->
                    result.onFailure { /*TODO*/ }
                }
            )
        }
        //TODO("On draw purpose wait")
    }

    /**
     * Updates the screen based on the xeque-mate information
     * @param team - Player that won the game
     */
    private fun checkMate(
        team: Team
    ) {
        _paintResults.value = PaintResults(
            selectedPiece = null,
            highlightPieces = null,
            xequePiece = null,
            endgameResult = EndgameResult(
                gameBoard.getWinningPieces().toList(),
                team
            )
        )
        _gameState.value = GameState.XequeMate
    }

    /**
     * This method will remove the game from the games collection before the ViewModel
     * is destroyed.
     * It's an idempotent action.
     */
    override fun onCleared() {
        super.onCleared()
        gamesRepository.deleteGame(
            challengeId = gameId,
            onComplete = { }
        )
        gameSubscription.remove()
    }
}