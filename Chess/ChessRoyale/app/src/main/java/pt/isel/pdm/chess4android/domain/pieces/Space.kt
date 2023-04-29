package pt.isel.pdm.chess4android.domain.pieces

import kotlinx.parcelize.Parcelize

@Parcelize
class Space(private val l: Location) : Piece(l, Team.SPACE, SPACE)