package pt.isel.pdm.chess4android.offline.pieces

import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.utils.KING
import kotlin.math.abs

@Parcelize
class King(
    override val team: Team,
    override var location: Location
) : Piece(location, team, KING) {

    enum class Moves(val x: Int, val y: Int) {
        MOVE1(-1, 0),
        MOVE2(-1, 1),
        MOVE3(0, 1),
        MOVE4(1, 1),
        MOVE5(1, 0),
        MOVE6(1, -1),
        MOVE7(0, -1),
        MOVE8(-1, -1)
    }

    override fun checkPosition(board: List<List<Piece>>): List<Location> {
        val positions = mutableListOf<Location>()
        Moves.values().forEach {
            val newLocation = location.computeLocation(it.x, it.y)
            if (newLocation.checkLimits()) {
                val piece = board[newLocation.y][newLocation.x]
                if (piece.team == Team.SPACE || piece.team != team) {
                    positions.add(newLocation)
                }
            }
        }
        val originalX = if (team == Team.BLACK) 3 else super.location.x
        //Verifies castling
        if (location.x == originalX && location.y == 7) {
            var move = Moves.MOVE1
            var newLocation = location.computeLocation(move.x, move.y)
            while (true) {
                if (newLocation.checkLimits()) {
                    val piece = board[newLocation.y][newLocation.x]
                    if (piece is Rook && piece.team == team) {
                        if (move == Moves.MOVE1) positions.add(Location(1, location.y))
                        else positions.add(Location(6, location.y))
                    } else if (piece !is Space) {
                        if (move == Moves.MOVE5) break
                        move = Moves.MOVE5
                        newLocation = super.location
                    }
                } else {
                    if (move == Moves.MOVE5) break
                    move = Moves.MOVE5
                    newLocation = super.location
                }
                newLocation = newLocation.computeLocation(move.x, move.y)
            }
        }
        return positions
    }

    fun xeque(board: MutableList<MutableList<Piece>>): Boolean {
        val pieces = mutableListOf<Piece>()
        val locationsAcquired = HashMap<Piece, List<Location>>()
        Moves.values().forEach {
            var actualLocation = location
            while (true) {
                val newLocation = actualLocation.computeLocation(it.x, it.y)
                if (!newLocation.checkLimits()) break
                val piece = board[newLocation.y][newLocation.x]
                if (piece.team != team && piece.team != Team.SPACE) {
                    val locations = if (locationsAcquired.containsKey(piece))
                        locationsAcquired[piece]
                    else {
                        val l = piece.checkPosition(board)
                        locationsAcquired[piece] = l
                        l
                    }
                    if (locations != null) {
                        if (locations.any { l -> l.x == location.x && l.y == location.y}) pieces.add(piece)
                    }
                    break
                }
                actualLocation = newLocation
            }
        }

        Knight.Moves.values().forEach {
            val newLocation = location.computeLocation(it.x, it.y)
            if (newLocation.checkLimits()) {
                val piece = board[newLocation.y][newLocation.x]
                if (piece is Knight && team != piece.team) {
                    if (!pieces.contains(piece))pieces.add(piece)
                }
            }
        }

        return pieces.isNotEmpty()
    }

    fun isCastling(old: Location, new: Location) =
        abs(old.x - new.x) > 1
}