package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

//TODO(Verify moves)
@Parcelize
class Knight(
    override val team: Team,
    override var location: Location
) : Piece(location, team, KNIGHT) {

    @IgnoredOnParcel
    override val moves: List<Move> = KNIGHT_MOVES

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
        return positions
    }
}