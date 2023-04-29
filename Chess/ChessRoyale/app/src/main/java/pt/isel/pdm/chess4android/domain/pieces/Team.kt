package pt.isel.pdm.chess4android.domain.pieces

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents the White and Black teams.
 * Space is also considered a team to simplify the logic to move or analyze
 * the possibles moves of a piece
 */
@Parcelize
enum class Team(val x: Int) : Parcelable {
    WHITE(1),
    BLACK(-1),
    SPACE(0);

    companion object {
        val firstToMove: Team = WHITE
    }

    val other: Team
        get() = if (this == WHITE) BLACK else WHITE
}