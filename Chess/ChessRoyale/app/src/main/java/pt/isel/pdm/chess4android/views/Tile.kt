package pt.isel.pdm.chess4android.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.pieces.*
import pt.isel.pdm.chess4android.domain.pieces.pawn.BasePawn

enum class Type { WHITE, BLACK, SELECTED, HIGHLIGHT, XEQUE }

/**
 * Custom view that implements a chess board tile.
 * Tiles are either black or white and can they can be empty or occupied by a chess piece.
 *
 * @property ctx            Context for the tile
 * @property backgroundType The tile's type (i.e. black or white)
 * @property highlightType  The type of the highlight. Can be Selected or Highlight
 * @property check          Null if there is no check
 * @property tilesPerSide   The number of tiles in each side of the chess board
 * @property piece          Piece contained in the tile
 */
@SuppressLint("ViewConstructor")
class Tile(
    private val ctx: Context,
    val backgroundType: Type,
    val highlightType: Type?,
    val check: Type?,
    private val tilesPerSide: Int,
    val piece: Piece
) : View(ctx) {

    private fun brushColor() =
        if (check == null)
            when (backgroundType) {
                Type.WHITE -> R.color.chess_board_white
                else -> R.color.chess_board_black
            }
        else R.color.xeque_and_mate


    private val brush = Paint().apply {
        color = ctx.resources.getColor(brushColor(), null)
        style = Paint.Style.FILL_AND_STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val side = Integer.min(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(side / tilesPerSide, side / tilesPerSide)
    }

    private fun highlightBrush(): Paint {
        val brush = Paint().apply {
            color = ctx.resources.getColor(
                when (highlightType) {
                    Type.HIGHLIGHT -> if (piece is Space) R.color.teal_700 else R.color.highlight_xeque
                    else -> R.color.teal_200
                },
                null
            )
            style = Paint.Style.FILL_AND_STROKE
        }
        return brush
    }

    /**
     * The radius can be different.
     * The radius is defined based on the Type and piece.
     * An highlighted space must have a lower radius than a piece
     */
    private fun radius() =
        when(highlightType) {
            Type.HIGHLIGHT -> if (piece is Space) width.toFloat() / 4 else width.toFloat() / 2
            else -> width.toFloat() / 2
        }

    override fun onDraw(canvas: Canvas) {
        val padding = 8
        val drawableRes = getPiece(piece)
        val drawable = if (drawableRes != null) VectorDrawableCompat
            .create(ctx.resources, drawableRes, null) else null
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), brush)
        if (highlightType != null) {
            canvas.drawCircle(
                width.toFloat() / 2,
                height.toFloat() / 2,
                radius(),
                highlightBrush()
            )
        }
        drawable?.setBounds(padding, padding, width-padding, height-padding)
        drawable?.draw(canvas)
    }

    private fun getPiece(piece: Piece) =
        when (piece) {
            is BasePawn -> if(piece.team == Team.WHITE)R.drawable.ic_white_pawn else R.drawable.ic_black_pawn
            is Rook -> if(piece.team == Team.WHITE)R.drawable.ic_white_rook else R.drawable.ic_black_rook
            is Knight -> if(piece.team == Team.WHITE)R.drawable.ic_white_knight else R.drawable.ic_black_knight
            is Bishop -> if(piece.team == Team.WHITE)R.drawable.ic_white_bishop else R.drawable.ic_black_bishop
            is Queen -> if(piece.team == Team.WHITE)R.drawable.ic_white_queen else R.drawable.ic_black_queen
            is King -> if(piece.team == Team.WHITE)R.drawable.ic_white_king else R.drawable.ic_black_king
            else -> null
        }
}