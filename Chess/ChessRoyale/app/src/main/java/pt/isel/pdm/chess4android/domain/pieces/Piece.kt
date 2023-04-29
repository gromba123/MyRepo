package pt.isel.pdm.chess4android.domain.pieces

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Represents the base class for every type of piece in the board.
 * The [Parcelize] annotation obligates the parameters to be defined as open
 */
@Parcelize
open class Piece(
    open var location: Location,
    open val team: Team,
    val id: Char
) : Parcelable {

    @IgnoredOnParcel
    protected open val moves = listOf<Move>()

    /**
     * Gets a list of positions that a piece can reach
     * @param board - The board from where the positions will be calculated
     */
    open fun checkPosition(board: List<List<Piece>>): List<Location> {
        val positions = mutableListOf<Location>()
        moves.forEach {
            var actualLocation = location
            while (true) {
                val newLocation = actualLocation.computeLocation(it.x, it.y)
                if (!newLocation.checkLimits()) break
                val piece = board[newLocation.y][newLocation.x]
                if (piece.team == Team.SPACE) {
                    positions.add(newLocation)
                    actualLocation = newLocation
                } else {
                    if (piece.team != team) positions.add(newLocation)
                    break
                }
            }
        }
        return positions
    }

    /**
     * Gets a list of all moves a piece can do without put the king in xeque
     * @param king - the king of the the team of the piece to move
     * @param board - The board from where the positions will be calculated
     */
    fun getMoves(
        board: MutableList<MutableList<Piece>>,
        king: King
    ): MutableList<Location> {
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
    fun isMate(
        board: MutableList<MutableList<Piece>>,
        king: King
    ): Boolean {
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
        king: King,
        location: Location
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

