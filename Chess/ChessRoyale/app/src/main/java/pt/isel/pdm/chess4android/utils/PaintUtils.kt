package pt.isel.pdm.chess4android.utils

import androidx.compose.ui.graphics.Color
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.domain.pieces.*
import pt.isel.pdm.chess4android.ui.theme.Highlight
import pt.isel.pdm.chess4android.ui.theme.Selected
import pt.isel.pdm.chess4android.ui.theme.Xeque
import pt.isel.pdm.chess4android.ui.theme.XequeMate

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
            if (list.any { it.location.x == piece.location.x && it.location.y == piece.location.y}) {
                return Xeque
            }
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
                return if (piece is Space) Highlight else Xeque
            }
        }
        return null
    }
}