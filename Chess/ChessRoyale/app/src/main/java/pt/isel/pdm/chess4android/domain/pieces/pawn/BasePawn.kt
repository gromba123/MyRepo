package pt.isel.pdm.chess4android.domain.pieces.pawn

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Move
import pt.isel.pdm.chess4android.domain.pieces.PAWN
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.pieces.Space
import pt.isel.pdm.chess4android.domain.pieces.Team

@Parcelize
open class BasePawn (
    override val team: Team,
    override var location: Location,
    protected var totalMoves: Int = 0
) : Piece(location, team, PAWN) {

    @IgnoredOnParcel
    protected open val passantMoves = listOf<Move>()

    @IgnoredOnParcel
    protected open val initialY = 6

    override fun checkPosition(board: List<List<Piece>>): List<Location> {
        val positions = mutableListOf<Location>()
        var maxIterations = if (location.y == initialY) moves.size else moves.size - 1
        var index = 0
        while (index < maxIterations) {
            val move = moves[index]
            val newPosition = location.computeLocation(move.x, move.y)
            if (newPosition.checkLimits()) {
                val piece = board[newPosition.y][newPosition.x]
                //Positions 1 and 2 must be either North/South West and/or North/South East
                if (move == moves[1] || move == moves[2]) {
                    if (piece.team != Team.SPACE && piece.team != team) {
                        positions.add(newPosition)
                    }
                } else {
                    if (piece.team == Team.SPACE) {
                        positions.add(newPosition)
                    } else if (maxIterations == moves.size) {
                        maxIterations--
                    }
                }
            }
            index++
        }
        checkPassant(passantMoves[0], board).let {
            if (it != null) {
                positions.add(it)
            }
        }

        checkPassant(passantMoves[1], board).let {
            if (it != null) {
                positions.add(it)
            }
        }
        return positions
    }

    private fun checkPassant(move: Move, board: List<List<Piece>>): Location? {
        val newPosition = location.computeLocation(move.x, move.y)
        if (newPosition.checkLimits()) {
            val sidePiece = board[location.y][location.x + move.x]
            val diagonalPiece = board[newPosition.y][newPosition.x]
            if (sidePiece is BasePawn && sidePiece.team != team && diagonalPiece is Space) {
                if (
                    (sidePiece.location.y == 3 || sidePiece.location.y == 4)
                    && sidePiece.totalMoves == 1
                ) {
                    return newPosition
                }
            }
        }
        return null
    }

    fun isPromoting() = location.y == 0 || location.y == 7

    fun incMoves() { totalMoves++ }
}