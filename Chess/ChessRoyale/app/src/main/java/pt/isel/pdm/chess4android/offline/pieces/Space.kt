package pt.isel.pdm.chess4android.offline.pieces

import kotlinx.parcelize.Parcelize

@Parcelize
class Space(private val l: Location) : Piece(l, Team.SPACE, 'S') {
    override fun checkPosition(board: List<List<Piece>>): List<Location> {
        TODO("Not yet implemented")
    }
}