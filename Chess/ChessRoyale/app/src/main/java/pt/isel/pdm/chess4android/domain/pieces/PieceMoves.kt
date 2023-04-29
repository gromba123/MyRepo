package pt.isel.pdm.chess4android.domain.pieces

enum class Move(
    val x: Int,
    val y: Int
) {
    NORTH(0, -1),
    NORTH_EAST(1, -1),
    EAST(1, 0),
    SOUTH_EAST(1, 1),
    SOUTH(0, 1),
    SOUTH_WEST(-1, 1),
    WEST(-1, 0),
    NORTH_WEST(-1, -1),
    KNIGHT_1(-2, -1),
    KNIGHT_2(-2, 1),
    KNIGHT_3(2, -1),
    KNIGHT_4(2, 1),
    KNIGHT_5(-1, -2),
    KNIGHT_6(-1, 2),
    KNIGHT_7(1, -2),
    KNIGHT_8(1, 2),
    DOUBLE_PAWN_UP(0, -2),
    DOUBLE_PAWN_DOWN(0, 2)
}

val KNIGHT_MOVES = listOf(
    Move.KNIGHT_1,
    Move.KNIGHT_2,
    Move.KNIGHT_3,
    Move.KNIGHT_4,
    Move.KNIGHT_5,
    Move.KNIGHT_6,
    Move.KNIGHT_7,
    Move.KNIGHT_8,
)