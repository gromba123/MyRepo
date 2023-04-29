package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Rook(
    override val team: Team,
    override var location: Location
) : Piece(location, team, ROOK) {

    @IgnoredOnParcel
    override val moves: List<Move> = listOf(
        Move.NORTH,
        Move.EAST,
        Move.SOUTH,
        Move.WEST
    )
}