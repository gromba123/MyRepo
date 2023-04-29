package pt.isel.pdm.chess4android.domain.pieces.pawn

import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Move
import pt.isel.pdm.chess4android.domain.pieces.Team

class DownPawn(
    override val team: Team,
    override var location: Location
) : BasePawn(team, location) {

    override val moves: List<Move> = listOf(
        Move.SOUTH,
        Move.SOUTH_WEST,
        Move.SOUTH_EAST,
        Move.DOUBLE_PAWN_DOWN
    )

    override val passantMoves: List<Move> = listOf(
        Move.SOUTH_WEST,
        Move.SOUTH_EAST
    )

    override val initialY: Int = 1
}