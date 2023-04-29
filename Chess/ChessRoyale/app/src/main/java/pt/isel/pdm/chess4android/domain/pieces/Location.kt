package pt.isel.pdm.chess4android.domain.pieces

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pt.isel.pdm.chess4android.utils.convertRank
import pt.isel.pdm.chess4android.utils.convertToFile

/**
 * Represents a location in the board
 */
@Parcelize
class Location(val x: Int, val y: Int) : Parcelable, Comparable<Location> {

    /**
     * Computes a new location based on the previous
     */
    fun computeLocation(x: Int, y: Int) = Location(this.x + x, this.y + y)

    /**
     * Checks if the location is inside the board
     */
    fun checkLimits() : Boolean = x in 0..7 && y in 0..7

    /**
     * Converts the location to an Algebraic Notation position
     */
    override fun toString(): String = "${convertToFile(x)}${convertRank(y)}"

    override fun compareTo(other: Location) = if (x == other.x && y == other.y) 0 else -1
}