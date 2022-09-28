package pt.isel.pdm.chess4android.offline.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.offline.pieces.*
import pt.isel.pdm.chess4android.online.games.GameState
import pt.isel.pdm.chess4android.utils.*

/**
 * Board for the offline game.
 * The [SpecialMoveResult] contains information about the promotion
 */
@Parcelize
data class OfflineBoard(
    val board: MutableList<MutableList<Piece>> = buildBoard(),
    val playingTeam: Team = Team.WHITE,
    val gameState: GameState = GameState.Free,
    private val whites: HashMap<Char, MutableList<Piece>> = buildWhiteHash(board),
    private val blacks: HashMap<Char, MutableList<Piece>> = buildBlackHash(board),
    val specialMoveResult: SpecialMoveResult? = null
) : Parcelable {

    /**
     * Moves a piece on the old to the new location.
     * Also verifies the new game state and the special moves
     */
    fun movePiece(old: Location, new: Location): OfflineBoard {
        val piece = board[old.y][old.x]
        piece.location = new
        if (piece is Pawn) piece.incMoves()
        board[old.y][old.x] = Space(old)
        val deletedPiece = board[new.y][new.x]
        removePiece(deletedPiece, playingTeam.other)
        board[new.y][new.x] = piece
        val result = verifySpecialMoves(piece, old)
        val newPlayingTeam = playingTeam.other
        return copy(
            playingTeam = newPlayingTeam,
            gameState = xeque(newPlayingTeam),
            specialMoveResult = result
        )
    }

    /**
     * Verifies if there is castling or a promotion
     */
    private fun verifySpecialMoves(
        piece: Piece,
        old: Location
    ): SpecialMoveResult? {
        if (piece is King) {
            if (piece.isCastling(old, piece.location)) {
                val rookLocation =
                    if (piece.location.x == 1) Location(piece.location.x - 1, piece.location.y)
                    else Location(piece.location.x + 1, piece.location.y)
                val newX = piece.location.x + (piece.location.x - rookLocation.x)
                val newLocation = Location(newX, rookLocation.y)
                val rook = board[rookLocation.y][rookLocation.x]
                rook.location = newLocation
                board[newLocation.y][newLocation.x] = rook
                board[rookLocation.y][rookLocation.x] = Space(rookLocation)
            }
        } else if (piece is Pawn) {
            if (piece.isPromoting()) return SpecialMoveResult(piece)
        }
        return null
    }

    /**
     * Removes a piece from the team hash
     */
    private fun removePiece(piece: Piece, team: Team) {
        if (piece !is Space) {
            if (team == Team.WHITE) whites[piece.id]?.remove(piece)
            else blacks[piece.id]?.remove(piece)
        }
    }

    /**
     * Verifies if the king of the given team is in xeque
     */
    private fun xeque(team: Team): GameState {
        val hash = if (team == Team.WHITE) whites else blacks
        val king: King = getKing(team)
        val pieces = king.xeque(board)
        if (pieces) return mate(hash, king)
        return GameState.Free
    }

    /**
     * Verifies if the given king is in xeque-mate
     */
    private fun mate(
        pieces: HashMap<Char, MutableList<Piece>>,
        king: King
    ): GameState {
        pieces.values.forEach { lists ->
            lists.forEach { piece ->
                if (!piece.isMate(board, king)) return GameState.Xeque
            }
        }
        return GameState.XequeMate
    }

    /**
     * Function that initiates a promotion a piece
     */
    fun promotion(id: Char): OfflineBoard {
        if (specialMoveResult != null) {
            val oldPiece = specialMoveResult.piece
            removePiece(oldPiece, playingTeam.other)
            val newPiece = pickPromotedById(id, specialMoveResult)
            return promote(oldPiece, newPiece)
        }
        return this
    }

    /**
     * Substitutes the old pawn for the new piece
     */
    private fun promote(oldPiece: Piece, newPiece: Piece): OfflineBoard {
        board[oldPiece.location.y][oldPiece.location.x] = newPiece
        if (oldPiece.team == Team.WHITE) whites[newPiece.id]?.add(newPiece)
        else blacks[newPiece.id]?.add(newPiece)
        return copy(
            gameState = xeque(playingTeam),
            specialMoveResult = null
        )
    }

    /**
     * Updates the board with the Forfeit state
     */
    fun forfeit() = copy(gameState = GameState.Forfeit)

    /**
     * Gets the king of the given team
     */
    fun getKing(team: Team) = (if (team == Team.WHITE) whites['K'] else blacks['K'])?.get(0) as King

    /**
     * Gets the pieces of the winning team
     */
    fun getWinningPieces() = if (playingTeam.other == Team.WHITE) whites.values else blacks.values
}