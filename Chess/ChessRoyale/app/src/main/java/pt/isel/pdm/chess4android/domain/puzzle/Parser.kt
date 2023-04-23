package pt.isel.pdm.chess4android.domain.puzzle

import pt.isel.pdm.chess4android.domain.pieces.*
import pt.isel.pdm.chess4android.utils.*
import kotlin.math.abs

/**
 * Type that receives a list of movements and converts them into a board
 */
class Parser {

    private val board: MutableList<MutableList<Piece>> = buildBoard()
    private val whites: HashMap<Char, MutableList<Piece>> = buildWhiteHash(board)
    private val blacks: HashMap<Char, MutableList<Piece>> = buildBlackHash(board)
    private var team = Team.WHITE

    /**
     * Receives a list of movements and converts them into a board
     */
    fun parsePGN(moves: List<String>, toRotate: Boolean): PuzzleBoard {
        val movesFiltered = moves.map { it.replace("+", "") }
        movesFiltered.forEach {
            if (it.contains("x")) {
                val l = it.indexOfFirst { c -> c == 'x' } + 1
                removeElement(convertToLocation(it.substring(l, l + 2)), team)
                val mv = it.replace("x", "")
                parseMove(mv, team)
            } else {
                parseMove(it, team)
            }
            team = team.other
        }

        val b = when (toRotate) {
            true -> {
                blacks['P']?.forEach { if (it is Pawn) it.setDirectionToPlay() }
                invertBoard(board)
            }
            else -> board
        }

        return PuzzleBoard(b, whites, blacks, team)
    }

    /**
     * Moves a piece to the new location
     */
    private fun movePiece(old: Location, new: Location) {
        val piece = board[old.y][old.x]
        piece.location = new
        board[old.y][old.x] = Space(old)
        board[new.y][new.x] = piece
    }

    /**
     * Removes a piece from the team hash
     */
    private fun removeElement(location: Location, team: Team) {
        val hash = if (team == Team.WHITE) blacks else whites
        val piece = board[location.y][location.x]
        hash[piece.id]?.remove(piece)
    }

    /**
     * Receives a move and converts it into a move
     */
    private fun parseMove(move: String, team: Team) {
        when {
            move.length == 2 -> movePawn(move, team)
            move.length == 3 -> {
                when {
                    move == "O-O" -> castleKingSide(team)
                    contains(move[0], team) -> move(move[0], move.substring(1), team)
                    else -> specificTie("P$move", team)
                }
            }
            move == "O-O-O" -> castleQueenSide(team)

            move.contains("=") -> promotion(move, team)

            move.length == 4 -> {
                when {
                    contains(move[0], team) -> specificTie(move, team)
                    else -> tie(move)
                }
            }
            else -> tie(move.substring(1))
        }
    }

    /**
     * Moves a piece when 2 pieces of the same type are in the
     * same row or column
     */
    private fun specificTie(move: String, team: Team) {
        val id = move[0]
        val pieces = (if (team == Team.WHITE) whites[id] else blacks[id]) ?: return
        val filtered = if (move[1] - '0' > 8)
            filterTiePieces(pieces) { it.location.x == move[1] - 'a'}
        else filterTiePieces(pieces) { it.location.y == convertRank(move[1] - '0') }
        val newLocation = convertToLocation(move.substring(2))
        filtered.forEach {
            if (it.checkPosition(board).any { location: Location -> location.toString() == newLocation.toString() }) {
                movePiece(it.location, newLocation)
            }
        }
    }

    /**
     * Filters the pieces that don't satisfy the predicate
     */
    private fun filterTiePieces(pieces: List<Piece>, predicate: (Piece) -> Boolean) =
        pieces.filter(predicate)

    /**
     * When 2 pieces of the same type can reach the same location and are on the same
     * row or column. The received move contains the old location and the new.
     */
    private fun tie(move: String) {
        val oldLocation = convertToLocation(move.substring(0, 2))
        val newLocation = convertToLocation(move.substring(2, 4))
        movePiece(oldLocation, newLocation)
    }

    /**
     * Promotes a pawn
     */
    private fun promotion(move: String, team: Team) {
        val pieces = if (team == Team.WHITE) whites['P'] else blacks['P']
        if (pieces != null) {
            val equalsIndex = move.indexOfFirst { it == '=' }
            val newLocation = convertToLocation(move.substring(equalsIndex - 2, equalsIndex))
            var i = 0
            while (i < pieces.size) {
                val list = pieces[i].checkPosition(this.board)
                if (list.any { location -> location.toString() == newLocation.toString() }) {
                    val p = pieces[i]
                    pieces.remove(p)
                    board[p.location.y][p.location.x] = Space(p.location)
                    val id = move[move.length - 1]
                    val newPiece = promote(newLocation, id, team)
                    (if (team == Team.WHITE) whites[id] else blacks[id])?.add(newPiece)
                    board[newLocation.y][newLocation.x] = newPiece
                    break
                }
                i++
            }
        }
    }

    /**
     * Obtains the type of piece to promote
     */
    private fun promote(location: Location, id: Char, team: Team): Piece =
        when (id) {
            'Q' -> Queen(team, location)
            'B' -> Bishop(team, location)
            'N' -> Knight(team, location)
            else -> Rook(team, location)
        }

    /**
     * To verify if the received key is the id of a piece of a identifier of a
     * row or column
     */
    private fun contains(c: Char, team: Team): Boolean =
        if (team == Team.WHITE) whites.containsKey(c) else blacks.containsKey(c)

    /**
     * Moves a piece
     */
    private fun move(c: Char, move: String, team: Team) {
        val pieces = if (team == Team.WHITE) whites[c] else blacks[c]
        val newLocation = convertToLocation(move)
        if (pieces != null) {
            var i = 0
            while (i < pieces.size) {
                val list = pieces[i].checkPosition(this.board)
                if (list.any { location -> location.toString() == newLocation.toString() }) {
                    val p = pieces[i]
                    if (c == 'P') {
                        checkPassant(newLocation, p.location, team)
                        (p as Pawn).incMoves()
                    }
                    movePiece(p.location, newLocation)
                    return
                }
                i++
            }
        }
    }

    /**
     * Moves a pawn
     */
    private fun movePawn(move: String, team: Team) = move('P', move, team)

    /**
     * Verifies en passant
     */
    private fun checkPassant(new: Location, old: Location, team: Team) {
        val delta = if (team == Team.WHITE) 1 else -1
        if (abs(new.x - old.x) != 0 && abs(new.y - old.y) != 0) {
            if (board[new.y][new.x] is Space && board[new.y + delta][new.x] is Pawn) {
                val pieces = if (team == Team.BLACK) whites['P'] else blacks['P']
                pieces?.remove(board[new.y + delta][new.x])
                board[new.y + delta][new.x] = Space(Location(new.x, new.y + delta))
            }
        }
    }

    /**
     * Executes a king side castling
     */
    private fun castleKingSide(team: Team) {
        if (team == Team.WHITE) {
            movePiece(Location(4, 7), Location(6, 7))
            movePiece(Location(7, 7), Location(5, 7))
        } else {
            movePiece(Location(4, 0), Location(6, 0))
            movePiece(Location(7, 0), Location(5, 0))
        }
    }

    /**
     * Executes a queen side castling
     */
    private fun castleQueenSide(team: Team) {
        if (team == Team.WHITE) {
            movePiece(Location(4, 7), Location(2, 7))
            movePiece(Location(0, 7), Location(3, 7))
        } else {
            movePiece(Location(4, 0), Location(2, 0))
            movePiece(Location(0, 0), Location(3, 0))
        }
    }
}