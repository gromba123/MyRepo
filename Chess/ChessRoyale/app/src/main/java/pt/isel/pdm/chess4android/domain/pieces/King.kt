package pt.isel.pdm.chess4android.domain.pieces

import kotlin.math.abs

class King(
    override val team: Team,
    override var location: Location
) : Piece(location, team, KING) {

    override val moves: List<Move> = listOf(
        Move.NORTH,
        Move.NORTH_EAST,
        Move.EAST,
        Move.SOUTH_EAST,
        Move.SOUTH,
        Move.SOUTH_WEST,
        Move.WEST,
        Move.NORTH_WEST
    )

    private val castlingMoves: List<Move> = listOf(
        Move.EAST,
        Move.WEST
    )

    override fun checkPosition(board: List<List<Piece>>): List<Location> {
        val positions = mutableListOf<Location>()
        moves.forEach {
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
            var index = 0
            val move = castlingMoves[index]
            var newLocation = location.computeLocation(move.x, move.y)
            while (index < castlingMoves.size) {
                if (newLocation.checkLimits()) {
                    val piece = board[newLocation.y][newLocation.x]
                    if (piece is Rook && piece.team == team) {
                        if (move == Move.WEST) positions.add(Location(1, location.y))
                        else positions.add(Location(6, location.y))
                    } else if (piece !is Space) {
                        index++
                        newLocation = super.location
                    }
                } else {
                    index++
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
        moves.forEach {
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

        KNIGHT_MOVES.forEach {
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