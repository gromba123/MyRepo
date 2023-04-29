package pt.isel.pdm.chess4android.domain.online

import android.content.res.Resources
import pt.isel.pdm.chess4android.domain.pieces.King
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.pieces.Space
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.domain.pieces.pawn.BasePawn
import pt.isel.pdm.chess4android.utils.SpecialMoveResult
import pt.isel.pdm.chess4android.utils.buildBoard
import pt.isel.pdm.chess4android.utils.buildOpponentHash
import pt.isel.pdm.chess4android.utils.buildPlayerHash
import pt.isel.pdm.chess4android.utils.composeAN
import pt.isel.pdm.chess4android.utils.convertToLocation
import pt.isel.pdm.chess4android.utils.invertLocation
import pt.isel.pdm.chess4android.utils.pickPromoted
import pt.isel.pdm.chess4android.utils.pickPromotedById

/**
 * Represents the online game board
 */
data class OnlineBoard (
    val playingTeam: Team = Team.WHITE,
    val gameState: GameState = GameState.Free,
    val playerTeam: Team,
    val board: MutableList<MutableList<Piece>> = buildBoard(playerTeam),
    val lastMove: String = "",
    private val whites: HashMap<Char, MutableList<Piece>> = buildPlayerHash(board),
    private val blacks: HashMap<Char, MutableList<Piece>> = buildOpponentHash(board),
    val specialMoveResult: SpecialMoveResult? = null
) {

    /**
     * Moves a piece
     */
    fun movePiece(old: Location, new: Location): OnlineBoard {
        val piece = board[old.y][old.x]
        piece.location = new
        if (piece is BasePawn) piece.incMoves()
        board[old.y][old.x] = Space(old)
        val oldPiece = board[new.y][new.x]
        removePiece(oldPiece, playingTeam.other)
        board[new.y][new.x] = piece
        val result = verifySpecialMoves(piece, old)
        val newPlayingTeam = playingTeam.other
        return copy(
            lastMove = composeAN(invertLocation(old), invertLocation(new)),
            playingTeam = newPlayingTeam,
            gameState = xeque(newPlayingTeam),
            specialMoveResult = result
        )
    }

    /**
     * Moves de opponents piece
     */
    fun moveOpponentPiece(move: String) =
        if (move.isNotEmpty()) {
            val l1 = convertToLocation(move.substring(0, 2))
            val l2 = convertToLocation(move.substring(2, 4))
            val b = movePiece(l1, l2)
            if (b.specialMoveResult != null) b.promoteById(move[move.length - 1])
            else b
        }
        else this

    /**
     * Verifies if there is castling or a promotion
     */
    private fun verifySpecialMoves(piece: Piece, old: Location): SpecialMoveResult? {
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
        }
        else if (piece is BasePawn) {
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
    private fun xeque(actualTeam: Team): GameState {
        val hash = if (actualTeam == Team.WHITE) whites else blacks
        val king: King = getKing(actualTeam)
        if (king.xeque(board)) return mate(hash, king)
        return GameState.Free
    }

    /**
     * Verifies if the given king is in xeque-mate
     */
    private fun mate(pieces: HashMap<Char, MutableList<Piece>>, king: King): GameState {
        pieces.values.forEach { lists ->
            lists.forEach { piece ->
                if (!piece.isMate(board, king)) return GameState.Xeque
            }
        }
        return GameState.XequeMate
    }

    /**
     * Used to promote a pawn that belongs to the other player
     */
    private fun promoteById(id: Char): OnlineBoard {
        if (specialMoveResult != null) {
            val oldPiece = specialMoveResult.piece
            removePiece(oldPiece, playingTeam.other)
            val newPiece = pickPromotedById(id, specialMoveResult)
            return promote(oldPiece, newPiece)
        }
        return this
    }

    /**
     * Used to promote a pawn that belongs to the actual player.
     * Receives access to the resources because the piece to promoted is chose
     * based on a AlertDialog Box that uses elements from the resources to identify
     * each available type of piece to promotion
     */
    fun promotionByResources(
        string: String,
        resources: Resources
    ): OnlineBoard {
        if (specialMoveResult != null) {
            val oldPiece = specialMoveResult.piece
            removePiece(oldPiece, playingTeam.other)
            val newPiece = pickPromoted(string, resources, specialMoveResult)
            return promote(oldPiece, newPiece)
        }
        return this
    }

    /**
     * Promotes a piece
     */
    private fun promote(oldPiece: Piece, newPiece: Piece): OnlineBoard {
        board[oldPiece.location.y][oldPiece.location.x] = newPiece
        if (oldPiece.team == Team.WHITE) whites[newPiece.id]?.add(newPiece)
        else blacks[newPiece.id]?.add(newPiece)
        return copy(
            lastMove = lastMove + "=${newPiece.id}",
            gameState = xeque(playingTeam),
            specialMoveResult = null
        )
    }

    /**
     * Updates the board with the Forfeit state
     */
    fun forfeit() = copy(gameState = GameState.Forfeit)

    /**
     * Updates the board with the team actually playing
     */
    fun swapPlayingTeam() = copy(playingTeam = playingTeam.other)

    /**
     * Verifies if the player can play
     */
    fun isPlaying() =
        playerTeam == playingTeam && gameState != GameState.XequeMate
                && !(gameState == GameState.Forfeit || gameState == GameState.Draw)

    /**
     * Gets the king of the given team
     */
    fun getKing(team: Team) = (if (team == Team.WHITE) whites['K'] else blacks['K'])?.get(0) as King

    /**
     * Gets the pieces of the winning team
     */
    fun getWinningPieces() = if (playingTeam.other == Team.WHITE) whites.values else blacks.values
}