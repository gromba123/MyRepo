package pt.isel.pdm.chess4android.domain.pieces.pawn

import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Move
import pt.isel.pdm.chess4android.domain.pieces.Team

class UpperPawn(
    override val team: Team,
    override var location: Location
) : BasePawn(team, location) {

    override val moves: List<Move> = listOf(
        Move.NORTH,
        Move.NORTH_WEST,
        Move.NORTH_EAST,
        Move.DOUBLE_PAWN_UP
    )

    override val passantMoves: List<Move> = listOf(
        Move.NORTH_WEST,
        Move.NORTH_EAST
    )
}