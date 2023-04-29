package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Bishop(
    override val team: Team,
    override var location: Location
) : Piece(location, team, BISHOP) {

    @IgnoredOnParcel
    override val moves: List<Move> = listOf(
        Move.NORTH_EAST,
        Move.SOUTH_EAST,
        Move.SOUTH_WEST,
        Move.NORTH_WEST
    )
}