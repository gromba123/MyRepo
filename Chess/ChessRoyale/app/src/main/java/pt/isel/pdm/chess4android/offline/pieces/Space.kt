package pt.isel.pdm.chess4android.offline.pieces

import kotlinx.parcelize.Parcelize

@Parcelize
class Space : Piece(Location(0,0), Team.SPACE, 'S') {
    override fun checkPosition(board: List<List<Piece>>): List<Location> {
        TODO("Not yet implemented")
    }
}