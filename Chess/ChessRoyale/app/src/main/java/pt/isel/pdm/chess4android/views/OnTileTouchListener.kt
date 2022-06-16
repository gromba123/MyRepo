package pt.isel.pdm.chess4android.views

/**
 * Listener for the board to allow movements
 */
interface OnTileTouchListener {
    fun onClick(x: Int, y: Int): Boolean
}