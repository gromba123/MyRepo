package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.navigation.KNIGHT

@Parcelize
class Knight(
    override val team: Team,
    override var location: Location
) : Piece(location, team, KNIGHT) {

    enum class Moves (val x: Int, val y: Int) {
        MOVE1(-2, -1),
        MOVE2(-2, 1),
        MOVE3(2, -1),
        MOVE4(2, 1),
        MOVE5(-1, -2),
        MOVE6(-1, 2),
        MOVE7(1, -2),
        MOVE8(1, 2);
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
        return positions
    }
}