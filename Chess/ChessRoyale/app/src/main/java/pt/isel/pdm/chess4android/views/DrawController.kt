package pt.isel.pdm.chess4android.views

import pt.isel.pdm.chess4android.utils.DrawResults
import pt.isel.pdm.chess4android.offline.pieces.Location

/**
 * Controller that controls the all the board painting
 */
data class DrawController(val int: Int = 0) {

    private var selectedPiece: Location? = null
    private var highlightPieces: MutableList<Location>? = mutableListOf()
    private var xeque: Location? = null

    /**
     * Paints the selected piece
     */
    fun drawSelectedPiece(location: Location?): DrawResults {
        val list = mutableListOf<Location>()
        if (selectedPiece != null)
            selectedPiece?.let { list.add(it) }
        selectedPiece = location
        if (location != null) {
            list.add(location)
        }
        return DrawResults(list, Type.SELECTED)
    }

    /**
     * Paints the moves of the selected piece
     */
    fun drawHighlight(list: List<Location>?): DrawResults {
        val newList = mutableListOf<Location>()
        if (highlightPieces != null)
            highlightPieces?.let { it.forEach { pos -> newList.add(pos) } }
        highlightPieces = list?.toMutableList()
        list?.let { it.forEach { pos -> newList.add(pos) } }
        return DrawResults(newList, Type.HIGHLIGHT)
    }

    /**
     * Paints a xeque
     */
    fun drawXeque(location: Location?): DrawResults {
        val list = mutableListOf<Location>()
        if (xeque != null)
            xeque?.let { list.add(it) }
        xeque = location
        if (location != null) {
            list.add(location)
        }
        return DrawResults(list, Type.XEQUE)
    }

    /**
     * Cleans the check
     */
    fun cleanCheck() {
        xeque = null
    }
}