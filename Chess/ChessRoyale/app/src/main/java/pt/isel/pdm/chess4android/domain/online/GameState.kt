package pt.isel.pdm.chess4android.domain.online

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.domain.pieces.Team

/**
 * Defines all the possible game states
 */
enum class GameState {Free, Xeque, XequeMate, Forfeit, Draw, AcceptedDraw}

/**
 * The challenge information.
 *
 * @property [id]                   the challenge identifier
 * @property [challengerName]       the challenger name
 * @property [challengerMessage]    the challenger message
 */
@Parcelize
data class ChallengeInfo(
    val id: String,
    val challengerName: String,
    val challengerMessage: String
) : Parcelable

/**
 * Represents an online move
 */
@Parcelize
data class OnlineGameState(
    val id: String,
    val moves: String,
    val state: GameState,
    val player: Team
): Parcelable

/**
 * Converts the last board to a move
 */
fun OnlineBoard.toGameState(
    gameId: String,
    lastMove: String = this.lastMove,
    gameState: GameState = this.gameState
): OnlineGameState = OnlineGameState(gameId, lastMove, gameState, playerTeam)