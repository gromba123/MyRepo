package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.navigation.PAWN

/**
 * The constructor parameter playingForward indicates the pawn
 * if he should move up or down. It's because of the board rotation where
 * in a normal play the black pawns only move south and where the board rotates
 * they need to move north
 */
@Parcelize
class Pawn(
    override val team: Team,
    override var location: Location,
    private var playingDirection: Boolean = false,
    private var totalMoves: Int = 0
) : Piece(location, team, PAWN) {

    enum class Moves(val x: Int, val y: Int) {
        MOVE2(0, -1),
        MOVE3(1, -1),
        MOVE4(-1, -1),
        MOVE1(0, -2)
    }

    override fun checkPosition(board: List<List<Piece>>): List<Location> {
        val positions = mutableListOf<Location>()
        val moves = Moves.values()
        val defineDirection = if (!playingDirection) team.x else { if (team == Team.WHITE) -1 else 1 }
        val initialY = if (team == Team.WHITE || playingDirection) 6 else 1
        var maxIterations = if (location.y == initialY) moves.size else moves.size - 1
        var index = 0

        while (index < maxIterations) {
            val move = moves[index]
            val newPosition = location.computeLocation(move.x, move.y * defineDirection)
            if (newPosition.checkLimits()) {
                val piece = board[newPosition.y][newPosition.x]
                if (move == Moves.MOVE3 || move == Moves.MOVE4) {
                    if (piece.team != Team.SPACE && piece.team != team) positions.add(newPosition)
                } else {
                    if (piece.team == Team.SPACE) positions.add(newPosition)
                    else if (maxIterations == moves.size)
                        maxIterations--
                }
            }
            index++
        }
        checkPassant(Moves.MOVE3, board).let {
            if (it != null) {
                positions.add(it)
            }
        }

        checkPassant(Moves.MOVE4, board).let {
            if (it != null) {
                positions.add(it)
            }
        }
        return positions
    }

    private fun checkPassant(move: Moves, board: List<List<Piece>>): Location? {
        val defineDirection = if (!playingDirection) team.x else 1
        val newPosition = location.computeLocation(move.x, move.y * defineDirection)
        if (newPosition.checkLimits()) {
            val sidePiece = board[location.y][location.x + move.x]
            val diagonalPiece = board[newPosition.y][newPosition.x]
            if (sidePiece is Pawn && sidePiece.team != team && diagonalPiece is Space) {
                if ((sidePiece.location.y == 3 || sidePiece.location.y == 4)
                    && sidePiece.totalMoves == 1) {
                    return newPosition
                }
            }
        }
        return null
    }

    fun isPromoting() = location.y == 0 || location.y == 7

    fun incMoves() { totalMoves++ }

    fun setDirectionToPlay() { playingDirection = true }
}