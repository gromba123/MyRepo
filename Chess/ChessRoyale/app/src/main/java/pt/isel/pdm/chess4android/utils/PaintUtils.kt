package pt.isel.pdm.chess4android.utils

import android.util.Log
import androidx.compose.ui.graphics.Color
import pt.isel.pdm.chess4android.APP_TAG
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.offline.pieces.*
import pt.isel.pdm.chess4android.theme.Highlight
import pt.isel.pdm.chess4android.theme.Selected
import pt.isel.pdm.chess4android.theme.Xeque
import pt.isel.pdm.chess4android.theme.XequeMate

fun getPiece(piece: Piece) =
    when (piece) {
        is Pawn -> if(piece.team == Team.WHITE) R.drawable.ic_white_pawn else R.drawable.ic_black_pawn
        is Rook -> if(piece.team == Team.WHITE) R.drawable.ic_white_rook else R.drawable.ic_black_rook
        is Knight -> if(piece.team == Team.WHITE) R.drawable.ic_white_knight else R.drawable.ic_black_knight
        is Bishop -> if(piece.team == Team.WHITE) R.drawable.ic_white_bishop else R.drawable.ic_black_bishop
        is Queen -> if(piece.team == Team.WHITE) R.drawable.ic_white_queen else R.drawable.ic_black_queen
        is King -> if(piece.team == Team.WHITE) R.drawable.ic_white_king else R.drawable.ic_black_king
        else -> null
    }

fun getColor(piece: Piece, paintResults: PaintResults): Color? {
    if (paintResults.endgameResult != null) {
        for (list: List<Piece> in paintResults.endgameResult.pieces) {
            if (list.contains(piece)) return Xeque
        }
        return null
    } else {
        if (paintResults.selectedPiece != null &&
            paintResults.selectedPiece.x == piece.location.x &&
            paintResults.selectedPiece.y == piece.location.y
        ) {
            return Selected
        }
        if (paintResults.xequePiece != null &&
            paintResults.xequePiece.x == piece.location.x &&
            paintResults.xequePiece.y == piece.location.y
        ) {
            return XequeMate
        }
        if (paintResults.highlightPieces != null) {
            if (paintResults.highlightPieces.any { it.x == piece.location.x && it.y == piece.location.y}) {
                return Highlight
            }
        }
        return null
    }
}