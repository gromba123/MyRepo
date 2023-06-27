package pt.isel.pdm.chess4android.utils

import android.content.res.Resources
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.pieces.BISHOP
import pt.isel.pdm.chess4android.domain.pieces.Bishop
import pt.isel.pdm.chess4android.domain.pieces.KNIGHT
import pt.isel.pdm.chess4android.domain.pieces.King
import pt.isel.pdm.chess4android.domain.pieces.Knight
import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.domain.pieces.Piece
import pt.isel.pdm.chess4android.domain.pieces.QUEEN
import pt.isel.pdm.chess4android.domain.pieces.Queen
import pt.isel.pdm.chess4android.domain.pieces.ROOK
import pt.isel.pdm.chess4android.domain.pieces.Rook
import pt.isel.pdm.chess4android.domain.pieces.Space
import pt.isel.pdm.chess4android.domain.pieces.Team
import pt.isel.pdm.chess4android.domain.pieces.pawn.DownPawn
import pt.isel.pdm.chess4android.domain.pieces.pawn.PuzzlePawn
import pt.isel.pdm.chess4android.domain.pieces.pawn.UpperPawn

/**
 * Contains the location of a promoted piece.
 * Due to possible concurrency and efficiency problems
 * this type needs to exit.
 */
@Parcelize
data class SpecialMoveResult(val piece: Piece) : Parcelable

/**
 * Represents the end of a game
 * @param pieces - pieces to highlight
 * @param team - winning team
 */
data class EndgameResult(val pieces: List<List<Piece>>, val team: Team)

data class PaintResults(
    val selectedPiece: Location? = null,
    val highlightPieces: List<Location>? = null,
    val xequePiece: Location? = null,
    val endgameResult: EndgameResult? = null
)

data class PromotionOption(
    @DrawableRes val idWhite: Int,
    @DrawableRes val idBlack: Int,
    @StringRes val name: Int,
    val piece: Char
)

val promotionOptions = listOf(
    PromotionOption(
        R.drawable.ic_white_queen,
        R.drawable.ic_black_queen,
        R.string.queen,
        QUEEN
    ),
    PromotionOption(
        R.drawable.ic_white_rook,
        R.drawable.ic_black_rook,
        R.string.rook,
        ROOK
    ),
    PromotionOption(
        R.drawable.ic_white_knight,
        R.drawable.ic_black_knight,
        R.string.knight,
        KNIGHT
    ),
    PromotionOption(
        R.drawable.ic_white_bishop,
        R.drawable.ic_black_bishop,
        R.string.bishop,
        BISHOP
    )
)

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

/**
 * Builds a chess board with the whites pieces below
 */
fun buildBoard(
    playerTeam: Team
): MutableList<MutableList<Piece>> {
    val opponentTeam = playerTeam.other
    return arrayListOf(
        arrayListOf(
            Rook(opponentTeam, Location(0, 0)),
            Knight(opponentTeam, Location(1, 0)),
            Bishop(opponentTeam, Location(2, 0)),
            Queen(opponentTeam, Location(3, 0)),
            King(opponentTeam, Location(4, 0)),
            Bishop(opponentTeam, Location(5, 0)),
            Knight(opponentTeam, Location(6, 0)),
            Rook(opponentTeam, Location(7, 0))
        ),
        arrayListOf(
            DownPawn(opponentTeam, Location(0, 1)),
            DownPawn(opponentTeam, Location(1, 1)),
            DownPawn(opponentTeam, Location(2, 1)),
            DownPawn(opponentTeam, Location(3, 1)),
            DownPawn(opponentTeam, Location(4, 1)),
            DownPawn(opponentTeam, Location(5, 1)),
            DownPawn(opponentTeam, Location(6, 1)),
            DownPawn(opponentTeam, Location(7, 1))
        ),
        arrayListOf(
            Space(Location(0, 2)),
            Space(Location(1, 2)),
            Space(Location(2, 2)),
            Space(Location(3, 2)),
            Space(Location(4, 2)),
            Space(Location(5, 2)),
            Space(Location(6, 2)),
            Space(Location(7, 2))
        ),
        arrayListOf(
            Space(Location(0, 3)),
            Space(Location(1, 3)),
            Space(Location(2, 3)),
            Space(Location(3, 3)),
            Space(Location(4, 3)),
            Space(Location(5, 3)),
            Space(Location(6, 3)),
            Space(Location(7, 3))
        ),
        arrayListOf(
            Space(Location(0, 4)),
            Space(Location(1, 4)),
            Space(Location(2, 4)),
            Space(Location(3, 4)),
            Space(Location(4, 4)),
            Space(Location(5, 4)),
            Space(Location(6, 4)),
            Space(Location(7, 4))
        ),
        arrayListOf(
            Space(Location(0, 5)),
            Space(Location(1, 5)),
            Space(Location(2, 5)),
            Space(Location(3, 5)),
            Space(Location(4, 5)),
            Space(Location(5, 5)),
            Space(Location(6, 5)),
            Space(Location(7, 5))
        ),
        arrayListOf(
            UpperPawn(playerTeam, Location(0, 6)),
            UpperPawn(playerTeam, Location(1, 6)),
            UpperPawn(playerTeam, Location(2, 6)),
            UpperPawn(playerTeam, Location(3, 6)),
            UpperPawn(playerTeam, Location(4, 6)),
            UpperPawn(playerTeam, Location(5, 6)),
            UpperPawn(playerTeam, Location(6, 6)),
            UpperPawn(playerTeam, Location(7, 6))
        ),
        arrayListOf(
            Rook(playerTeam, Location(0, 7)),
            Knight(playerTeam, Location(1, 7)),
            Bishop(playerTeam, Location(2, 7)),
            Queen(playerTeam, Location(3, 7)),
            King(playerTeam, Location(4, 7)),
            Bishop(playerTeam, Location(5, 7)),
            Knight(playerTeam, Location(6, 7)),
            Rook(playerTeam, Location(7, 7))
        )
    )
}

fun buildPuzzleBoard(
    playerTeam: Team
): MutableList<MutableList<Piece>> {
    val opponentTeam = playerTeam.other
    return arrayListOf(
        arrayListOf(
            Rook(opponentTeam, Location(0, 0)),
            Knight(opponentTeam, Location(1, 0)),
            Bishop(opponentTeam, Location(2, 0)),
            Queen(opponentTeam, Location(3, 0)),
            King(opponentTeam, Location(4, 0)),
            Bishop(opponentTeam, Location(5, 0)),
            Knight(opponentTeam, Location(6, 0)),
            Rook(opponentTeam, Location(7, 0))
        ),
        arrayListOf(
            PuzzlePawn(opponentTeam, Location(0, 1)),
            PuzzlePawn(opponentTeam, Location(1, 1)),
            PuzzlePawn(opponentTeam, Location(2, 1)),
            PuzzlePawn(opponentTeam, Location(3, 1)),
            PuzzlePawn(opponentTeam, Location(4, 1)),
            PuzzlePawn(opponentTeam, Location(5, 1)),
            PuzzlePawn(opponentTeam, Location(6, 1)),
            PuzzlePawn(opponentTeam, Location(7, 1))
        ),
        arrayListOf(
            Space(Location(0, 2)),
            Space(Location(1, 2)),
            Space(Location(2, 2)),
            Space(Location(3, 2)),
            Space(Location(4, 2)),
            Space(Location(5, 2)),
            Space(Location(6, 2)),
            Space(Location(7, 2))
        ),
        arrayListOf(
            Space(Location(0, 3)),
            Space(Location(1, 3)),
            Space(Location(2, 3)),
            Space(Location(3, 3)),
            Space(Location(4, 3)),
            Space(Location(5, 3)),
            Space(Location(6, 3)),
            Space(Location(7, 3))
        ),
        arrayListOf(
            Space(Location(0, 4)),
            Space(Location(1, 4)),
            Space(Location(2, 4)),
            Space(Location(3, 4)),
            Space(Location(4, 4)),
            Space(Location(5, 4)),
            Space(Location(6, 4)),
            Space(Location(7, 4))
        ),
        arrayListOf(
            Space(Location(0, 5)),
            Space(Location(1, 5)),
            Space(Location(2, 5)),
            Space(Location(3, 5)),
            Space(Location(4, 5)),
            Space(Location(5, 5)),
            Space(Location(6, 5)),
            Space(Location(7, 5))
        ),
        arrayListOf(
            PuzzlePawn(playerTeam, Location(0, 6)),
            PuzzlePawn(playerTeam, Location(1, 6)),
            PuzzlePawn(playerTeam, Location(2, 6)),
            PuzzlePawn(playerTeam, Location(3, 6)),
            PuzzlePawn(playerTeam, Location(4, 6)),
            PuzzlePawn(playerTeam, Location(5, 6)),
            PuzzlePawn(playerTeam, Location(6, 6)),
            PuzzlePawn(playerTeam, Location(7, 6))
        ),
        arrayListOf(
            Rook(playerTeam, Location(0, 7)),
            Knight(playerTeam, Location(1, 7)),
            Bishop(playerTeam, Location(2, 7)),
            Queen(playerTeam, Location(3, 7)),
            King(playerTeam, Location(4, 7)),
            Bishop(playerTeam, Location(5, 7)),
            Knight(playerTeam, Location(6, 7)),
            Rook(playerTeam, Location(7, 7))
        )
    )
}

/**
 * Builds an empty chess board
 */
fun buildEmptyBoard(): MutableList<MutableList<Piece>> = arrayListOf(
    arrayListOf(
        Space(Location(0, 0)),
        Space(Location(1, 0)),
        Space(Location(2, 0)),
        Space(Location(3, 0)),
        Space(Location(4, 0)),
        Space(Location(5, 0)),
        Space(Location(6, 0)),
        Space(Location(7, 0))
    ),
    arrayListOf(
        Space(Location(0, 1)),
        Space(Location(1, 1)),
        Space(Location(2, 1)),
        Space(Location(3, 1)),
        Space(Location(4, 1)),
        Space(Location(5, 1)),
        Space(Location(6, 1)),
        Space(Location(7, 1))
    ),
    arrayListOf(
        Space(Location(0, 2)),
        Space(Location(1, 2)),
        Space(Location(2, 2)),
        Space(Location(3, 2)),
        Space(Location(4, 2)),
        Space(Location(5, 2)),
        Space(Location(6, 2)),
        Space(Location(7, 2))
    ),
    arrayListOf(
        Space(Location(0, 3)),
        Space(Location(1, 3)),
        Space(Location(2, 3)),
        Space(Location(3, 3)),
        Space(Location(4, 3)),
        Space(Location(5, 3)),
        Space(Location(6, 3)),
        Space(Location(7, 3))
    ),
    arrayListOf(
        Space(Location(0, 4)),
        Space(Location(1, 4)),
        Space(Location(2, 4)),
        Space(Location(3, 4)),
        Space(Location(4, 4)),
        Space(Location(5, 4)),
        Space(Location(6, 4)),
        Space(Location(7, 4))
    ),
    arrayListOf(
        Space(Location(0, 5)),
        Space(Location(1, 5)),
        Space(Location(2, 5)),
        Space(Location(3, 5)),
        Space(Location(4, 5)),
        Space(Location(5, 5)),
        Space(Location(6, 5)),
        Space(Location(7, 5))
    ),
    arrayListOf(
        Space(Location(0, 6)),
        Space(Location(1, 6)),
        Space(Location(2, 6)),
        Space(Location(3, 6)),
        Space(Location(4, 6)),
        Space(Location(5, 6)),
        Space(Location(6, 6)),
        Space(Location(7, 6))
    ),
    arrayListOf(
        Space(Location(0, 7)),
        Space(Location(1, 7)),
        Space(Location(2, 7)),
        Space(Location(3, 7)),
        Space(Location(4, 7)),
        Space(Location(5, 7)),
        Space(Location(6, 7)),
        Space(Location(7, 7))
    )
)

/**
 * Builds an HashMap containing all the white pieces
 */
fun buildPlayerHash(
    board: MutableList<MutableList<Piece>>
): HashMap<Char, MutableList<Piece>> {
    val hash = HashMap<Char, MutableList<Piece>>()
    val rook = mutableListOf(board[7][0], board[7][7])
    val bishop = mutableListOf(board[7][2], board[7][5])
    val knight = mutableListOf(board[7][1], board[7][6])
    val king = mutableListOf(board[7][4])
    val queen = mutableListOf(board[7][3])
    val pawn = mutableListOf(
        board[6][0],
        board[6][1],
        board[6][2],
        board[6][3],
        board[6][4],
        board[6][5],
        board[6][6],
        board[6][7]
    )
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
fun buildOpponentHash(
    board: MutableList<MutableList<Piece>>
): HashMap<Char, MutableList<Piece>> {
    val hash = HashMap<Char, MutableList<Piece>>()
    val rook = mutableListOf(board[0][0], board[0][7])
    val bishop = mutableListOf(board[0][2], board[0][5])
    val knight = mutableListOf(board[0][1], board[0][6])
    val king = mutableListOf(board[0][4])
    val queen = mutableListOf(board[0][3])
    val pawn = mutableListOf(
        board[1][0],
        board[1][1],
        board[1][2],
        board[1][3],
        board[1][4],
        board[1][5],
        board[1][6],
        board[1][7]
    )
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
fun invertBoard(
    board: MutableList<MutableList<Piece>>
): MutableList<MutableList<Piece>> {
    val newBoard = buildEmptyBoard()
    board.forEach { row ->
        row.forEach {
            val newLocation = invertLocation(it.location)
            it.location = newLocation
            newBoard[newLocation.y][newLocation.x] = it
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
    specialMoveResult: SpecialMoveResult
): Piece {
    val piece = specialMoveResult.piece
    return when(string) {
        resources.getString(R.string.queen) -> Queen(piece.team, piece.location)
        resources.getString(R.string.rook) -> Rook(piece.team, piece.location)
        resources.getString(R.string.knight) -> Knight(piece.team, piece.location)
        else -> Bishop(piece.team, piece.location)
    }
}

/**
 * Picks the correct type of piece to promote based on a resource
 */
fun pickPromotedById(
    id: Char,
    specialMoveResult: SpecialMoveResult
): Piece {
    val piece = specialMoveResult.piece
    return when(id) {
        QUEEN -> Queen(piece.team, piece.location)
        ROOK -> Rook(piece.team, piece.location)
        KNIGHT -> Knight(piece.team, piece.location)
        else -> Bishop(piece.team, piece.location)
    }
}