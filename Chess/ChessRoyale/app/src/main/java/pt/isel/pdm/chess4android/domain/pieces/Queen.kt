package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Queen(
    override val team: Team,
    override var location: Location
) : Piece(location, team, QUEEN) {

    @IgnoredOnParcel
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
}