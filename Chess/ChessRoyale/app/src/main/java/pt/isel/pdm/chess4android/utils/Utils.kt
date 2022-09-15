package pt.isel.pdm.chess4android.utils

import android.app.Application
import android.content.res.Resources
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.offline.pieces.*
import pt.isel.pdm.chess4android.views.Type

open class BaseBoardViewModel(
    application: Application
) : AndroidViewModel(application)

/**
 * Contains the location of a promoted piece.
 * Due to possible concurrency and efficiency problems
 * this type needs to exit.
 */
@Parcelize
data class SpecialMoveResult(val piece: Piece) : Parcelable

/**
 * Contains the squares to paint and the color to paint
 */
data class DrawResults(val list: List<Location>, val type: Type)

/**
 * Represents the end of a game
 * @param pieces - pieces to highlight
 * @param team - winning team
 */
data class EndgameResult(val pieces: List<List<Piece>>, val team: Team)

/**
 * Converts an Algebraic Notation location into a board location
 */
fun convertToLocation(move: String): Location {
    val x: Int = move[0] - 'a'
    val y: Int = convertRank(move[1] - '0')
    return Location(x, y)
}

/**
 * Transforms a coordinate into a Algebraic Notation rank
 */
fun convertRank(int: Int): Int = (-1) * (int - 8)

/**
 * Transforms a coordinate into a Algebraic Notation file
 */
fun convertToFile(int: Int): Char = 'a' + int

/**
 * Converts a pair of locations into an Algebraic Notation move
 */
fun composeAN(old: Location, new: Location) : String {
    val str1 = old.toString()
    val str2 = new.toString()
    return str1 + str2
}

fun getPiece(piece: Piece) =
    when (piece) {
        is Pawn -> if(piece.team == Team.WHITE)R.drawable.ic_white_pawn else R.drawable.ic_black_pawn
        is Rook -> if(piece.team == Team.WHITE)R.drawable.ic_white_rook else R.drawable.ic_black_rook
        is Knight -> if(piece.team == Team.WHITE)R.drawable.ic_white_knight else R.drawable.ic_black_knight
        is Bishop -> if(piece.team == Team.WHITE)R.drawable.ic_white_bishop else R.drawable.ic_black_bishop
        is Queen -> if(piece.team == Team.WHITE)R.drawable.ic_white_queen else R.drawable.ic_black_queen
        is King -> if(piece.team == Team.WHITE)R.drawable.ic_white_king else R.drawable.ic_black_king
        else -> null
    }

/**
 * Builds a chess board with the pieces in the correct pieces
 */
fun buildBoard(): MutableList<MutableList<Piece>> = arrayListOf(
    arrayListOf(
        Rook(Team.BLACK, Location(0, 0)),
        Knight(Team.BLACK, Location(1, 0)),
        Bishop(Team.BLACK, Location(2, 0)),
        Queen(Team.BLACK, Location(3, 0)),
        King(Team.BLACK, Location(4, 0)),
        Bishop(Team.BLACK, Location(5, 0)),
        Knight(Team.BLACK, Location(6, 0)),
        Rook(Team.BLACK, Location(7, 0))
    ),
    arrayListOf(
        Pawn(Team.BLACK, Location(0, 1)),
        Pawn(Team.BLACK, Location(1, 1)),
        Pawn(Team.BLACK, Location(2, 1)),
        Pawn(Team.BLACK, Location(3, 1)),
        Pawn(Team.BLACK, Location(4, 1)),
        Pawn(Team.BLACK, Location(5, 1)),
        Pawn(Team.BLACK, Location(6, 1)),
        Pawn(Team.BLACK, Location(7, 1))
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Pawn(Team.WHITE, Location(0, 6)),
        Pawn(Team.WHITE, Location(1, 6)),
        Pawn(Team.WHITE, Location(2, 6)),
        Pawn(Team.WHITE, Location(3, 6)),
        Pawn(Team.WHITE, Location(4, 6)),
        Pawn(Team.WHITE, Location(5, 6)),
        Pawn(Team.WHITE, Location(6, 6)),
        Pawn(Team.WHITE, Location(7, 6))
    ),
    arrayListOf(
        Rook(Team.WHITE, Location(0, 7)),
        Knight(Team.WHITE, Location(1, 7)),
        Bishop(Team.WHITE, Location(2, 7)),
        Queen(Team.WHITE, Location(3, 7)),
        King(Team.WHITE, Location(4, 7)),
        Bishop(Team.WHITE, Location(5, 7)),
        Knight(Team.WHITE, Location(6, 7)),
        Rook(Team.WHITE, Location(7, 7))
    )
)

/**
 * Builds an empty chess board
 */
fun buildEmptyBoard(): MutableList<MutableList<Piece>> = arrayListOf(
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    ),
    arrayListOf(
        Space(), Space(), Space(), Space(),
        Space(), Space(), Space(), Space()
    )
)

/**
 * Builds an HashMap containing all the white pieces
 */
fun buildWhiteHash(board: MutableList<MutableList<Piece>>): HashMap<Char, MutableList<Piece>> {
    val hash = HashMap<Char, MutableList<Piece>>()
    val rook = mutableListOf(board[7][0], board[7][7])
    val bishop = mutableListOf(board[7][2], board[7][5])
    val knight = mutableListOf(board[7][1], board[7][6])
    val king = mutableListOf(board[7][4])
    val queen = mutableListOf(board[7][3])
    val pawn = mutableListOf(board[6][0], board[6][1], board[6][2], board[6][3],
                                board[6][4], board[6][5], board[6][6], board[6][7])
    hash['R'] = rook
    hash['B'] = bishop
    hash['N'] = knight
    hash['P'] = pawn
    hash['Q'] = queen
    hash['K'] = king
    return hash
}

/**
 * Builds an HashMap containing all the black pieces
 */
fun buildBlackHash(board: MutableList<MutableList<Piece>>): HashMap<Char, MutableList<Piece>> {
    val hash = HashMap<Char, MutableList<Piece>>()
    val rook = mutableListOf(board[0][0], board[0][7])
    val bishop = mutableListOf(board[0][2], board[0][5])
    val knight = mutableListOf(board[0][1], board[0][6])
    val king = mutableListOf(board[0][4])
    val queen = mutableListOf(board[0][3])
    val pawn = mutableListOf(board[1][0], board[1][1], board[1][2], board[1][3],
        board[1][4], board[1][5], board[1][6], board[1][7])
    hash['R'] = rook
    hash['B'] = bishop
    hash['N'] = knight
    hash['P'] = pawn
    hash['Q'] = queen
    hash['K'] = king
    return hash
}

/**
 * Receives a board an inverts their positions so the black pieces stays in the bottom of
 * the board
 * @param board - board to invert
 */
fun invertBoard(board: MutableList<MutableList<Piece>>): MutableList<MutableList<Piece>> {
    val newBoard = buildEmptyBoard()
    board.forEach { row ->
        row.forEach {
            if (it !is Space) {
                val newLocation = invertLocation(it.location)
                it.location = newLocation
                newBoard[newLocation.y][newLocation.x] = it
            } else {
                newBoard[it.location.y][it.location.x] = it
            }

        }
    }
    return newBoard
}

/**
 * Inverts a location
 */
fun invertLocation(l: Location): Location = Location((-1) * (l.x - 7), (-1) * (l.y - 7))

/**
 * Inverts all the moves in a solution
 */
fun invertPuzzleSolution(solution: List<String>): List<String> {
    val newSln = mutableListOf<String>()
    solution.forEach {
        val old = invertLocation(convertToLocation(it.substring(0, 2)))
        val new = invertLocation(convertToLocation(it.substring(2, 4)))
        newSln.add(composeAN(old, new))
    }
    return newSln
}

/**
 * Picks the correct type of piece to promote based on a resource
 */
fun pickPromoted(
    string: String,
    resources: Resources,
    specialMoveResult: SpecialMoveResult?
): Piece {
    val piece = specialMoveResult?.piece
    if (piece != null) {
        return when(string) {
            resources.getString(R.string.queen) -> Queen(piece.team, piece.location)
            resources.getString(R.string.rook) -> Rook(piece.team, piece.location)
            resources.getString(R.string.knight) -> Knight(piece.team, piece.location)
            else -> Bishop(piece.team, piece.location)
        }
    }
    return Space()
}

/**
 * Picks the correct type of piece to promote based on a resource
 */
fun pickPromotedById(id: Char, specialMoveResult: SpecialMoveResult?): Piece {
    val piece = specialMoveResult?.piece
    if (piece != null) {
        return when(id) {
            'Q' -> Queen(piece.team, piece.location)
            'R' -> Rook(piece.team, piece.location)
            'N' -> Knight(piece.team, piece.location)
            else -> Bishop(piece.team, piece.location)
        }
    }
    return Space()
}

/**
 * Rotates a board if the team is black
 */
fun rotateBoard(playerTeam: Team) =
    if (playerTeam == Team.BLACK) invertBoard(buildBoard()) else buildBoard()