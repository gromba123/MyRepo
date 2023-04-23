package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.Parcelize

@Parcelize
class Rook(
    override val team: Team,
    override var location: Location
) : Piece(location, team, ROOK) {

    enum class Moves(val x: Int, val y: Int) {
        MOVE1(-1, 0),
        MOVE2(1, 0),
        MOVE3(0, 1),
        MOVE4(0, -1)
    }

    override fun checkPosition(board: List<List<Piece>>): List<Location> {
        val positions = mutableListOf<Location>()
        Moves.values().forEach {
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
}