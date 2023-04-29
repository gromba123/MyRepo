package pt.isel.pdm.chess4android.domain.puzzle

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.domain.pieces.King
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.pieces.Space
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.utils.buildBoard

@Parcelize
data class PuzzleBoard(
    val board: MutableList<MutableList<Piece>> = buildBoard(Team.WHITE),
    private val whites: HashMap<Char, MutableList<Piece>> = HashMap(),
    private val blacks: HashMap<Char, MutableList<Piece>> = HashMap(),
    val playingTeam: Team = Team.WHITE
) : Parcelable {

    /**
     * Moves a piece of the board
     */
    fun movePiece(old: Location, new: Location): PuzzleBoard {
        val piece = board[old.y][old.x]
        piece.location = new
        board[old.y][old.x] = Space(old)
        val remove: Piece = board[new.y][new.x]
        if (remove !is Space) {
            (if (playingTeam.other == Team.WHITE) whites[remove.id] else blacks[remove.id])?.remove(remove)
        }
        board[new.y][new.x] = piece
        return copy(playingTeam = playingTeam.other)
    }

    /**
     * Obtains the king of the given team
     */
    fun getKing(team: Team) = (if (team == Team.WHITE) whites['K']else blacks['K'])?.get(0) as King
}