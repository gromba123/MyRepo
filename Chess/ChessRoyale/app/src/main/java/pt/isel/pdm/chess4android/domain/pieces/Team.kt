package pt.isel.pdm.chess4android.domain.pieces

const val WHITE = "white"
const val BLACK = "black"

/**
 * Represents the White and Black teams.
 * Space is also considered a team to simplify the logic to move or analyze
 * the possibles moves of a piece
 */
enum class Team(val x: Int, val value: String) {
    WHITE(1, "white"),
    BLACK(-1, "black"),
    SPACE(0, "space");

    val other: Team
        get() = if (this == WHITE) BLACK else WHITE
}

fun getTeam(team: String) = when(team) {
    WHITE -> Team.WHITE
    else -> Team.BLACK
}