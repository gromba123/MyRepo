package pt.isel.pdm.chess4android.domain

import pt.isel.pdm.chess4android.domain.pieces.Location
import pt.isel.pdm.chess4android.utils.PaintResults

/**
 * Controller that controls the all the board painting
 */
class DrawController(val int: Int = 0) {

    private var selectedPiece: Location? = null
    private var highlightPieces: List<Location>? = null
    private var xeque: Location? = null

    fun getActualResults() = PaintResults(
        selectedPiece = selectedPiece,
        highlightPieces = highlightPieces,
        xequePiece = xeque,
        endgameResult = null
    )

    /**
     * Paints the selected piece
     */
    fun drawSelectedPiece(location: Location, list: List<Location>): PaintResults {
        selectedPiece = location
        highlightPieces = list
        return getActualResults()
    }

    /**
     * Paints a xeque
     */
    fun drawXeque(location: Location?): PaintResults {
        xeque = location
        return getActualResults()
    }

    fun cleanSelectedPiece() {
        selectedPiece = null
        highlightPieces = null
    }

    /**
     * Cleans the check
     */
    fun cleanCheck() {
        xeque = null
    }
}