package pt.isel.pdm.chess4android.domain.puzzle

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.utils.composeAN
import pt.isel.pdm.chess4android.utils.convertToLocation
import pt.isel.pdm.chess4android.utils.invertPuzzleSolution

/**
 * Represents the states of the puzzle
 */
enum class PuzzleState { OnProgress, Finished }

/**
 * Represents the states of a move
 */
enum class MoveState { Allowed, Blocked }

/**
 * Represents the result of a move
 */
data class RulerResult(
    val moveState: MoveState,
    val puzzleState: PuzzleState,
    val board: PuzzleBoard
    )

/**
 * The type that controls all the puzzle moves
 */
@Parcelize
class PuzzleRuler(
    private val pgn: MutableList<String>,
    private val sln: MutableList<String>,
    private val team: Team,
    val id: String
    ): Parcelable {

    @IgnoredOnParcel
    private val solution: MutableList<String> = if (team == Team.WHITE) sln.toMutableList()
                                else invertPuzzleSolution(sln).toMutableList()

    /**
     * Executes a move if it is a part of the solution
     */
    fun tryExecuteMove(
        oldLocation: Location,
        newLocation: Location,
        board: PuzzleBoard
    ): RulerResult {
        if (solution.first() != composeAN(oldLocation, newLocation))
            return RulerResult(MoveState.Blocked, PuzzleState.OnProgress, board)
        val newBoard = board.movePiece(oldLocation, newLocation)
        solution.removeFirst()
        pgn.add(sln.removeFirst())
        return RulerResult(MoveState.Allowed, PuzzleState.OnProgress, newBoard)
    }

    fun executeOpponentMove(
        board: PuzzleBoard
    ): RulerResult {
        val newBoard = if (solution.size != 0) {
            val nb = board.movePiece(
                convertToLocation(solution.first().substring(0, 2)),
                convertToLocation(solution.first().substring(2, 4))
            )
            solution.removeFirst()
            pgn.add(sln.removeFirst())
            nb
        } else board
        val puzzleState = if (sln.size != 0) PuzzleState.OnProgress else PuzzleState.Finished
        return RulerResult(MoveState.Allowed, puzzleState, newBoard)
    }

    fun getPgn() = pgn

    fun getSln() = sln
}