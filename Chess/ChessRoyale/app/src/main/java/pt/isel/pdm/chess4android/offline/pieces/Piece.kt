package pt.isel.pdm.chess4android.offline.pieces

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.utils.convertRank
import pt.isel.pdm.chess4android.utils.convertToFile

/**
 * Represents a location in the board
 */
@Parcelize
data class Location(val x: Int, val y: Int) : Parcelable {

    /**
     * Computes a new location based on the previous
     */
    fun computeLocation(x: Int, y: Int) = Location(this.x + x, this.y + y)

    /**
     * Checks if the location is inside the board
     */
    fun checkLimits() : Boolean = x < 8 && y < 8 && x >= 0 && y >= 0

    /**
     * Converts the location to an Algebraic Notation position
     */
    override fun toString(): String = "${convertToFile(x)}${convertRank(y)}"
}

/**
 * Represents the White and Black teams.
 * Space is also considered a team to simplify the logic to move or analyze
 * the possibles moves of a piece
 */
@Parcelize
enum class Team(val x: Int) : Parcelable {
    WHITE(1),
    BLACK(-1),
    SPACE(0);

    companion object {
        val firstToMove: Team = WHITE
    }

    val other: Team
        get() = if (this == WHITE) BLACK else WHITE
}

/**
 * Represents the base class for every type of piece in the board.
 * The [Parcelize] annotation obligates the parameters to be defined as open
 */
@Parcelize
open class Piece(open var location: Location, open val team: Team, val id: Char) : Parcelable {

    /**
     * Gets a list of positions that a piece can reach
     * @param board - The board from where the positions will be calculated
     */
    open fun checkPosition(board: List<List<Piece>>): List<Location> {
        TODO("Nothing")
    }

    /**
     * Gets a list of all moves a piece can do without put the king in xeque
     * @param king - the king of the the team of the piece to move
     * @param board - The board from where the positions will be calculated
     */
    fun getMoves(board: MutableList<MutableList<Piece>>, king: King): MutableList<Location> {
        val list = checkPosition(board)
        val pos = mutableListOf<Location>()
        list.forEach {
            val bool = this.simulateMove(board, king, it)
            if (bool) pos.add(it)
        }
        return pos
    }

    /**
     * Simulates all the moves and verifies with the king is in xeque
     * @param king - the king of the the team of the piece to move
     * @param board - The board from where the positions will be calculated
     */
    fun isMate(board: MutableList<MutableList<Piece>>, king: King): Boolean {
        val list = checkPosition(board)
        list.forEach {
            val bool = this.simulateMove(board, king, it)
            if (bool) return false
        }
        return true
    }

    /**
     * Simulates if a moves is possible with put the king in xeque
     * @param board - The board from where the positions will be calculated
     * @param king - The king of the the team of the piece to move
     * @param location - The location to simulate the move
     */
    private fun simulateMove(
        board: MutableList<MutableList<Piece>>,
        king: King, location: Location
    ): Boolean {
        val p = board[location.y][location.x]
        val oldLocation = this.location

        board[oldLocation.y][oldLocation.x] = Space(oldLocation)
        board[location.y][location.x] = this
        this.location = location

        val bool = king.xeque(board)

        board[oldLocation.y][oldLocation.x] = this
        this.location = oldLocation
        board[location.y][location.x] = p
        return !bool
    }
}

