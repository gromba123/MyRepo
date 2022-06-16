package pt.isel.pdm.chess4android.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.GridLayout
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.offline.pieces.Location
import pt.isel.pdm.chess4android.offline.pieces.Piece
import pt.isel.pdm.chess4android.offline.pieces.Space

/**
 * Custom view that implements a chess board.
 */
@SuppressLint("ClickableViewAccessibility")
class BoardView(private val ctx: Context, attrs: AttributeSet?) : GridLayout(ctx, attrs) {

    private val side = 8

    private var listener: OnTileTouchListener? = null

    private val brush = Paint().apply {
        ctx.resources.getColor(R.color.chess_board_black, null)
        style = Paint.Style.STROKE
        strokeWidth = 10F
    }

    init {
        rowCount = side
        columnCount = side
        repeat(side * side) {
            val row = it / side
            val column = it % side
            val tile = Tile(
                            ctx,
                            if((row + column) % 2 == 0) Type.WHITE else Type.BLACK,
                            null,
                            null,
                            side,
                            Space()
                        )
            addView(tile)
        }
    }

    fun drawBoard(board: List<List<Piece>>) {
        var counter = 0
        board.forEach {
            it.forEach { p ->
                addTile(counter / side, counter % side, p)
                counter++
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        canvas.drawLine(0f, 0f, width.toFloat(), 0f, brush)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), brush)
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), brush)
        canvas.drawLine(width.toFloat(), 0f, width.toFloat(), height.toFloat(), brush)
    }

    private fun addTile(x: Int, y: Int, piece: Piece = Space()) {
        val index = x * side + y
        val t: Tile = getChildAt(index) as Tile
        val newTile = Tile(ctx, t.backgroundType, t.highlightType, t.check, side, piece)
        removeView(t)
        addView(newTile, index)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        try {
            val tileSize = width / side
            return when {
                event == null -> false
                event.action == MotionEvent.ACTION_DOWN -> {
                    val x = (event.x / tileSize).toInt()
                    val y = (event.y / tileSize).toInt()
                    listener?.onClick(x, y)
                    true
                }
                else -> false
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Creates a new tile and updates the tile at the supposed index
     */
    fun drawSelected(location: Location, type: Type) {
        val index = location.y * side + location.x
        val tile: Tile = getChildAt(index) as Tile
        val newBackgroundType = tile.backgroundType
        val newHighlightType = if (type != Type.XEQUE && tile.highlightType == null) type else null
        val check = if (type == Type.XEQUE && tile.check == null) type else null
        val newTile = Tile(ctx, newBackgroundType, newHighlightType, check, side, tile.piece)
        removeView(tile)
        addView(newTile, index)
    }

    fun setListener(listener: OnTileTouchListener) {
        this.listener = listener
    }
}